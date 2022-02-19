package com.pinguin.model.enums;

public enum BugStatus {
    NEW("NEW"),
    VERIFIED("VERIFIED"),
    RESOLVED("RESOLVED")
    ;

    private final String text;

    BugStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
