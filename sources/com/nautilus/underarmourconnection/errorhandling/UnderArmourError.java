package com.nautilus.underarmourconnection.errorhandling;

public class UnderArmourError {
    private String errorMessage;
    private String errorRecommendation;

    public UnderArmourError(String errorMessage2, String errorRecommendation2) {
        this.errorMessage = errorMessage2;
        this.errorRecommendation = errorRecommendation2;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getErrorRecommendation() {
        return this.errorRecommendation;
    }
}
