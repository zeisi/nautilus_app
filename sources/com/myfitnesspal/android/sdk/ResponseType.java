package com.myfitnesspal.android.sdk;

public enum ResponseType {
    Code {
        public String toString() {
            return "code";
        }
    },
    Token {
        public String toString() {
            return "token";
        }
    },
    Both {
        public String toString() {
            return "code,token";
        }
    }
}
