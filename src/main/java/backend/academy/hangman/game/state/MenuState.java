package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import org.jspecify.annotations.NonNull;

public final class MenuState extends State {
    public MenuState(@NonNull GameStatus status) {
        super(status);
    }
}
