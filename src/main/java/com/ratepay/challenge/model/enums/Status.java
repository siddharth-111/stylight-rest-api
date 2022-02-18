package com.ratepay.challenge.model.enums;

public enum Status {
    ADD("NEW"),
    VERIFIED("VERIFIED"),
    RESOLVED("RESOLVED")
    ;

    private final String text;

    Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
