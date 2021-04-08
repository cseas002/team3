import java.util.Arrays;
import java.util.HashMap;

/**
 * Javadoc TBA
 */
public class GameLogic {

    /**
     * The number of letters in the English alphabet
     */
    public static final int LETTERPOP = ('Z' - 'A') + 1;

    private Word[] words;//The array of words we can choose from
    private final char[] answer; //The answer

    /**
     * Constructs a GameLogic for the given lexicon, with the answer having the given length.
     * <p>
     * The maximum length of a word is 32 for this implementation of GameLogic
     *
     * @param lexicon    The array containing all the words we can choose from.
     * @param wordLength The length of the answer to the Game.
     */
    public GameLogic(String[] lexicon, int wordLength) {
        lexicon = discardWordsOfDifferentLength(lexicon, wordLength);
        if (lexicon == null || lexicon.length == 0) {
            throw new IllegalArgumentException("Lexicon must contain at least 1 valid word");
        }
        answer = new char[wordLength];
        Arrays.fill(answer, ' ');
        words = new Word[lexicon.length];
        for (int i = 0; i < lexicon.length; ++i) {
            words[i] = new Word(lexicon[i]);
        }

    }

    //Discards all words with a different length from an array.
    private static String[] discardWordsOfDifferentLength(String[] arr, int length) {
        int pop = 0;
        for (String s : arr) {
            if (s.length() == length) {
                ++pop;
            }
        }
        String[] ans = new String[pop];
        pop = 0;
        for (String s : arr) {
            if (s.length() == length) {
                ans[pop] = s;
                ++pop;
            }
        }
        return ans;
    }

    /**
     * Returns the current population of possible words that the answer can be replaced by.
     *
     * @return the current population of possible words that the answer can be replaced by.
     */
    public int potentialWordPopulation() {
        return words.length;
    }

    /**
     * Returns the current clues we have for the answer as a String.
     *
     * @return the current clues we have for the answer as a String.
     */
    public String getCurrentAnswerFramework() {
        return new String(answer);
    }


    /**
     * Returns one word that can satisfy the framework we have.
     *
     * @return one word that can satisfy the framework we have.
     */
    public String getFinalWord() {
        return words[0].toString();
    }

    //Adds the specified character to the indexes in the array, wherever the integer config has a bit of 1
    //Returns how many times it added it to the array.
    private static int addCharToCharArray(char[] arr, char c, int config) {
        int ans = 0;
        for (int i = 0; config != 0; ++i) {
            if ((config & 1) == 1) {
                ++ans;
                arr[i] = capsLock(c);
            }
            config = config >> 1;
        }
        return ans;
    }

    /**
     * Returns how many times the character C occurs in the most popular configuration of that character in the lexicon. And removes all the words not of that configuration from the GameLogic.
     *
     * @param c The character the player chose as a guess.
     * @return How many times C occurs in the word the GameLogic has in mind (or words because GameLogic cheats)
     */
    public int playerMove(char c) {
        return addCharToCharArray(answer, c, query(c));
    }

    //Returns the most popular configuration of C in our words, and removes all he words not following it.
    //This Operation has a time complexity of O(N)
    private int query(char c) {
        int letter = alphabetOrder(c);

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        //This map will take a configuration as a key and associate it with an integer representing how many times we have this configuration in our array of words.
        int bestConfiguration = 0;
        map.put(0, 0);
        //Best configuration is the most popular configuration

        //We use this for loop to find the most popular configuration, using the map to do so in O(N) complexity
        for (Word value : words) {
            int tmp = value.letterConfig[letter];
            //If its the first time we see the configuration associate it with a population of 1
            if (!map.containsKey(tmp)) {
                map.put(tmp, 1);
            }
            //Otherwise increase the population by 1
            else {
                map.put(tmp, map.get(tmp) + 1);
            }
            //If temporary configuration is now more popular than previous best configuration, then tmp is the new best Configuration.
            int comp=map.get(bestConfiguration)-map.get(tmp);
            //In case of a tie we pick the configuration with the lowest number of bits, meaning hte one that has the least instances of the letter.
            if (comp<0||(comp==0&&Integer.bitCount(tmp)<Integer.bitCount(bestConfiguration))) {
                bestConfiguration = tmp;
            }
        }
        //We will now remove all the words that dont contain the popular configuration from our array.
        //Construct a smaller array to replace our array
        Word[] newWords = new Word[map.get(bestConfiguration)];
        //Add to the new array all the words that stay
        int count = 0;
        for (Word word : words) {
            if (word.letterConfig[letter] == bestConfiguration) {
                newWords[count] = word;
                ++count;
            }
        }
        words = newWords;
        return bestConfiguration;
    }


    //Returns the alphabetic order of c
    private static int alphabetOrder(char c) {
        return (capsLock(c) - 'A');
    }

    //If letter c is not a capital letter, it makes it capital
    private static char capsLock(char c) {
        if ('a' <= c && 'z' >= c) {
            return (char) (c + 'A' - 'a');
        }
        return c;
    }

    public char[] getAnswer() {
        return answer;
    }

    private class Word {
        //private String s;
        private final int[] letterConfig;

        private Word(String word) {
            letterConfig = new int[LETTERPOP];
            for (int i = 0; i < word.length(); ++i) {
                letterConfig[alphabetOrder(word.charAt(i))] += 1 << i;
            }
            //this.s=word;
        }

        /**
         * Returns the String representation of the Word. Its the same string that was used to construct it.
         * <p>
         * This is a very slow implementation, mainly because this is a private class and only the GameLogic class can use word objects, and the only time needed is at the end of the Game if the player fails and we need to tell him which word we had in mind.<br>
         * So there is no reason to store that value only to call it once.
         *
         * @return the String representation of the Word. Its the same string that was used to construct it.
         */
        public String toString() {
            char[] bigans = new char[32];
            int size = 0;
            for (int i = 0; i < LETTERPOP; ++i) {
                size += addCharToCharArray(bigans, (char) (i + 'a'), letterConfig[i]);
            }
            char[] ans = new char[size];
            if (size >= 0) System.arraycopy(bigans, 0, ans, 0, size);
            return new String(ans);
        }
    }

    //The main I used to test this class

    public static void main(String[] args) {
        String[] lexicon = {"Bob", "dog", "pub", "pad", "wog", "pog", "gog", "org", "potatopoophead"};
        GameLogic gl = new GameLogic(lexicon, 3);
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
        System.out.println("ans =" + new String(answer));
        for (Word word : words) {
            System.out.println(word);
        }
    }
    //*/

}

