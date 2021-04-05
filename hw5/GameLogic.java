package cseas002.team3.hw5;

import java.util.Arrays;
import java.util.HashMap;
/**
 * Javadoc TBA
 */
public class GameLogic {
	private int a;
	
	public static final int LETTERPOP=('Z'-'A')+1;
	
	private Word[] words;
	public final char[] answer;
	
	public GameLogic(String[] lexicon) {
		if(lexicon==null||lexicon.length==0) {
			throw new IllegalArgumentException("Lexicon must contain at least 1 word");
		}





		//answer=new char[lexicon[0].length()];
		//answer=new char[1 + (int) (Math.random() * 7)];
		answer = new char[4];






		Arrays.fill(answer, ' ');
		words=new Word[lexicon.length];
		for(int i=0;i<lexicon.length;++i) {
			words[i]=new Word(lexicon[i]);
		}
		
	}
	
	public int potentialWordPopulation() {
		return words.length;
	}
	
	public String getCurrentAnswerFramework() {
		return new String(answer);
	}
	
	public String getFinalWord() {
		return words[0].toString();
	}
	
	private static int addCharToCharArray(char[] arr,char c,long config) {
		int ans=0;
		for(int i=0;config!=0;++i) {
			if((config&1)==1) {
				++ans;




				if (arr.length == 0)
					System.out.println("error");




				arr[i]=c;
			}
			config=config>>1;
		}
		return ans;
	}
	
	public int playerMove(char c) {
		return addCharToCharArray(answer,c,query(c));
	}
	
	private long query(char c) {
		int letter=alphabetOrder(c);
		
		HashMap<Long,Integer> map=new HashMap<>();
		//This map will take a configuration as a key and associate it with an integer representing how many times we have this configuration in our array of words.
		long bestConfiguration=0;
		map.put((long) 0,0);
		//Best configuration is the most popular configuration
		
		//We use this for loop to find the most popular configuration, using the map to do so in O(N) complexity
		for (Word word : words) {
			long tmp = word.letterConfig[letter];
			//If its the first time we see the configuration associate it with a population of 1
			if (!map.containsKey(tmp)) {
				map.put(tmp, 1);
			}
			//Otherwise increase the population by 1
			else {
				map.put(tmp, map.get(tmp) + 1);
			}
			//If temporary configuration is now more popular than previous best configuration, then tmp is the new best Configuration.
			if (map.get(bestConfiguration) < map.get(tmp)) {
				bestConfiguration = tmp;
			}
		}
		//We will now remove all the words that dont contain the popular configuration from our array.
		//Construct a smaller array to replace our array
		Word[] newWords=new Word[map.get(bestConfiguration)];
		//Add to the new array all the words that stay
		int count=0;
		for (Word word : words) {
			if (word.letterConfig[letter] == bestConfiguration) {
				newWords[count] = word;
				++count;
			}
		}
		words=newWords;
		return bestConfiguration;
	}
	
	
	
	private static int alphabetOrder(char c) {
		return (capsLock(c)-'A');
	}
	
	private static char capsLock(char c) {
		if('a'<=c&&'z'>=c) {
			return (char) (c+'A'-'a');
		}
		return c;
	}
	
	private static class Word {
		//private String s;
		private final long[] letterConfig;
		private Word(String word) {
			letterConfig=new long[LETTERPOP];
			for(int i=0;i<word.length();++i) {
				letterConfig[alphabetOrder(word.charAt(i))]+= 1L <<i;
			}
			//this.s=word;
		}
		
		/**
		 * Returns the String representation of the Word. Its the same string that was used to construct it.
		 * 
		 * This is a very slow implementation, mainly because this is a private class and only the GameLogic class can use word objects, and the only time needed is at the end of the game if the player fails and we need to tell him which word we had in mind.<br>
		 * So there is no reason to store that value only to call it once.
		 * 
		 * @return the String representation of the Word. Its the same string that was used to construct it.
		 */
		public String toString() {
			char[] bigans=new char[64];
			int size=0;
			for(int i=0;i<LETTERPOP;++i) {
				size+=addCharToCharArray(bigans,(char)(i+'a'),letterConfig[i]);
			}
			char[] ans=new char[size];
			if (size >= 0) System.arraycopy(bigans, 0, ans, 0, size);
			return new String(ans);
		}
	}
	
	//The main I used to test this class
	public static void main (String[] args) {
		String[] lexicon= {"Bob","dog","pub","pad","wog","pog","gog","org"};
		GameLogic gl=new GameLogic(lexicon);
		gl.printallwords();
		gl.playerMove('g');
		gl.printallwords();
		gl.playerMove('o');
		gl.printallwords();
		gl.playerMove('d');
		gl.printallwords();
		gl.playerMove('w');
		gl.printallwords();
		gl.playerMove('g');
		gl.printallwords();
		gl.playerMove('w');
		gl.printallwords();
		
	}
	public void printallwords() {
		System.out.println("ans ="+new String(answer));
		for (Word word : words) {
			System.out.println(word);
		}
	}
	//*/

}
