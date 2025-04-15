package com.example.sneakershop.enums;

public enum SneakerCategory {
    RUNNING("Беговые"),
    BASKETBALL("Баскетбольные"),
    LIMITED("Лимитированные"),
    CASUAL("Повседневные");


    private final String displayName;

    SneakerCategory(String displayName) {
        this.displayName = displayName;
    }


    public String getDisplayName() {
        return displayName;
    }
}
