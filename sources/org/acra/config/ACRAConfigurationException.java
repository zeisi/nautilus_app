package org.acra.config;

public class ACRAConfigurationException extends Exception {
    private static final long serialVersionUID = -7355339673505996110L;

    public ACRAConfigurationException(String msg) {
        super(msg);
    }

    public ACRAConfigurationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
