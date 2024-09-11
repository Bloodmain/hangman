package backend.academy.hangman.game.io;

import backend.academy.hangman.game.state.State;
import backend.academy.hangman.service.StateDisplayService;
import java.io.IOException;

public interface UI {
    void displayState(StateDisplayService service, State state) throws IOException;

    String getInput() throws IOException;
}
