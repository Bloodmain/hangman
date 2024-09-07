package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public final class HangmanState extends State {
    private int hangmanSubState = 0;
    private boolean isHintEnabled = false;

    public HangmanState(GameStatus status) {
        super(status);
    }
}
