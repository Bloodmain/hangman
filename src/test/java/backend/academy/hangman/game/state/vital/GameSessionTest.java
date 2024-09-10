package backend.academy.hangman.game.state.vital;

import backend.academy.hangman.game.word.Word;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.function.Predicate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;

public class GameSessionTest {
    @Test
    @DisplayName("guesses correct letters")
    public void correctGuesses() {
        Word word = Instancio.create(Word.class);
        GameSession session = new GameSession(word);
        session.maxTries(1);

        word.word().chars().forEach(c -> session.addCorrectGuess((char) c));

        assertThat(session.guessedWrongly()).isEqualTo(0);
        assertThat(session.isSessionEnded()).isEqualTo(true);
    }

    @Test
    @DisplayName("guesses incorrect letters")
    public void wrongGuesses() {
        Word word = Instancio.create(Word.class);
        GameSession session = new GameSession(word);
        session.maxTries(5);

        for (int i = 0; i < 5; ++i) {
            session.addWrongGuess();
        }

        assertThat(session.guessedWrongly()).isEqualTo(5);
        assertThat(session.isSessionEnded()).isEqualTo(true);
    }

    @Test
    @DisplayName("Check if the letter is correct")
    public void checkForLetterCorrectness() {
        Word word = Instancio.of(Word.class).set(all(String.class), "abcklo").create();
        GameSession session = new GameSession(word);
        Predicate<Character> predicate = session::isCorrectLetter;

        assertThat(predicate).accepts('a', 'b', 'c', 'k', 'l', 'o');
        assertThat(predicate).rejects('e', 'f', 'g', 'm', 'n', 'q', '1', '0', '$', 'z');
    }
}
