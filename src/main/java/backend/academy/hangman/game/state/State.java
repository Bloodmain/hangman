package backend.academy.hangman.game.state;

import backend.academy.hangman.game.state.vital.GameStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract sealed class State permits MenuState, ChooseCategoryState, ChooseDifficultyState, HangmanState {
    private final GameStatus status;

    public void exitGame() {
        status.isGameActive(false);
    }
}
