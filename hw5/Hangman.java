package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private final ArrayList <String> words = new ArrayList<>();
    private boolean GUI;

    /**
     * Constructor with given file
     * @param file the file
     * @throws FileNotFoundException in case file can't be found
     */
    public Hangman (File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        while (scan.hasNext())
        {
            boolean is_word = true;
            String word = scan.next();
            for (int i = 0; i < word.length(); i++)
                //true if the character is a Latin letter
                if (word.charAt(i) < 65 || word.charAt(i) > 122 || (word.charAt(i) > 90 && word.charAt(i) < 97))
                {
                    is_word = false;
                    break;
                }

            if (is_word)
            words.add(word);
        }
    }

    /**
     * Constructor with given file and length of the word
     * @param file the file
     * @param length length of the chosen word
     * @throws FileNotFoundException in case file can't be found
     */
    public Hangman(File file, int length) throws FileNotFoundException {
        this(file);
        words.removeIf(word -> word.length() != length); //removes all the words who have different lengths
    }


    public static void main(String[] args) throws FileNotFoundException {
            Hangman test = new Hangman(new File("words.txt"), 4);

            for (String word : test.words)
                System.out.println(word);

    }
}
