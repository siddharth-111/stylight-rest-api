package com.stylight.model.enums;

public enum StoryStatus {
    NEW("NEW"),
    ESTIMATED("ESTIMATED"),
    COMPLETED("COMPLETED")
    ;

    private final String text;

    StoryStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
