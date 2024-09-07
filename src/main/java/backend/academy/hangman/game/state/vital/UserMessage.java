package backend.academy.hangman.game.state.vital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMessage {
    private String message;
    private MessageLevel level;
}
