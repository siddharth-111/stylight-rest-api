package com.pinguin.model.enums;

public enum Priority {
    CRITICAL("CRITICAL"),
    MAJOR("MAJOR"),
    MINOR("MINOR")
    ;

    private final String text;

    Priority(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

