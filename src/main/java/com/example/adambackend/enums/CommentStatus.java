package com.example.adambackend.enums;

public enum CommentStatus {
    DELETE(-1),PENDING(0),ACTIVE(1);
    private int value;

    private CommentStatus(int value) {
        this.value = value;
    }
}
