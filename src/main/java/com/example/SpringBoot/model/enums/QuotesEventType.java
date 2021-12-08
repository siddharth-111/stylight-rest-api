package com.example.SpringBoot.model.enums;

public enum QuotesEventType {
    QUOTE("QUOTE")
    ;

    private final String text;

    QuotesEventType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
