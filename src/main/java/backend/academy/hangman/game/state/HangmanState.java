package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

@Setter @Getter
public final class HangmanState extends State {
    private int hangmanSubState = 0;
    private boolean isHintEnabled = false;

    public HangmanState(@NonNull GameStatus status) {
        super(status);
    }
}
