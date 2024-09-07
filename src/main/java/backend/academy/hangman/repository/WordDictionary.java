package backend.academy.hangman.repository;

import backend.academy.hangman.game.Settings;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.game.word.WordClassification;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;

public class WordDictionary implements Dictionary {
    /**
     * For the fast realisation of {@link #getRandomWord(WordClassification)}
     */
    private final Map<WordClassification, List<Word>> classifiedWords;

    public WordDictionary() {
        this(Settings.DEFAULT_WORDS);
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
