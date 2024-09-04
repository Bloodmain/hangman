package backend.academy.hangman.game.words;

/**
 * Class to provide classification for a word, so we can enlarge our classification parameters easily.
 *
 * @param category   a category of a word
 * @param difficulty a difficulty of a word
 */
public record WordClassification(Categories category, Difficulties difficulty) {
}
