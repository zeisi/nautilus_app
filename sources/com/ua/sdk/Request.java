package com.ua.sdk;

public interface Request {
    boolean cancel();

    boolean isAsynchronous();

    boolean isCanceled();
}
