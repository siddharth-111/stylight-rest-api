package com.example.SpringBoot.Model.enums;

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
