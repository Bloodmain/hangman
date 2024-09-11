package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import org.jspecify.annotations.NonNull;

public final class ChooseDifficultyState extends State {
    public ChooseDifficultyState(@NonNull GameStatus status) {
        super(status);
    }
}
