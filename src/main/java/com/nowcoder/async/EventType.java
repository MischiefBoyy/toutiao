package com.nowcoder.async;

public enum EventType {
	ADDCLICKNUM(0), LIKE(1);

	private int value;

	EventType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
