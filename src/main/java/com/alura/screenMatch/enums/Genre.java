package com.alura.screenMatch.enums;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FANTASY("Fantasy");


    Genre(String name) {}

    public static Genre fromString (String genre) {
        for (Genre value : Genre.values()) {
            if (value.name().equalsIgnoreCase(genre)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Genre not found! - " + genre);
    }
}
