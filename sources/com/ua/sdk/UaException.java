package com.ua.sdk;

public class UaException extends Exception {
    private final Code code;

    public enum Code {
        TIMEOUT,
        NETWORK,
        PERMISSION,
        CANCELED,
        NOT_FOUND,
        BAD_FORMAT,
        UNKNOWN,
        NOT_AUTHENTICATED,
        NOT_CONNECTED,
        NETWORK_ON_MAIN_THREAD
    }

    public UaException(String message) {
        super(message);
        this.code = Code.UNKNOWN;
    }

    public UaException(String message, Throwable e) {
        super(message, e);
        this.code = Code.UNKNOWN;
    }

    public UaException(Code code2, String message, Throwable e) {
        super(message, e);
        this.code = code2;
    }

    public UaException() {
        super(Code.UNKNOWN.toString());
        this.code = Code.UNKNOWN;
    }

    public UaException(Throwable cause) {
        super(Code.UNKNOWN.toString(), cause);
        this.code = Code.UNKNOWN;
    }

    public UaException(Code code2) {
        super(code2.toString());
        this.code = code2;
    }

    public UaException(Code code2, Throwable cause) {
        super(code2.toString(), cause);
        this.code = code2;
    }

    public Code getCode() {
        return this.code;
    }
}
