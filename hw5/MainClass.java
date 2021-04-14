package cseas002.team3.hw5;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The MainClass is the class that provides the main method required to play the game. Upon execution the user is asked wether he wants to play the game with a JFrame or whether he wants to play in console just like in moodle.<br>
 * 
 * 
 * @author Orfeas Pliaridis & Christoforos Seas
 * @version 1.2
 */
public class MainClass {

	/**
	 * Plays the hangman game. It also asks the user whether he wants to play using a JFrame or using console like in moodle.
	 * 
	 * @param args The o-th argument is the name of the File we read our words from, and if he wants to play by console the next 2 arguments are the length of the answer and the amount of Tries the user has.
	 */
    public static void main(String[] args) {
        //String fileName="dictionary.txt"; int answerLength=9; int amountOfTries=30;/*
        String fileName = args[0];
        //*/
		String console;

        System.out.println("Would you like to play with a JFrame? (Type yes if you do, otherwise you will play by console)");
		Scanner s = new Scanner(System.in);
		boolean valid;
        do {
        	valid = true;
        	console = s.next();
        	if (!(console.equals("yes") || console.equals("YES") || console.equals("Yes") || console.equals("no") || console.equals("No") || console.equals("NO")))
			{
				valid = false;
				System.out.println("Wrong input");
			}
		} while (!valid);

        if (console.equals("yes") || console.equals("Yes") || console.equals("YES")) {
            Hangman.filename = fileName;
            //System.out.println(new File(fileName).getAbsolutePath());
            new Frame(false, 800, 600) {
                protected void removeLabels() {
                }
            };
        } else {
			int answerLength = Integer.parseInt(args[1]);
			int amountOfTries = Integer.parseInt(args[2]);
			try {
				new GraphicsLessGame(new Hangman(new File(fileName), answerLength).toArray(), answerLength, amountOfTries);/*
				String[] test=readFromFile("dictionary.txt");
				System.out.println(test.length);
				new GraphicsLessGame(test,9,9);//*/
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
        s.close();


    }
}
