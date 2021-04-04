package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private ArrayList <String> words = new ArrayList<>();
    private String[] wordsInArray;
    private boolean GUI;
    public GameLogic gameLogic;

    /**
     * Constructor with given ArrayList of Strings
     * @param words ArrayList of Strings
     */
    public Hangman(ArrayList<String> words)
    {
        this.words = new ArrayList<>(words);
        gameLogic = new GameLogic(toArray());
    }

    /**
     * Constructor with given array of Strings
     * @param wordsInArray String array
     */
    public Hangman(String[] wordsInArray)
    {
        this.wordsInArray = new String[wordsInArray.length];
        System.arraycopy(wordsInArray, 0, this.wordsInArray, 0, wordsInArray.length);
        gameLogic = new GameLogic(wordsInArray);
    }

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
        gameLogic = new GameLogic(toArray());
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

    private String[] toArray()
    {
        wordsInArray = words.toString().split("\\W+");
        return wordsInArray;
    }

    public int playerMove(char c)
    {
        return gameLogic.playerMove(c);
    }

    public String getCurrentAnswerFramework()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        for (int i = 0; i < wordsInArray.length; i++)
            if (wordsInArray[i].length() != 4)
                wordsInArray[i] = "";

        gameLogic.printallwords();
        return gameLogic.getCurrentAnswerFramework();
    }

    public String getFinalWord()
    {
        return gameLogic.getFinalWord();
    }

    public static void main(String[] args) throws FileNotFoundException {
            Hangman test = new Hangman(new File("words.txt"), 4);

            for (String word : test.words)
                System.out.println(word);
    }
}
