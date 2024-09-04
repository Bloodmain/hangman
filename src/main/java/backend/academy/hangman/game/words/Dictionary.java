package backend.academy.hangman.game.words;

public interface Dictionary {
    Word getRandomWord(WordClassification classification);
}
