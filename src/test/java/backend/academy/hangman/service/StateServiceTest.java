package backend.academy.hangman.service;

import backend.academy.hangman.game.state.ChooseCategoryState;
import backend.academy.hangman.game.state.ChooseDifficultyState;
import backend.academy.hangman.game.state.HangmanState;
import backend.academy.hangman.game.state.MenuState;
import backend.academy.hangman.game.state.State;
import backend.academy.hangman.game.state.vital.GameSession;
import backend.academy.hangman.game.state.vital.GameStatus;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.repository.WordDictionary;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class StateServiceTest {
    private static Stream<State> provideAllStates() {
        return Stream.of(
            new MenuState(new GameStatus()),
            new ChooseCategoryState(new GameStatus()),
            new ChooseDifficultyState(new GameStatus()),
            new HangmanState(new GameStatus())
        );
    }

    @ParameterizedTest
    @MethodSource("provideAllStates")
    @DisplayName("game status is ended after inputting an exit code")
    public void exitGame(State state) {
        StateService service = new StateService(new WordDictionary());

        State newState = service.processInput(state, "e!");

        assertThat(newState.status().isGameActive()).isEqualTo(false);
    }

    @ParameterizedTest
    @DisplayName("game state does not change with incorrect input")
    @ValueSource(strings = {"hey", "you", "!!", "jshgpiudfg", "drop table users;", "//", "12", "System.exit();"})
    public void incorrectInput(String input) {
        StateService service = new StateService(new WordDictionary());
        GameSession spySession = spy(Instancio.of(GameSession.class)
            .ignore(field("isSessionEnded"))
            .create());
        GameStatus spyStatus = spy(new GameStatus());
        State spyState = spy(Instancio.of(State.class)
            .set(field("status"), spyStatus)
            .create());
        spyStatus.session(spySession);

        doThrow(IllegalAccessError.class).when(spyState).exitGame();
        doThrow(IllegalAccessError.class).when(spyStatus).isGameActive(anyBoolean());
        doThrow(IllegalAccessError.class).when(spyStatus).isGameActive(anyBoolean());
        doThrow(IllegalAccessError.class).when(spySession).maxTries(anyInt());
        doThrow(IllegalAccessError.class).when(spySession).answer(any());
        doThrow(IllegalAccessError.class).when(spySession).addWrongGuess();
        doThrow(IllegalAccessError.class).when(spySession).addCorrectGuess(anyChar());

        State newState = assertDoesNotThrow(() -> service.processInput(spyState, input));

        assertThat(newState).isSameAs(spyState);
    }

    @Test
    @DisplayName("game ends in case of wrong guesses == max tries")
    public void playerLoose() {
        StateService service = new StateService(new WordDictionary());
        State state = Instancio.of(HangmanState.class)
            .ignore(field(GameSession.class, "isSessionEnded"))
            .ignore(field(GameSession.class, "guessedWrongly"))
            .set(field(GameSession.class, "maxTries"), 5)
            .set(field(Word.class, "word"), "ab")
            .create();

        for (int i = 0; i < 5; ++i) {
            state = service.processInput(state, "c");
        }

        assertThat(state.status().session().isSessionEnded()).isEqualTo(true);
        assertThat(state.status().session().guessedWrongly()).isEqualTo(5);
    }

    @Test
    @DisplayName("game ends in case of guessing all letters")
    public void playerWin() {
        StateService service = new StateService(new WordDictionary());
        State state = Instancio.of(HangmanState.class)
            .ignore(field(GameSession.class, "isSessionEnded"))
            .ignore(field(GameSession.class, "guessedWrongly"))
            .set(field(GameSession.class, "maxTries"), 5)
            .set(field(Word.class, "word"), "aba")
            .create();

        state = service.processInput(state, "a");
        state = service.processInput(state, "c");
        state = service.processInput(state, "b");

        assertThat(state.status().session().isSessionEnded()).isEqualTo(true);
        assertThat(state.status().session().guessedWrongly()).isEqualTo(1);
    }

    @Test
    @DisplayName("game returns to the menu when ended")
    public void returnToMenu() {
        StateService service = new StateService(new WordDictionary());
        State state = Instancio.of(HangmanState.class)
            .set(field(GameSession.class, "isSessionEnded"), true)
            .create();

        state = service.processInput(state, "svsvfdsv");

        assertThat(state).isInstanceOf(MenuState.class);
    }

    @Test
    @DisplayName("correct updating hangman substates")
    public void correctUpdatingHangmanSubstates() {
        StateService service = new StateService(new WordDictionary());
        HangmanState state = Instancio.of(HangmanState.class)
            .ignore(field(GameSession.class, "isSessionEnded"))
            .ignore(field(GameSession.class, "guessedWrongly"))
            .set(field(GameSession.class, "maxTries"), 4)
            .set(field("hangmanSubState"), 0)
            .set(field(Word.class, "word"), "ab")
            .create();

        state = (HangmanState) service.processInput(state, "c");
        assertThat(state.hangmanSubState()).isEqualTo(2);

        state = (HangmanState) service.processInput(state, "c");
        assertThat(state.hangmanSubState()).isEqualTo(4);

        state = (HangmanState) service.processInput(state, "c");
        assertThat(state.hangmanSubState()).isEqualTo(5);

        state = (HangmanState) service.processInput(state, "c");
        assertThat(state.hangmanSubState()).isEqualTo(6);
    }
}
