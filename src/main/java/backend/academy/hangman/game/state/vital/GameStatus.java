package backend.academy.hangman.game.state.vital;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameStatus {
    private boolean isGameActive = true;
    private GameSession session;

    /**
     * Message to display for user in case of an error/just info
     */
    private UserMessage userMessage = new UserMessage("", MessageLevel.INFO);

    public void resetSession() {
        session = null;
    }
}
