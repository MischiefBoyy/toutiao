package com.nowcoder.async;

import java.util.List;

public interface EventHandler {
	/**
	 * 可以处理各种各样的事情
	 * @TODO TODO
	 * @Time 2018年1月11日 上午9:57:49
	 * @author WEQ
	 * @return void
	 */
	void  doHandle(EventModel eventModel);
	
	/**
	 * 关注哪一些 EventType
	 */
	List<EventType> getSupportEventType();
}
