package com.nowcoder.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.nowcoder.service.RedisAdapter;
import com.nowcoder.util.RedisKeyUtil;

@Component
public class EventConsumer implements InitializingBean, ApplicationContextAware {
	@Autowired
	RedisAdapter jedisAdapter;
	private static Logger logger = LoggerFactory.getLogger(EventConsumer.class);
	private ApplicationContext applicationContext;
	private Map<EventType, List<EventHandler>> configs = new HashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		// 找到所有的Handler类
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if (beans != null) {
			beans.forEach((k, v) -> {
				// 得到一个handler监听的 handler类型 即EventType列表
				List<EventType> eventTypes = v.getSupportEventType();
				// 循环该列表 ，判断该列表的eventType是否已经存在 关系映射Map中 即configs
				for (EventType type : eventTypes) {
					// 如果不存在则把eventType作为key存入configs
					if (!configs.containsKey(type)) {
						configs.put(type, new ArrayList<EventHandler>(16));
					}
					// 可以即为handler的类型，value即为监听该类型的handler实体，因为可以使一个实体监听多个类型，所以需要存入列表
					configs.get(type).add(v);
				}
			});

		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// 从队列一直消费
				while (true) {
					// 获取消息队列中的业务
					String key = RedisKeyUtil.getEventQueueKey();
					List<String> messages = jedisAdapter.brpop(0, key);
					// 第一个元素是队列名字
					for (String message : messages) {
						if (message.equals(key)) {
							continue;
						}
						EventModel eventModel = JSON.parseObject(message, EventModel.class);
						// 找到这个事件的处理handler列表
						if (!configs.containsKey(eventModel.getType())) {
							logger.error("不能识别的事件:"+eventModel.getType());
							continue;
						}

						for (EventHandler handler : configs.get(eventModel.getType())) {
							handler.doHandle(eventModel);
						}
					}
				}

			}
		});
		thread.start();

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;

	}

}
