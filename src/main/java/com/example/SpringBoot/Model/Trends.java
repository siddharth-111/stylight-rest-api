package com.example.SpringBoot.Model;

public enum Trends {
    DESCENDING("descending"),
    ASCENDING("ascending"),
    CONSTANT("constant"),
    UNDEFINED("undefined")
    ;

    private final String text;

    Trends(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}