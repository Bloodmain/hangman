package backend.academy.hangman.service;

import backend.academy.hangman.game.Settings;
import backend.academy.hangman.game.state.ChooseCategoryState;
import backend.academy.hangman.game.state.ChooseDifficultyState;
import backend.academy.hangman.game.state.HangmanState;
import backend.academy.hangman.game.state.MenuState;
import backend.academy.hangman.game.state.State;
import backend.academy.hangman.game.state.vital.GameSession;
import backend.academy.hangman.game.state.vital.MessageLevel;
import backend.academy.hangman.game.state.vital.UserMessage;
import backend.academy.hangman.game.word.Categories;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.game.word.Difficulties;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.game.word.WordClassification;
import java.security.SecureRandom;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class StateService {
    @NonNull private final Dictionary dict;
    private final SecureRandom rnd = new SecureRandom();

    public State processInput(@NonNull State state, @NonNull String input) {
        return switch (state) {
            case MenuState menuState -> processMenuInput(menuState, input);
            case ChooseCategoryState chooseCategoryState -> processCategoryInput(chooseCategoryState, input);
            case ChooseDifficultyState chooseDifficultyState -> processDifficultyInput(chooseDifficultyState, input);
            case HangmanState hangmanState -> processHangmanInput(hangmanState, input);
        };
    }

    private State processMenuInput(@NonNull MenuState state, @NonNull String input) {
        switch (input) {
            case Settings.NEW_GAME_CODE -> {
                GameSession session = new GameSession(new Word());
                state.status().session(session);
                return new ChooseCategoryState(state.status());
            }
            case Settings.QUIT_GAME_CODE, Settings.EXIT_CODE -> {
                state.exitGame();
                return state;
            }
            default -> {
                state.status().userMessage(new UserMessage(Settings.BAD_INPUT_MESSAGE, MessageLevel.ERROR));
                return state;
            }
        }
    }

    private State processCategoryInput(@NonNull ChooseCategoryState state, @NonNull String input) {
        if (Settings.EXIT_CODE.equals(input)) {
            state.exitGame();
            return state;
        }

        if (Settings.RANDOM_CHOOSE_CODE.equals(input) || Settings.CATEGORIES_CHOOSE_NUMBER.containsKey(input)) {
            Categories category = getOrRandomEnum(Settings.CATEGORIES_CHOOSE_NUMBER, input, Categories.class);
            WordClassification classification = new WordClassification();

            state.status().session().answer().classification(classification);
            classification.category(category);

            String categoryMessage = Settings.CATEGORY_CHOSEN_TEMPLATE.formatted(category);
            state.status().userMessage(new UserMessage(categoryMessage, MessageLevel.INFO));

            return new ChooseDifficultyState(state.status());
        }

        state.status().userMessage(new UserMessage(Settings.BAD_INPUT_MESSAGE, MessageLevel.ERROR));
        return state;
    }

    private State processDifficultyInput(@NonNull ChooseDifficultyState state, @NonNull String input) {
        if (Settings.EXIT_CODE.equals(input)) {
            state.exitGame();
            return state;
        }

        if (Settings.RANDOM_CHOOSE_CODE.equals(input) || Settings.DIFFICULTIES_CHOOSE_NUMBER.containsKey(input)) {
            Difficulties difficulty = getOrRandomEnum(Settings.DIFFICULTIES_CHOOSE_NUMBER, input, Difficulties.class);
            GameSession session = state.status().session();
            WordClassification classification = session.answer().classification();
            int maxTries = Settings.TRIES.get(difficulty);

            classification.difficulty(difficulty);
            session.answer(dict.getRandomWord(classification));

            session.maxTries(maxTries);

            String difficultyMessage = Settings.DIFFICULTY_CHOSEN_TEMPLATE.formatted(difficulty, maxTries);
            state.status().userMessage(new UserMessage(difficultyMessage, MessageLevel.INFO));

            return new HangmanState(state.status());
        }

        state.status().userMessage(new UserMessage(Settings.BAD_INPUT_MESSAGE, MessageLevel.ERROR));
        return state;
    }

    private State processHangmanInput(@NonNull HangmanState state, @NonNull String input) {
        GameSession session = state.status().session();

        if (Settings.EXIT_CODE.equals(input)) {
            state.exitGame();
            return state;
        } else if (session.isSessionEnded()) {
            state.status().resetSession();
            return new MenuState(state.status());
        } else if (Settings.HELP_CODE.equals(input)) {
            state.isHintEnabled(true);
            return state;
        }

        if (input.length() != 1) {
            state.status().userMessage(new UserMessage(Settings.BAD_INPUT_MESSAGE, MessageLevel.ERROR));

        } else if (session.isCorrectLetter(input.charAt(0))) {
            session.addCorrectGuess(input.charAt(0));
            if (session.isSessionEnded()) {
                state.status().userMessage(new UserMessage(Settings.WIN_MESSAGE, MessageLevel.CONGRATS));
            }

        } else {
            /*
             Smoothly updating the "hangman figure" state. For example,
             if max tries is set to 4 and the max substate is 6,
             then the substate will iterate over 2, 4, 5, 6 while guessing wrong.
             And if we have max tries equals to 6, then we will reach every (1,2,3,4,5,6) state
             */
            int triesLeft = session.maxTries() - session.guessedWrongly();

            session.addWrongGuess();

            int subStateNow = state.hangmanSubState();
            int step = ceilDiv(Settings.MAX_HANGMAN_STATE - subStateNow, triesLeft);
            int nextSubState = subStateNow + step;

            if (session.isSessionEnded()) {
                state.status().userMessage(new UserMessage(Settings.LOOSE_MESSAGE, MessageLevel.ERROR));
            }
            state.hangmanSubState(nextSubState);

        }
        return state;
    }

    private <T extends Enum<?>> T getOrRandomEnum(Map<String, T> map, String key, Class<T> token) {
        if (!map.containsKey(key)) {
            T[] values = token.getEnumConstants();
            return values[rnd.nextInt(values.length)];
        }
        return map.get(key);
    }

    /**
     * Divides a by b, ceiling the result
     *
     * @param a dividend
     * @param b divider
     * @return a rounded up division
     */
    private static int ceilDiv(int a, int b) {
        return (a + b - 1) / b;
    }
}
