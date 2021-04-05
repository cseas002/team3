package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private ArrayList <String> words = new ArrayList<>();
    private String[] wordsInArray;
    private boolean GUI;
    private int length;
    public GameLogic gameLogic;

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
      //  this.wordsInArray = new String[wordsInArray.length];
      //  System.arraycopy(wordsInArray, 0, this.wordsInArray, 0, wordsInArray.length);
       // length = 3;
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

        this.length = length;

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

    private String[] toArray()
    {
        wordsInArray = words.toString().split("\\W+");
        return wordsInArray;
    }

    public int playerMove(char c)
    {
        return gameLogic.playerMove(c);
    }

    /*
    public String getCurrentAnswerFramework()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        for (int i = 0; i < wordsInArray.length; i++)
            if (wordsInArray[i].length() != 4)
                wordsInArray[i] = "";

        gameLogic.printallwords();
        return gameLogic.getCurrentAnswerFramework();
    }//*/

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
