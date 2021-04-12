package cseas002.team3.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
	//I commented it because I used the fixed one in hangman


	/*
    private static String[] readFromFile(String filename) {
        try {
            File input = new File(filename);
            Scanner scam = new Scanner(input);
            ArrayList<String> tmp = new ArrayList<String>();
            while (scam.hasNext()) {
                tmp.add(scam.next());/*
				String tmp=scam.next();
				System.out.println(tmp);
				ans.add(tmp);//
            }
            scam.close();
            //I wanted to use ArrayList.toArray() but it did not work at all
            String[] ans = new String[tmp.size()];
            for (int i = 0; i < tmp.size(); ++i) {
                ans[i] = tmp.get(i);
            }
            return ans;
        } catch (Exception e) {
            System.out.printf("An error has occured while trying to read words from your file\n");
            e.printStackTrace();
        }
        //System.out.println("bug");
        return null;
    } //*/

    public static void main(String[] args) {
        //String fileName="dictionary.txt"; int answerLength=9; int amountOfTries=30;/*
        String fileName = args[0];
        //*/

        System.out.println("Would you like to play with a JFrame? (Type yes if you do, otherwise you will play by console)");
        Scanner s = new Scanner(System.in);
        if (s.next().equals("yes")) {
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
