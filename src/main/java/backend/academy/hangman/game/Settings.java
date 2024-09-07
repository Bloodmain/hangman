package backend.academy.hangman.game;

import backend.academy.hangman.game.word.Categories;
import backend.academy.hangman.game.word.Difficulties;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.game.word.WordClassification;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Settings {
    public final static List<Word> DEFAULT_WORDS = List.of(
        new Word("tree", "A big plant",
            new WordClassification(Categories.NATURE, Difficulties.EASY)),
        new Word("leaf", "Part of a tree",
            new WordClassification(Categories.NATURE, Difficulties.EASY)),
        new Word("grass", "Grows on a lawn",
            new WordClassification(Categories.NATURE, Difficulties.EASY)),
        new Word("hedge", "Grows close to the ground",
            new WordClassification(Categories.NATURE, Difficulties.MEDIUM)),
        new Word("bamboo", "Looks like long stalks of wood",
            new WordClassification(Categories.NATURE, Difficulties.MEDIUM)),
        new Word("daffodil", "Has small yellow petals",
            new WordClassification(Categories.NATURE, Difficulties.MEDIUM)),
        new Word("dandelion", "Has yellow flowers.",
            new WordClassification(Categories.NATURE, Difficulties.HARD)),
        new Word("lawnmower", "Related to lawn",
            new WordClassification(Categories.NATURE, Difficulties.HARD)),
        new Word("wheelbarrow", "Helps to carry materials",
            new WordClassification(Categories.NATURE, Difficulties.HARD)),

        new Word("laptop", "Small computer",
            new WordClassification(Categories.PROGRAMMING, Difficulties.EASY)),
        new Word("code", "Hope it is not in Russian",
            new WordClassification(Categories.PROGRAMMING, Difficulties.EASY)),
        new Word("java", "A programming language",
            new WordClassification(Categories.PROGRAMMING, Difficulties.EASY)),
        new Word("encapsulation", "OOP principle",
            new WordClassification(Categories.PROGRAMMING, Difficulties.MEDIUM)),
        new Word("eclipse", "IDE",
            new WordClassification(Categories.PROGRAMMING, Difficulties.MEDIUM)),
        new Word("refactoring", "Process that never happens",
            new WordClassification(Categories.PROGRAMMING, Difficulties.MEDIUM)),
        new Word("mutable", "A c++ keyword",
            new WordClassification(Categories.PROGRAMMING, Difficulties.HARD)),
        new Word("comonad", "Haskell related",
            new WordClassification(Categories.PROGRAMMING, Difficulties.HARD)),
        new Word("slicing", "c++ inheritance difficulty",
            new WordClassification(Categories.PROGRAMMING, Difficulties.HARD)),

        new Word("teacher", "School worker",
            new WordClassification(Categories.JOBS, Difficulties.EASY)),
        new Word("butcher", "Has a big knife",
            new WordClassification(Categories.JOBS, Difficulties.EASY)),
        new Word("doctor", "Likes white robe",
            new WordClassification(Categories.JOBS, Difficulties.EASY)),
        new Word("physician", "Forgot how to write",
            new WordClassification(Categories.JOBS, Difficulties.MEDIUM)),
        new Word("accountant", "Works with money",
            new WordClassification(Categories.JOBS, Difficulties.MEDIUM)),
        new Word("jeweller", "Has many diamonds",
            new WordClassification(Categories.JOBS, Difficulties.MEDIUM)),
        new Word("labourer", "Unskilled worker",
            new WordClassification(Categories.JOBS, Difficulties.HARD)),
        new Word("stuntman", "Likes to jump",
            new WordClassification(Categories.JOBS, Difficulties.HARD)),
        new Word("seamstress", "Has a needle",
            new WordClassification(Categories.JOBS, Difficulties.HARD))
    );

    public static final String EXIT_CODE = "e!";
    public static final String HELP_CODE = "h!";
    public static final String NEW_GAME_CODE = "y";
    public static final String QUIT_GAME_CODE = "n";
    public static final String RANDOM_CHOOSE_CODE = "";

    public static final String HIDDEN_LETTER = "_";

    public static final Map<String, Difficulties> DIFFICULTIES_CHOOSE_NUMBER = Map.of(
        "1", Difficulties.EASY,
        "2", Difficulties.MEDIUM,
        "3", Difficulties.HARD
    );
    public static final Map<String, Categories> CATEGORIES_CHOOSE_NUMBER = Map.of(
        "1", Categories.NATURE,
        "2", Categories.PROGRAMMING,
        "3", Categories.JOBS
    );

    public static final String CHOOSE_TEMPLATE = "%s. %s";
    public static final String HINT_TEMPLATE = "Hint: %s";
    public static final String WORD_TEMPLATE = "Word: %s";

    public static final String OUTPUT_DELIMITER = "-".repeat(50);

    public static final String CATEGORY_CHOSEN_TEMPLATE = "Category \"%s\" has been chosen.";
    public static final String DIFFICULTY_CHOSEN_TEMPLATE = "Difficulty \"%s\" has been chosen. You have %d attempts.";
    public static final String ATTEMPTS_LEFT_TEMPLATE = "You have %d attempts left.";

    public static final String MENU_TEXT = """
        Welcome to the Hangman game! The rules are pretty simple: you have to guess a word by entering letters.
        If the hidden word contains the letter you've inputted, than all its occurrencies are shown.
        Otherwise, the attempt burns out. Shall we begin?
        """;

    public static final String MENU_PROMPT =
        "Enter \"%s\" to start a new game or \"%s\" to exit: ".formatted(NEW_GAME_CODE, QUIT_GAME_CODE);

    public static final String CATEGORY_CHOOSE_TEXT = "First, choose a category of hidden word.";
    public static final String CATEGORY_CHOOSE_PROMPT =
        ("Enter a number of a category or an empty line to choose a random category. "
            + "Also you may enter \"%s\" to exit the game: ")
            .formatted(EXIT_CODE);

    public static final String DIFFICULTY_CHOOSE_TEXT =
        "Now, lets choose a difficulty. It affects the hidden words and maximum attempts.";
    public static final String DIFFICULTY_CHOOSE_PROMPT =
        "Enter a number or an empty line to a choose random difficulty. Also you may enter \"%s\" to exit the game: "
            .formatted(EXIT_CODE);

    public static final String BAD_INPUT_MESSAGE = "Sorry, incorrect input. Please, try again.";

    public static final String WIN_MESSAGE = "Congrats! You are a winner!";
    public static final String LOOSE_MESSAGE =
        "Unfortunately, you're not succeded :(";

    public static final String GAME_PROMPT =
        "Enter a letter / \"%s\" for a hint / \"%s\" to exit the game: ".formatted(HELP_CODE, EXIT_CODE);
    public static final String ENDING_PROMPT = "Enter any key to return to the menu or \"e!\" to exit the game: ";

    public static final Map<Integer, String> HANGMAN_STATES = Map.of(
        0, """
              |/--------
              |
              |
              |
              |
              |
              |
              |
            __|________
            """,
        1, """
              |/--------
              |      |
              |     (_)
              |
              |
              |
              |
              |
            __|________
            """,
        2, """
              |/--------
              |      |
              |     (_)
              |      |
              |      |
              |
              |
              |
            __|________
            """,
        3, """
              |/--------
              |      |
              |     (_)
              |     /|
              |    / |
              |
              |
              |
            __|________
            """,
        4, """
              |/--------
              |      |
              |     (_)
              |     /|\\
              |    / | \\
              |
              |
              |
            __|________
            """,
        5, """
              |/--------
              |      |
              |     (_)
              |     /|\\
              |    / | \\
              |     /
              |    /
              |
            __|________
            """,
        6, """
              |/--------
              |      |
              |     (_)
              |     /|\\
              |    / | \\
              |     / \\
              |    /   \\
              |
            __|________
            """
    );

    public static final int MAX_HANGMAN_STATE = HANGMAN_STATES.size() - 1;
    /**
     * Tries have to be in the range [1..{@link #MAX_HANGMAN_STATE}]
     */
    public static final Map<Difficulties, Integer> TRIES = Map.of(
        Difficulties.EASY, 6,
        Difficulties.MEDIUM, 4,
        Difficulties.HARD, 2
    );
}
