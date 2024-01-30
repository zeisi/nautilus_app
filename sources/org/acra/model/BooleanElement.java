package org.acra.model;

public class BooleanElement implements Element {
    private final boolean content;

    public BooleanElement(boolean content2) {
        this.content = content2;
    }

    public Object value() {
        return Boolean.valueOf(this.content);
    }

    public String[] flatten() {
        return new String[]{toString()};
    }

    public String toString() {
        return String.valueOf(this.content);
    }
}
