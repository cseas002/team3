package cseas002.team3.hw5;


import java.util.ArrayList;
import java.util.Scanner;

public class GraphicsLessGame {

    private final GameLogic gameLogic;
    private int remainingLives;
    private boolean isStillGoing = true;
    private int charactersCompleted = 0;
    private final ArrayList<Character> guesses = new ArrayList<>();

    public GraphicsLessGame(String[] lexicon, int answerLength, int playerLives) {
        System.out.println("Welcome to the hangman game.\n");
        System.out.println("You choose to use a length word of: " + answerLength);
        System.out.println("You choose to use a maximum of tries: " + playerLives);
        remainingLives = playerLives;
        gameLogic = new GameLogic(lexicon, answerLength);
        play();
    }

    private void play() {
        Scanner scam = new Scanner(System.in);
        while (this.isStillGoing) {
            printMessage();
            String s = scam.next();
            if (s.length() != 1 || !isLetter(s.charAt(0))) {
                System.out.println("You must enter a letter!");
            } else {
                query(s.charAt(0));
            }
        }
        scam.close();
    }

    private void gameOver(String finalMessage) {
        System.out.print("\nanswer = ");
        CapsLockLessPrint(gameLogic.getFinalWord());
        System.out.println("\n" + finalMessage);
        this.isStillGoing = false;
    }

    private void query(char playerMove) {
        playerMove = unCapsLock(playerMove);
        if (guesses.contains(playerMove)) {
            System.out.println("You already guessed that");
            return;
        }
        guesses.add(playerMove);

        int instances = gameLogic.playerMove(playerMove);
        charactersCompleted += instances;
        if (instances == 0) {
            System.out.printf("Sorry, there are no %c's\n", playerMove);
            --remainingLives;
        } else if (instances == 1) {
            System.out.printf("Yes, there is one %c\n", playerMove);
        } else {
            System.out.printf("Yes, there are %d %c's\n", instances, playerMove);
        }

        //Checking for victory
        if (charactersCompleted == gameLogic.getAnswer().length) {
            gameOver("You beat me");
        }

        //Checking for defeat
        if (remainingLives == 0) {
            gameOver("Sorry, you lose");
        }
    }

    private void printMessage() {
        System.out.println("\nguesses : " + remainingLives);
        System.out.println("words   : " + gameLogic.potentialWordPopulation());
        System.out.print("guessed : ");
        printGuesses();
        System.out.print("current : ");
        printWithMinusSignInsteadOfGaps(gameLogic.getAnswer());
        System.out.print("Your guess? ");
    }


    //Prints the guesses in the form of [a, b, l]
    private void printGuesses() {
        System.out.print("[");
        if (guesses.size() > 0) {
            System.out.printf("%c", guesses.get(guesses.size() - 1));
        }
        for (int i = guesses.size() - 2; i >= 0; --i) {
            System.out.printf(", %c", guesses.get(i));
        }
        System.out.println("]");
    }

    //Now what follows is a series of simple static methods used in this class to help with handling characters and strings

    //Prints the string s replacing all characters with their non-capital counterparts
    private static void CapsLockLessPrint(String s) {
        for (int i = 0; i < s.length(); ++i) {
            System.out.print(unCapsLock(s.charAt(i)));
        }
    }

    //Returns true if c is a letter in the english alphabet
    private static boolean isLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    //Makes capital letters small letters
    private static char unCapsLock(char c) {
        if ('A' <= c && 'Z' >= c) {
            return (char) (c + 'a' - 'A');
        }
        return c;
    }

    //Prints the characters in an array of characters replacing all ' ' characters with '-', and all capital letters with their non-capital counterpart.
    private static void printWithMinusSignInsteadOfGaps(char[] arr) {
        for (char c : arr) {
            if (c == ' ') {
                System.out.print("-");
            } else {
                System.out.printf("%c", unCapsLock(c));
            }
        }
        System.out.print("\n");
    }
	
	
  /*//	The main used to test this class
	public static void main (String args[]) {
		String[] lexicon = {"Bob", "dog", "pub", "pad", "wog", "pog", "gog", "org", "potatopoophead"};
		new GraphicsLessGame(lexicon,3,5);
	}
	//*/
}
