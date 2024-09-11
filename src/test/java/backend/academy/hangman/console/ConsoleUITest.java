package backend.academy.hangman.console;

import backend.academy.hangman.game.io.UI;
import backend.academy.hangman.game.state.HangmanState;
import backend.academy.hangman.game.state.vital.UserMessage;
import backend.academy.hangman.service.StateDisplayService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConsoleUITest {
    @Test
    @DisplayName("case insensitivity")
    public void caseInsensitivity() {
        Scanner input = new Scanner("G");
        UI ui = new ConsoleUI(input, new PrintWriter(OutputStream.nullOutputStream()));

        String actual = assertDoesNotThrow(ui::getInput);

        assertThat(actual).isEqualTo("g");
    }

    @Test
    @DisplayName("displays at least the state")
    public void displayState() {
        StateDisplayService service = new StateDisplayService();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UI ui = new ConsoleUI(new Scanner(InputStream.nullInputStream()), new PrintWriter(out));
        HangmanState state = Instancio.create(HangmanState.class);

        assertDoesNotThrow(() -> ui.displayState(service, state));

        assertThat(out.toString()).contains(service.prepareStateToDisplay(state));
    }

    @Test
    @DisplayName("user message displaying")
    public void displayUserMessage() {
        StateDisplayService service = new StateDisplayService();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UI ui = new ConsoleUI(new Scanner(InputStream.nullInputStream()), new PrintWriter(out));
        HangmanState state = Instancio.of(HangmanState.class)
            .set(field(UserMessage.class, "message"), "Hey, i am the message from the past!;")
            .create();

        assertDoesNotThrow(() -> ui.displayState(service, state));

        assertThat(out.toString()).contains("Hey, i am the message from the past!;");
    }
}
