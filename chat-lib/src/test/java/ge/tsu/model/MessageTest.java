package ge.tsu.model;

import ge.tsu.model.provider.MessagesProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    Message message;

    @ParameterizedTest
    @ValueSource(strings = {"no color", ""})
    @DisplayName("Test not contains colors")
    void getPrefix(String argument) {
        message = new Message("user", argument);
        assertNull(message.getPrefix());
    }

    @ParameterizedTest
    @ArgumentsSource(MessagesProvider.class)
    @DisplayName("Test existing colors")
    void getColorPrefix(String argument) {
        message = new Message("user", argument);
        assertNotNull(message.getPrefix());
    }

    @ParameterizedTest
    @ValueSource(strings = {"key", ""})
    @DisplayName("Test wrong color key")
    void readColorCode(String key) {
        assertThrows(NullPointerException.class, () -> message.readColorCode(key));
    }
}