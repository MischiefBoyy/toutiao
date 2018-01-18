package com.nowcoder.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.nowcoder.service.RedisAdapter;
import com.nowcoder.util.RedisKeyUtil;

@Service
public class EventProducer {
	@Autowired
	RedisAdapter jedisAdapter;

	public boolean sendEvent(EventModel model) {
		try {
			// 加入队列 存入到redis队列
			String json = JSON.toJSONString(model);
			String key = RedisKeyUtil.getEventQueueKey();
			jedisAdapter.lpush(key, json);
			return true;
		} catch (Exception e) {
			System.out.println("-----------添加队列出现问题");
			return false;
		}

	}
}
