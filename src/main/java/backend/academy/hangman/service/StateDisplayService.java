package backend.academy.hangman.service;

import backend.academy.hangman.game.Settings;
import backend.academy.hangman.game.state.ChooseCategoryState;
import backend.academy.hangman.game.state.ChooseDifficultyState;
import backend.academy.hangman.game.state.HangmanState;
import backend.academy.hangman.game.state.MenuState;
import backend.academy.hangman.game.state.State;
import backend.academy.hangman.game.state.vital.GameSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StateDisplayService {
    public String prepareStateToDisplay(State state) {
        return switch (state) {
            case MenuState menuState -> prepareMenu();
            case ChooseCategoryState categoryState -> prepareCategory();
            case ChooseDifficultyState difficultyState -> prepareDifficulty();
            case HangmanState hangmanState -> prepareHangman(hangmanState);
        };
    }

    private String prepareMenu() {
        return joinNewline(
            Settings.MENU_TEXT,
            Settings.MENU_PROMPT
        );
    }

    private String prepareCategory() {
        return chooseTemplate(Settings.CATEGORY_CHOOSE_TEXT, Settings.CATEGORY_CHOOSE_PROMPT,
            Settings.CATEGORIES_CHOOSE_NUMBER);
    }

    private String prepareDifficulty() {
        return chooseTemplate(Settings.DIFFICULTY_CHOOSE_TEXT, Settings.DIFFICULTY_CHOOSE_PROMPT,
            Settings.DIFFICULTIES_CHOOSE_NUMBER);
    }

    private String prepareHangman(HangmanState state) {
        List<String> lines = new ArrayList<>();
        GameSession session = state.status().session();

        lines.add(Settings.ATTEMPTS_LEFT_TEMPLATE.formatted(session.maxTries() - session.guessedWrongly()));
        lines.add(Settings.HANGMAN_STATES.get(state.hangmanSubState()));

        if (state.isHintEnabled()) {
            lines.add(Settings.HINT_TEMPLATE.formatted(session.answer().hint()));
        }

        lines.add(Settings.WORD_TEMPLATE.formatted(displayHiddenWord(state)));

        if (session.isSessionEnded()) {
            lines.add(Settings.ENDING_PROMPT);
        } else {
            lines.add(Settings.GAME_PROMPT);
        }

        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private String displayHiddenWord(State state) {
        GameSession session = state.status().session();
        String word = session.answer().word();
        Set<Character> guessedLetters = session.guessedCorrectly();

        return word.chars()
            .mapToObj(c -> (char) c)
            .map(c -> guessedLetters.contains(c) ? String.valueOf(c) : Settings.HIDDEN_LETTER)
            .collect(Collectors.joining());
    }

    private static String joinNewline(String... lines) {
        return String.join(System.lineSeparator(), lines);
    }

    private static <T> String chooseTemplate(String text, String prompt, Map<String, T> chooseOptions) {
        String categories = chooseOptions.entrySet()
            .stream()
            .map(e -> Settings.CHOOSE_TEMPLATE.formatted(e.getKey(), e.getValue()))
            .sorted()
            .collect(Collectors.joining(System.lineSeparator()));

        return joinNewline(text, categories, prompt);
    }
}
