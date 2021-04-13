package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

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
