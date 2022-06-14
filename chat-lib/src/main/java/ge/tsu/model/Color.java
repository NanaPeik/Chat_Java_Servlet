package ge.tsu.model;

import java.util.Objects;

public enum Color {
    red("red"),
    green("green"),
    yellow("yellow"),
    blue("blue"),
    purple("purple"),
    cyan("cyan"),
    white("white");

    private String value;

    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Color get(String value) {
        for (Color color : Color.values()) {
            if (Objects.equals(value, color.value))
                return color;
        }
        return null;
    }
}
