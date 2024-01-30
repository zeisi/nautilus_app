package com.myfitnesspal.shared.utils;

public final class Strings {
    public static final boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static final boolean notEmpty(String s) {
        return !isEmpty(s);
    }

    public static final boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 != null) {
            return s1.equals(s2);
        }
        if (s2 != null) {
            return s2.equals(s1);
        }
        return false;
    }

    public static final String toString(Object obj) {
        return obj != null ? obj.toString() : "null";
    }
}
