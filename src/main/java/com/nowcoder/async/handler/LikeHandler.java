package com.nowcoder.async.handler;

import java.util.Arrays;
import java.util.List;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;

public class LikeHandler implements EventHandler{

	@Override
	public void doHandle(EventModel eventModel) {
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.LIKE);
	}

}
