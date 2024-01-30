package org.acra.model;

public class StringElement implements Element {
    private final String content;

    public StringElement(String content2) {
        this.content = content2;
    }

    public Object value() {
        return this.content;
    }

    public String[] flatten() {
        return new String[]{this.content};
    }

    public String toString() {
        return this.content;
    }
}
