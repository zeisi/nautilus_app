package com.myfitnesspal.android.sdk;

public enum Scope {
    Diary {
        public String toString() {
            return "diary";
        }
    },
    DiaryReadOnly {
        public String toString() {
            return "diary_readonly";
        }
    }
}
