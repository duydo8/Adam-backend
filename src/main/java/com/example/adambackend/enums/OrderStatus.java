package com.example.adambackend.enums;

public enum OrderStatus {
    pending(1),// dang xac nhan
    active(2),// da xac nhan
    received(3),// da nhan hang
    cancel(0),// huy don hang
    payback(-1),// doi tra
    delay(4),// hoan don hang
    checking(5),//kiem tra lai don hang hoan tra hoac cancel
    success(6); // thành công
    private int value;

    private OrderStatus(int value) {
        this.value = value;
    }
}
