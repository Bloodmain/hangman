package backend.academy.hangman.game.state.vital;

import backend.academy.hangman.game.word.Word;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

@Getter
public class GameSession {
    private final Set<Character> guessedCorrectly = new HashSet<>();

    @Setter
    private Word answer;

    @Setter
    private int maxTries;

    private int guessedWrongly = 0;

    private boolean isSessionEnded = false;

    public GameSession(@NonNull Word answer) {
        this.answer = answer;
    }

    public void addWrongGuess() {
        guessedWrongly++;
        if (guessedWrongly == maxTries) {
            isSessionEnded = true;
        }
    }

    public void addCorrectGuess(char letter) {
        guessedCorrectly.add(letter);
        if (checkIfWordGuessed()) {
            isSessionEnded = true;
        }
    }

    public boolean isCorrectLetter(char letter) {
        return answer.word().indexOf(letter) != -1;
    }

    private boolean checkIfWordGuessed() {
        String word = answer.word();
        return word.chars().allMatch(c -> guessedCorrectly.contains((char) c));
    }
}
