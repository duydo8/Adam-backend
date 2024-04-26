package com.example.adambackend.enums;

public enum OrderStatus {
	pending(1),// dang xac nhan
	active(2),// da xac nhan
	received(3),// da nhan hang
	cancel(0),// huy don hang
	payback(-1),// doi tra
	delay(4),// hoan don hang
	checking(5),//kiem tra lai don hang hoan tra hoac cancel
	success(6), // thành công
	delivery(7);
	private int value;

	public int getValue() {
		return value;
	}

	private OrderStatus(int value) {
		this.value = value;
	}

	public static OrderStatus getOrderStatusFromValue(int value) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
}
