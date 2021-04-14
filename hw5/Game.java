package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class which controls the game's main frame
 * @author Christoforos Seas 1028675
 */
public class Game extends JFrame implements ActionListener{
    private JButton exit;
    private final JLabel[] lettersLabels;
    private final boolean fullscreen;
    private final JButton[] letters = new JButton[26];
    private final JLabel[] hearts = new JLabel[24];
    private final boolean[] notVisible = new boolean[26];
    private Hangman hangman;
    private final char[] answer;
    private final boolean[] foundLetters;
    private int lives;
    private boolean end;

    /**
     * Constructor used by all the other constructors
     * Creates the game based on the player's chosen options
     * @param fullscreen if it's fullscreen or not
     * @param width width of the frame
     * @param height height of the frame
     * @param length length of the word
     * @param lives lives the player will have
     */
    public Game(boolean fullscreen, int width, int height, int length, int lives)
    {
        this.lives = lives;
        this.fullscreen = fullscreen;
        if (!fullscreen)
            setSize(width, height);

        try {
            hangman = new Hangman(new File(Hangman.filename), length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        foundLetters = new boolean[length];
        lettersLabels = new JLabel[length];
        answer = hangman.gameLogic.getAnswer();

        initialize();
        setVisible(true);
    }

    /**
     * Constructor when fullscreen is false and given lives and length
     * @param width width of the frame
     * @param height height of the frame
     * @param length length of the word
     * @param lives lives the player will have
     */
    public Game (int width, int height, int length, int lives)
    {
        this(false, width, height, length, lives);
    }

    /**
     * Constructor when fullscreen is false and given lives and random length
     * @param width width of the frame
     * @param height height of the frame
     * @param lives lives the player will have
     */
    public Game (int width, int height, int lives)
    {
        this(width, height, (int) (4 + Math.random() * 10), lives);
    }

    /**
     * Constructor when fullscreen is true and given lives and given length
     * @param length length of the word
     * @param lives lives the player will have
     */
    public Game (int length, int lives) //fullscreen must be true
    {
        this(true, 0, 0, length, lives);
    }

    /**
     * Constructor when fullscreen is true and given lives and random length
     * @param lives lives the player will have
     */
    public Game (int lives)
    {
        this((int) (4 + Math.random() * 10), lives);
    }

    /**
     * Method which initializes lives
     * Lives are basically heart pictures which are added depending on the player's lives
     */
    private void initializeLives()
    {
        ImageIcon heart = new ImageIcon("Hangman Pirate pictures\\heart.png");
        heart = resizeHeart(heart);
        for (int i = 0; i < lives; i++) {
            hearts[i] = new JLabel(heart);
            add(hearts[i]);
            hearts[i].setVisible(true);
            hearts[i].setBounds(getWidth() / 5 + (i % 6) * getWidth() / 20, getHeight() / 700, getWidth() / 10, (i / 6 + 1) * getHeight() / 10);

        }

    }

    /**
     * Initializer Method to initialize the game
     */
    private void initialize()
    {
        Background();
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Frame.ICON.getImage());
        getContentPane().setBackground(new Color(40, 106, 177));
        initializeLetters();
        createUnderscoresAndLetters();
        if (!end)
        initializeLives();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                 setSize(getWidth(), getHeight());
                 Background();
                 removeLetters();
                 removeUnderscoresAndLetters();
                 createUnderscoresAndLetters();
                 if (!end)
                 initializeLives();
                 initializeLetters();
            }
        });

        //for fullscreen
        if (fullscreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
            exitButton();
        }
        else
            setResizable(true);

        if (!end)
        checkDone();
    }

    /**
     * Creating underscores and letters
     */
    private void createUnderscoresAndLetters() {
        for (int i = 0; i < foundLetters.length; i++) {
            //if the letter is found it will print the letter, otherwise underscore
            lettersLabels[i] = foundLetters[i] ? new JLabel(Character.toString(answer[i])) : new JLabel("_");
            add(lettersLabels[i]);
            lettersLabels[i].setFont((new Font("Test", Font.PLAIN, getWidth() / 32)));
            lettersLabels[i].setBounds(getWidth() - getWidth() / 3 + i * getWidth() / 40, getHeight() / 15, getWidth() / 2, getHeight());
            revalidate();
            lettersLabels[i].setVisible(true);
        }
    }

    /**
     * Removes underscores and letters
     */
    private void removeUnderscoresAndLetters()
    {
        for (JLabel letter : lettersLabels)
            remove(letter);
    }

    /**
     * Method which creates the background based on current state of the game.
     * If the player won, the shark will eat the pirate and if the player lost,
     * the shark will eat the player.
     * Otherwise, default background is shown
     */
    private void Background() {
        ImageIcon backgroundIcon;
        if (!end)
        backgroundIcon = new ImageIcon("Hangman Pirate Pictures\\default.png");
        else if (lives == 0)
            backgroundIcon = new ImageIcon("Hangman Pirate Pictures\\lose.png");
        else
            backgroundIcon = new ImageIcon("Hangman Pirate Pictures\\win.png");

        backgroundIcon = resize(backgroundIcon);
        setContentPane(new JLabel(backgroundIcon));

        if (fullscreen)
            exitButton();

        revalidate();
    }

    /**
     * Method which initializes letters' buttons
     * Chooses which are shown and not, sets the background color, foreground color, position, size, etc.
     */
    private void initializeLetters() {
        for (int i = 0; i < 13; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(getWidth() / 20 + i * getWidth() / 15, getHeight() * 75 / 100, getHeight() / 13, getHeight() / 13);
            letters[i].setForeground(new Color(4, 100, 113));
            letters[i].setBackground(Color.CYAN);
            letters[i].addActionListener(this);
            add(letters[i]);
        }

        for (int i = 13; i < 26; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(getWidth() / 20 + (i - 13) * getWidth() / 15, getHeight() * 85 / 100, getHeight() / 13, getHeight() / 13);
            letters[i].setForeground(Color.CYAN);
            letters[i].setBackground(new Color(4, 100, 113));
            letters[i].addActionListener(this);
            add(letters[i]);
        }
    }

    /**
     * Method which removes the letters' buttons
     */
    private void removeLetters()
    {
        for (JButton letter : letters)
            remove(letter);
    }

    /**
     * Method for exit button
     */
    private void exitButton()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.setBackground(Color.CYAN);
        exit.addActionListener(this);
        add(exit);
    }

    /**
     * overriding {@link ActionListener}'s actionPerformed {@link ActionListener#actionPerformed(ActionEvent)} method
     * @param e Action event which occured
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (end) {
            dispose();
            System.exit(1);
        }

        if (e.getSource() == exit) {
            dispose();
            System.exit(0);
        }
        else
            for (int i = 0; i < letters.length; i++)
                if (e.getSource() == letters[i]) //if the player chooses a letter
                {
                    notVisible[i] = true; //it will not be visible
                    char character = letters[i].getText().charAt(0);
                    if (hangman.playerMove(character) == 0) //it will use the Hangman's class playerMove method which uses the GameLogic class
                        remove(hearts[--lives]); //remove last heart and make lives = lives - 1

                    for (int j = 0; j < answer.length; j++)
                        if (Character.isAlphabetic(hangman.gameLogic.getAnswer()[j]) && !foundLetters[j]) //if the player found a letter, it will be added to the answer
                        {
                            answer[j] = hangman.gameLogic.getAnswer()[j];
                            foundLetters[j] = true;
                        }
                    initialize();
                }
    }

    /**
     * Method which checks whether the game is over
     */
    private void checkDone() {
        checkWin();
        checkLose();
    }

    /**
     * Method which checks if the player lost
     */
    private void checkLose() {
        if (lives == 0) {
            end = true;
            initialize();
        }
    }

    /**
     * Method which checks if the player won
     */
    private void checkWin() {
        boolean done = true;
        for (boolean word : foundLetters)
            if (!word) {
                done = false; //if even one word isn't found, that means the player didn't win
                break;
            }

        if (!done)
            return ;

        end = true; //else, the player won
        initialize();
    }

    /**
     * Method which resizes heart's image dimensions depending on frame's size
     * @param icon the heart icon
     * @return resized heart
     */
    private ImageIcon resizeHeart(ImageIcon icon)
    {
        Image image = icon.getImage(); // transform it
        Image newImage;
        if (!fullscreen)
            newImage = image.getScaledInstance(getWidth() / 25, getHeight() / 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        else
            newImage = image.getScaledInstance(Hangman.size.width / 20, Hangman.size.height / 20,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);  // transform it back

        return icon;
    }

    /**
     * Method which resizes background's image dimensions depending on frame's size
     * @param icon the background icon
     * @return resized background
     */
    private ImageIcon resize(ImageIcon icon)
    {
        Image image = icon.getImage(); // transform it
        Image newImage;
        if (!fullscreen)
            newImage = image.getScaledInstance(getWidth(), getHeight(),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        else
            newImage = image.getScaledInstance(Hangman.size.width, Hangman.size.height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);  // transform it back

        return icon;
    }

    /**
     * Main class just for testing.
     * Creating a fullscreen game using words.txt file
     * @param args args
     */
    public static void main(String[] args) {
        Hangman.filename = "words.txt";
        new Game(1);
    }
}
