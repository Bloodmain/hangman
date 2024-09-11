package backend.academy.hangman.game;

import backend.academy.hangman.game.io.UI;
import backend.academy.hangman.game.state.MenuState;
import backend.academy.hangman.game.state.State;
import backend.academy.hangman.game.state.vital.GameStatus;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.service.StateDisplayService;
import backend.academy.hangman.service.StateService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class GameController {
    @NonNull private final UI ui;
    @NonNull private final Dictionary dict;

    public void play() throws IOException {
        State state = new MenuState(new GameStatus());
        StateService service = new StateService(dict);
        StateDisplayService displayService = new StateDisplayService();

        while (state.status().isGameActive()) {
            ui.displayState(displayService, state);
            String input = ui.getInput();
            state = service.processInput(state, input);
        }
    }
}
