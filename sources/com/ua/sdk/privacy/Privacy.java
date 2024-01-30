package com.ua.sdk.privacy;

import com.ua.sdk.Entity;

public interface Privacy extends Entity {
    Level getLevel();

    String getPrivacyDescription();

    public enum Level {
        PRIVATE(0),
        FRIENDS(1),
        PUBLIC(3);
        
        public final int id;

        private Level(int id2) {
            this.id = id2;
        }
    }
}
