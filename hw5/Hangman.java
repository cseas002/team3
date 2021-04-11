package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private ArrayList <String> words = new ArrayList<>();
    public GameLogic gameLogic;
    public static String filename;

    /**
     * Constructor with given ArrayList of Strings
     * @param words ArrayList of Strings
     */
    public Hangman(ArrayList<String> words)
    {
        this.words = new ArrayList<>(words);
        gameLogic = new GameLogic(toArray(), (int) (4 + Math.random() * 10));
    }

    /**
     * Constructor with given array of Strings
     * @param wordsInArray String array
     */
    public Hangman(String[] wordsInArray)
    {
        gameLogic = new GameLogic(wordsInArray, 3);
    }

    /**
     * Constructor with given file
     * @param file the file
     * @throws FileNotFoundException in case file can't be found
     */
    public Hangman (File file) throws FileNotFoundException {
        this(file, (int) (4 + Math.random() * 10));
    }

    /**
     * Constructor with given file and length of the word
     * @param file the file
     * @param length length of the chosen word
     * @throws FileNotFoundException in case file can't be found
     */
    public Hangman(File file, int length) throws FileNotFoundException {
        if (length == 0)
            length = (int) (4 + Math.random() * 10);

        Scanner scan = new Scanner(file);
        while (scan.hasNext())
        {
            boolean is_word = true;
            String word = scan.next();
            for (int i = 0; i < word.length(); i++)
                //true if the character is a Latin letter
                if (word.charAt(i) < 65 || word.charAt(i) > 122 || (word.charAt(i) > 90 && word.charAt(i) < 97) || word.length() != length)
                {
                    is_word = false;
                    break;
                }

            if (is_word)
                words.add(word);
        }

       // words.removeIf(word -> word.length() != this.length); //removes all the words who have different lengths
        gameLogic = new GameLogic(toArray(), length);
    }

    public String[] toArray()
    {
        return words.toString().split("\\W+");
    }

    public int playerMove(char c)
    {
        return gameLogic.playerMove(c);
    }

    public String getFinalWord()
    {
        return gameLogic.getFinalWord();
    }

    public static void main(String[] args) throws FileNotFoundException {
            Hangman test = new Hangman(new File("words.txt"), (int) (4 + Math.random() * 10));

            for (String word : test.words)
                System.out.println(word);
    }
}
