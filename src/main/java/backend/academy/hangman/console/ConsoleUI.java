package backend.academy.hangman.console;

import backend.academy.hangman.game.Settings;
import backend.academy.hangman.game.io.UI;
import backend.academy.hangman.game.state.State;
import backend.academy.hangman.game.state.vital.GameStatus;
import backend.academy.hangman.game.state.vital.MessageLevel;
import backend.academy.hangman.game.state.vital.UserMessage;
import backend.academy.hangman.service.StateDisplayService;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import java.io.IOException;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

/**
 * Provides console UI for the game. Requires a scanner and a writer to put the information in.
 * Note: this class does NOT close the given scanner and writer.
 */
@RequiredArgsConstructor
public class ConsoleUI implements UI {
    @NonNull private final Scanner in;
    @NonNull private final Writer out;

    @Override
    public void displayState(@NonNull StateDisplayService service, @NonNull State state) throws IOException {
        String toDisplay = service.prepareStateToDisplay(state);
        GameStatus status = state.status();

        out.write(Ansi.colorize(Settings.OUTPUT_DELIMITER, Attribute.BLUE_TEXT()));
        displayNewline();

        if (!status.userMessage().message().isBlank()) {
            out.write(coloredMessage(status.userMessage()));

            status.userMessage(new UserMessage("", MessageLevel.INFO));
            displayNewline();
            displayNewline();
        }

        out.write(toDisplay);
        out.flush();
    }

    @Override
    public String getInput() throws IOException {
        try {
            return in.nextLine().toLowerCase();
        } catch (final NoSuchElementException e) {
            throw new IOException("No input", e);
        } catch (final IllegalStateException e) {
            throw new IOException("Provided scanner is closed", e);
        }
    }

    private void displayNewline() throws IOException {
        out.write(System.lineSeparator());
    }

    private String coloredMessage(UserMessage message) {
        Attribute color = switch (message.level()) {
            case INFO -> Attribute.BLUE_TEXT();
            case ERROR -> Attribute.RED_TEXT();
            case CONGRATS -> Attribute.GREEN_TEXT();
        };
        return Ansi.colorize(message.message(), color);
    }
}
