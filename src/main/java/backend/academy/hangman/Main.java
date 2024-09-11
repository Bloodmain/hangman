package backend.academy.hangman;

import backend.academy.hangman.console.ConsoleUI;
import backend.academy.hangman.game.GameController;
import backend.academy.hangman.game.io.UI;
import backend.academy.hangman.game.word.Dictionary;
import backend.academy.hangman.repository.WordDictionary;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        try (
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            Writer writer = new PrintWriter(System.out, false, StandardCharsets.UTF_8)
        ) {
            Dictionary dict = new WordDictionary();
            UI ui = new ConsoleUI(scanner, writer);
            GameController controller = new GameController(ui, dict);
            controller.play();
        } catch (final IOException e) {
            System.err.println("An IO exception occurred: " + e.getMessage());
        }
    }
}
