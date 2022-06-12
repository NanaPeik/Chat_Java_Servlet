package ge.tsu.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {

    private final String name;
    private final String text;
    private final LocalDateTime time;

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
        time = LocalDateTime.now();
    }

    public Message(String name, String text, LocalDateTime time) {
        this.name = name;
        this.text = text;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(name, message.name) && Objects.equals(text, message.text) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, time);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s]: %s", time, name, text);
    }
}
