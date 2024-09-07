package backend.academy.hangman.game.word;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class to provide classification for a word, so we can enlarge our classification parameters easily.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WordClassification {
    private Categories category;
    private Difficulties difficulty;
}
