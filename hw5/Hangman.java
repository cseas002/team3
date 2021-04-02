package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    Scanner scan;
    ArrayList <String> words = new ArrayList<>();

    /**
     * Constructor with given file
     * @param file the file
     */
    public Hangman (File file) throws FileNotFoundException {
        scan = new Scanner(file);
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

        for (String str : words)
            System.out.println(str);
    }


    public static void main(String[] args) {
        try {
            Hangman test = new Hangman(new File("words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
