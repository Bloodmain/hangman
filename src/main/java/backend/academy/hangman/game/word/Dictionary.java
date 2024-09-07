package backend.academy.hangman.game.word;

public interface Dictionary {
    Word getRandomWord(WordClassification classification);
}
