package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class Game extends JFrame implements ActionListener {
    private JButton exit;
    private final JLabel[] lettersLabels;
    private final boolean fullscreen;
    private final JButton[] letters = new JButton[26];
    private final boolean[] notVisible = new boolean[26];
    private Hangman hangman;
    private int wrongChoose;
    private final char[] answer;
    private final boolean[] foundLetters;
    private final int difficulty;

    public Game(boolean fullscreen, int width, int height, int length, int difficulty) //true
    {
        this.difficulty = difficulty;
        if (!fullscreen)
            setSize(width, height);

        try {
            hangman = new Hangman(new File("words.txt"), length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }//*/
       // hangman = new Hangman(new String[] {"Bob","dog","pub","pad","wog","pog","gog","org"});
        this.fullscreen = fullscreen;

       // length = 3;
        foundLetters = new boolean[length];
        lettersLabels = new JLabel[length];
        answer = hangman.gameLogic.getAnswer();

        initialize();

        setVisible(true);
    }

    public Game (int width, int height, int length, int difficulty)
    {
        this(false, width, height, length, difficulty);
    }

    public Game (int width, int height, int difficulty) //random length must be true
    {
        this(width, height, (int) (4 + Math.random() * 10), difficulty);
    }

    public Game (int length, int difficulty) //fullscreen must be true
    {
        this(true, 0, 0, length, difficulty);
    }

    public Game (int difficulty)
    {
        this((int) (4 + Math.random() * 10), difficulty);
    }

    private void initialize()
    {
        Background();
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 106, 177));
        initializeLetters();
        createUnderscoresAndLetters();
        checkDone();

      //  if (!fullscreen)
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                 setSize(getWidth(), getHeight());
                 removeLetters();
                 removeUnderscoresAndLetters();
                 createUnderscoresAndLetters();
                 initializeLetters();
            }
        });

        //for fullscreen
        if (fullscreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
            exit_button();
        }
        else
            setResizable(true);

    }

    private void createUnderscoresAndLetters() {
        for (int i = 0; i < foundLetters.length; i++) {
            //if the letter is found it will print the letter, otherwise underscore
            lettersLabels[i] = foundLetters[i] ? new JLabel(Character.toString(answer[i])) : new JLabel("_");
            add(lettersLabels[i]);
            lettersLabels[i].setFont((new Font("Test", Font.PLAIN, getWidth() / 32)));
            lettersLabels[i].setBounds(getWidth() / 2 + i * getWidth() / 40, getHeight() / 10, getWidth() / 2, getHeight());
            revalidate();
            lettersLabels[i].setVisible(true);
        }
    }

    private void removeUnderscoresAndLetters()
    {
        for (JLabel letter : lettersLabels)
            remove(letter);
    }

    private void Background() {
        switch (wrongChoose) {
            case 0 -> setContentPane(new JLabel(new ImageIcon("Hang.jpg")));
            case 1 -> setContentPane(new JLabel(new ImageIcon("Hangman only head.jpg")));
            case 2 -> setContentPane(new JLabel(new ImageIcon("Hangman only head one eye.jpg")));
            case 3 -> setContentPane(new JLabel(new ImageIcon("Hangman only head two eyes.jpg")));
            case 4 -> setContentPane(new JLabel(new ImageIcon("Hangman only head two eyes nose.jpg")));
            case 5 -> setContentPane(new JLabel(new ImageIcon("Hangman only head all face.jpg")));
            case 6 -> setContentPane(new JLabel(new ImageIcon("Hangman no hands.jpg")));
            case 7 -> setContentPane(new JLabel(new ImageIcon("Hangman no hand.jpg")));
            case 8 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs.jpg")));
            case 9 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs dollar.jpg")));
            case 10 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 1.jpg")));
            case 11 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 2.jpg")));
            case 12 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 3.jpg")));
            case 13 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 4.jpg")));
            case 14 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 5.jpg")));
            case 15 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 6.jpg")));
            case 16 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 7.jpg")));
            case 17 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs 8.jpg")));
            case 18 -> setContentPane(new JLabel(new ImageIcon("Hangman no leg 1.jpg")));
            case 19 -> setContentPane(new JLabel(new ImageIcon("Hangman no leg 2.jpg")));
            case 20 -> setContentPane(new JLabel(new ImageIcon("Hangman no leg 3.jpg")));
            case 21 -> setContentPane(new JLabel(new ImageIcon("Hangman no leg 4.jpg")));
            case 22 -> setContentPane(new JLabel(new ImageIcon("Hangman dead.jpg")));
            case 23 -> setContentPane(new JLabel(new ImageIcon("Hangman dead 1.jpg")));
            case 24 -> setContentPane(new JLabel(new ImageIcon("Hangman dead 2.jpg")));
            default -> setContentPane(new JLabel(new ImageIcon("Hangman dead 3.jpg")));
        }
        revalidate();
    }

    private void initializeLetters() {
        for (int i = 0; i < 13; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(getWidth() / 20 + i * getWidth() / 15, getHeight() * 75 / 100, getHeight() / 13, getHeight() / 13);
            letters[i].addActionListener(this);
            add(letters[i]);
        }

        for (int i = 13; i < 26; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(getWidth() / 20 + (i - 13) * getWidth() / 15, getHeight() * 85 / 100, getHeight() / 13, getHeight() / 13);
            letters[i].addActionListener(this);
            add(letters[i]);
        }
    }

    private void removeLetters()
    {
        for (JButton letter : letters)
            remove(letter);
    }

    private void exit_button()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.addActionListener(this);
        add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit)
            System. exit(0);
        else
            for (int i = 0; i < letters.length; i++)
                if (e.getSource() == letters[i])
                {
                    notVisible[i] = true;
                    char character = letters[i].getText().charAt(0);
                    if (hangman.playerMove(character) == 0)
                         wrongChoose += difficulty;

                    //from this line
                    for (int j = 0; j < Integer.min(hangman.gameLogic.getAnswer().length, answer.length); j++)
                        if (Character.isAlphabetic(hangman.gameLogic.getAnswer()[j])) {
                            answer[j] = hangman.gameLogic.getAnswer()[j];
                            foundLetters[j] = true;
                        }

                    //to this line is just for testing


                    initialize();

                    //letters[i].setVisible(false);
                }
    }

    private void checkDone() {
        checkWin();
        checkLose();
    }

    private void checkLose() {
       // if (wrongChoose >= 6)
            //class for losing
          //  System.exit(0);
    }

    private void checkWin() {
        boolean done = true;
        for (boolean word : foundLetters)
            if (!word) {
                done = false;
                break;
            }

        if (!done)
            return ;

        System.exit(1);
    }

    public static void main(String[] args) {
        new Game(1);
    }
}
