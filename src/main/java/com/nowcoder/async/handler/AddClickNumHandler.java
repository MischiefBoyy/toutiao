package com.nowcoder.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
@Component
public class AddClickNumHandler implements EventHandler {

	@Override
	public void doHandle(EventModel eventModel) {
		System.out.println("addCount  --  Handler");
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		return Arrays.asList(EventType.ADDCLICKNUM);
	}

}
