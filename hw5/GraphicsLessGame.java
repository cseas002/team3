package cseas002.team3.hw5;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * The GraphicsLessGame class has a very simple job. Upon creation it plays the HangMan game in the console just like it was in the moodle instructions, printing the appropriate messages needed.
 * 
 * @author Orfeas Pliaridis
 * @version 1.0
 */
public class GraphicsLessGame {
		
	private GameLogic gameLogic; //The game's gamelogic
	private int remainingLives;  //How many lives the player has remaining
	private boolean isStillGoing=true; //Wether the game is still going or not, always begins as true
	private int charactersCompleted=0; //How many characters the player has found, once its as long as the answer's length player wins
	private ArrayList<Character> guesses=new ArrayList<Character>(); //The characters the player already guessed
	
	/**
	 * Constructs a GraphicsLessGame with the given lexicon, choosing a word of the specified length as the answer and giving the player the given amount of lives.
	 * <p>
	 * Upon construction the game will play itself just like it's in moodle
	 * 
	 * @param lexicon The array of strings containing all the words we can choose from
	 * @param answerLength The length of the answer
	 * @param playerLives How many guesses the player has
	 */
	public GraphicsLessGame(String[] lexicon, int answerLength, int playerLives) {
		if(playerLives<=0||answerLength<=0) {
			throw new IllegalArgumentException("Both answeLength and playerLives must be positive integers");
		}
		System.out.println("Welcome to the hangman game.\n");
		System.out.println("You choose to use a length word of: "+answerLength);
		System.out.println("You choose to use a maximum of tries: "+playerLives);
		remainingLives=playerLives;
		gameLogic=new GameLogic(lexicon,answerLength);
		play();
	}
	
	//This plays the game
	private void play() {
		Scanner scam=new Scanner(System.in);
		//While the game is going
		while(this.isStillGoing) {
			printMessage();
			String s=scam.next();
			//Checking if the player entered a letter or not
			if(s.length()!=1||!isLetter(s.charAt(0))) {
				System.out.println("You must enter a letter!");
			}
			else {
				//If he entered the letter then do a query
				query(s.charAt(0));
			}
		}
		scam.close();
	}
	
	//Ends the game with the given final message
	private void gameOver(String finalMessage) {
		System.out.print("\nanswer = ");
		CapsLockLessPrint(gameLogic.getFinalWord());
		System.out.println("\n"+finalMessage);
		this.isStillGoing=false;
	}
	
	//This is for whenever a player makes a move
	private void query(char playerMove) {
		playerMove=unCapsLock(playerMove); //We dont use capital letters here
		
		//Dont let the player guess the same letter twice
		if(guesses.contains(playerMove)) { 
			System.out.println("You already guessed that");
			return;
		}
		guesses.add(playerMove);
		
		//Instances is how many times the letter belongs in our word
		int instances=gameLogic.playerMove(playerMove);
		//Once we have as many characters as the answers length its game over
		charactersCompleted+=instances;
		//If the letter is nowhere
		if(instances==0) {
			System.out.printf("Sorry, there are no %c's\n",playerMove);
			--remainingLives;
		}
		//If the letter appears 1 or more time we print these messages
		else if(instances==1){
			System.out.printf("Yes, there is one %c\n",playerMove);
		}
		else {
			System.out.printf("Yes, there are %d %c's\n",instances,playerMove);
		}
		
		//Checking for victory
		if(charactersCompleted==gameLogic.getAnswer().length) {
			gameOver("You beat me");
		}
		
		//Checking for defeat
		if(remainingLives==0) {
			gameOver("Sorry, you lose");
		}
	}
	
	//Prints the message for every turn
	private void printMessage() {
		System.out.println("\nguesses : "+remainingLives);
		System.out.println("words   : "+gameLogic.potentialWordPopulation()); 
		System.out.print("guessed : "); printGuesses();
		System.out.print("current : "); printWithMinusSignInsteadOfGaps(gameLogic.getAnswer());
		System.out.print("Your guess? ");
	}
	
	
	//Prints the guesses in the form of [a, b, l]
	private void printGuesses() {
		System.out.printf("[");
		if(guesses.size()>0) {System.out.printf("%c",guesses.get(guesses.size()-1));}
		for(int i=guesses.size()-2;i>=0;--i) {
			System.out.printf(", %c",guesses.get(i));
		}
		System.out.printf("]\n");
	}
	
	//Now what follows is a series of simple static methods used in this class to help with handling characters and strings
	
	//Prints the string s replacing all characters with their non-capital counterparts
	private static void CapsLockLessPrint(String s) {
		for(int i=0;i<s.length();++i) {
			System.out.print(unCapsLock(s.charAt(i)));
		}
	}
		
	//Returns true if c is a letter in the english alphabet
	private static boolean isLetter(char c) {
		return('a'<=c&&c<='z')||('A'<=c&&c<='Z');
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
		for(char c : arr) {
			if (c==' ') {
				System.out.print("-");
			}
			else {
				System.out.printf("%c",unCapsLock(c));
			}
		}
		System.out.print("\n");
	}
	
	
	/*The main used to test this class
	public static void main (String args[]) {
		String[] lexicon = {"Bob", "dog", "pub", "pad", "wog", "pog", "gog", "org", "potatopoophead"};
		new GraphicsLessGame(lexicon,3,5);
	}
	//*/
}
