package org.acra.model;

public class NumberElement implements Element {
    private final Number content;

    public NumberElement(Number content2) {
        this.content = content2;
    }

    public Object value() {
        return this.content;
    }

    public String[] flatten() {
        return new String[]{toString()};
    }

    public String toString() {
        return this.content.toString();
    }
}
