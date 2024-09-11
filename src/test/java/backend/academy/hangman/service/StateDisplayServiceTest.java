package backend.academy.hangman.service;

import backend.academy.hangman.game.state.ChooseCategoryState;
import backend.academy.hangman.game.state.ChooseDifficultyState;
import backend.academy.hangman.game.state.HangmanState;
import backend.academy.hangman.game.state.vital.GameSession;
import backend.academy.hangman.game.word.Word;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

public class StateDisplayServiceTest {
    @Test
    @DisplayName("shows all categories")
    public void categoryShows() {
        StateDisplayService service = new StateDisplayService();
        ChooseCategoryState state = Instancio.create(ChooseCategoryState.class);

        String actual = service.prepareStateToDisplay(state);

        assertThat(actual).contains("NATURE", "JOBS", "PROGRAMMING");
    }

    @Test
    @DisplayName("shows all difficulties")
    public void difficultyShows() {
        StateDisplayService service = new StateDisplayService();
        ChooseDifficultyState state = Instancio.create(ChooseDifficultyState.class);

        String actual = service.prepareStateToDisplay(state);

        assertThat(actual).contains("EASY", "MEDIUM", "HARD");
    }

    @Test
    @DisplayName("shows attempts left")
    public void attemptsShows() {
        StateDisplayService service = new StateDisplayService();
        HangmanState state = Instancio.of(HangmanState.class)
            .set(field(GameSession.class, "maxTries"), 125)
            .set(field(GameSession.class, "guessedWrongly"), 31)
            .create();

        String actual = service.prepareStateToDisplay(state);

        assertThat(actual).contains("94");
    }

    @Test
    @DisplayName("shows hint")
    public void hintShows() {
        StateDisplayService service = new StateDisplayService();
        HangmanState state = Instancio.of(HangmanState.class)
            .set(field(Word.class, "hint"), "it's a super hint!")
            .set(field("isHintEnabled"), true)
            .create();

        String actual = service.prepareStateToDisplay(state);

        assertThat(actual).contains("it's a super hint!");
    }

    @Test
    @DisplayName("doesn't show hint when it's disabled")
    public void doesNotShowHint() {
        StateDisplayService service = new StateDisplayService();
        HangmanState state = Instancio.of(HangmanState.class)
            .set(field(Word.class, "hint"), "it's a super hint!")
            .set(field("isHintEnabled"), false)
            .create();

        String actual = service.prepareStateToDisplay(state);

        assertThat(actual).doesNotContain("it's a super hint!");
    }

    @Test
    @DisplayName("shows hidden word correctly")
    public void wordShows() {
        StateDisplayService service = new StateDisplayService();
        HangmanState state = Instancio.of(HangmanState.class)
            .set(field(Word.class, "word"), "abacaba")
            .set(field(GameSession.class, "guessedCorrectly"), new HashSet<>())
            .create();

        String actual = service.prepareStateToDisplay(state);
        assertThat(actual).contains("_______");

        state.status().session().addCorrectGuess('c');
        actual = service.prepareStateToDisplay(state);
        assertThat(actual).contains("___c___");

        state.status().session().addCorrectGuess('b');
        actual = service.prepareStateToDisplay(state);
        assertThat(actual).contains("_b_c_b_");

        state.status().session().addCorrectGuess('a');
        actual = service.prepareStateToDisplay(state);
        assertThat(actual).contains("abacaba");
    }
}
