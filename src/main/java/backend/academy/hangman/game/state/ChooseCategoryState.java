package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import org.jspecify.annotations.NonNull;

public final class ChooseCategoryState extends State {
    public ChooseCategoryState(@NonNull GameStatus status) {
        super(status);
    }
}
