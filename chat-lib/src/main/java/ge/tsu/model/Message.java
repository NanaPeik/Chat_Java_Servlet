package ge.tsu.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Properties;

public class Message implements Serializable {

    private final String name;
    private final String text;
    private final String time;

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
        LocalTime localTime = LocalDateTime.now().toLocalTime();
        time = localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond();
    }

    public Message(String name, String text, String time) {
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

    public String getTime() {
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

    public String readColorCode(String colorKey) throws IOException {
        String rootFolderPath = null;
        try (var inputStream = Message.class.getResourceAsStream("/config.properties")) {
            if (inputStream != null) {
                // Reading properties
                var props = new Properties();
                props.load(inputStream);
                rootFolderPath = props.getProperty(colorKey, "");
            }
        } catch (NullPointerException e) {
            throw new IOException(e);
        }
        return rootFolderPath;
    }

    @Override
    public String toString() {
        String result = null;
        String suffix = null;
        try {
            suffix = readColorCode("reset");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String prefix = getPrefix();

        if (prefix != null)
            result = String.format("[%s][%s]:%s %s%s", time, name, prefix, text, suffix);

        if (result != null) return result;
        else return String.format("[%s][%s]: %s", time, name, text);
    }

    public String getPrefix() {
        String prefix = null;
        //red, green, yellow, blue, purple, cyan, white
        //text gets color according this sequence
        try {
            if (text.contains(Color.red.getValue())) {
                prefix = readColorCode(Color.red.getValue());
            } else if (text.contains(Color.green.getValue())) {
                prefix = readColorCode(Color.green.getValue());
            } else if (text.contains(Color.yellow.getValue())) {
                prefix = readColorCode(Color.yellow.getValue());
            } else if (text.contains(Color.blue.getValue())) {
                prefix = readColorCode(Color.blue.getValue());
            } else if (text.contains(Color.purple.getValue())) {
                prefix = readColorCode(Color.purple.getValue());
            } else if (text.contains(Color.cyan.getValue())) {
                prefix = readColorCode(Color.cyan.getValue());
            } else if (text.contains(Color.white.getValue())) {
                prefix = readColorCode(Color.white.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prefix;
    }
}
