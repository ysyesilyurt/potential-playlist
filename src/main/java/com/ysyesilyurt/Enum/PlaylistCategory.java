package com.ysyesilyurt.Enum;

public enum PlaylistCategory {
    Focus("Focus"),
    Chill("Chill"),
    Classical("Classical"),
    Happiness("Happiness"),
    Mood("Mood"),
    Workout("Workout");

    private String displayName;

    PlaylistCategory(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
