package backend.academy.hangman.repository;

import backend.academy.hangman.game.word.Categories;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.game.word.Difficulties;
import backend.academy.hangman.game.word.Word;
import backend.academy.hangman.game.word.WordClassification;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;

public class DictionaryTest {
    @ParameterizedTest
    @DisplayName("get words from specified classification")
    @CsvSource({
        "male,PROGRAMMING,HARD",
        "lombok,NATURE,EASY",
        "STEVE,JOBS,MEDIUM"
    })
    void getFromSpecifiedClassification(String word, Categories category, Difficulties difficulty) {
        WordClassification classification = new WordClassification(category, difficulty);
        List<Word> wordList = List.of(new Word(word, "", classification));
        Dictionary dict = new WordDictionary(wordList);
        assertThat(dict.getRandomWord(classification)).isEqualTo(new Word(word, "", classification));
    }

    @Test
    @DisplayName("ensure that all words for the category can be accessed")
    void allWordsCanBeAccessed() {
        WordClassification classification = Instancio.create(WordClassification.class);
        List<Word> expected =
            Instancio.ofList(Word.class).size(3).set(all(WordClassification.class), classification).create();
        Dictionary dict = new WordDictionary(expected);

        Set<Word> actual = new HashSet<>();
        while (actual.size() != expected.size()) {
            actual.add(dict.getRandomWord(classification));
        }

        assertThat(actual.size()).isEqualTo(expected.size());
    }

    @RepeatedTest(5)
    @DisplayName("get a word with the specific category from the filled list")
    void getFromFullList() {
        List<Word> wordList = Instancio.ofList(Word.class).size(100).create();
        WordClassification classification = wordList.getFirst().classification();
        Dictionary dict = new WordDictionary(wordList);

        assertThat(dict.getRandomWord(classification).classification()).isEqualTo(classification);
    }
}
