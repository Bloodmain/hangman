package backend.academy.hangman.repository;

import backend.academy.hangman.game.word.Categories;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.game.word.Difficulties;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.game.word.WordClassification;
import org.jspecify.annotations.NonNull;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordDictionary implements Dictionary {
    private final static List<Word> DEFAULT_WORDS = List.of(
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

    /**
     * For the fast realisation of {@link #getRandomWord(WordClassification)}
     */
    private final Map<WordClassification, List<Word>> classifiedWords;

    public WordDictionary() {
        this(DEFAULT_WORDS);
    }

    public WordDictionary(@NonNull List<Word> words) {
        classifiedWords = words.stream()
            .collect(Collectors.groupingBy(Word::classification));
    }

    private final SecureRandom rnd = new SecureRandom();

    @Override
    public Word getRandomWord(@NonNull WordClassification classification) {
        List<Word> words = classifiedWords.get(classification);
        return words.get(rnd.nextInt(words.size()));
    }
}
