package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class Game extends JFrame implements ActionListener{
    private JButton exit;
    private final JLabel[] lettersLabels;
    private final boolean fullscreen;
    private final JButton[] letters = new JButton[26];
    private final boolean[] notVisible = new boolean[26];
    private Hangman hangman;
    private final char[] answer;
    private final boolean[] foundLetters;
    private int lives;
    private final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean end;

    public Game(boolean fullscreen, int width, int height, int length, int lives) //true
    {
        this.lives = lives;
        if (!fullscreen)
            setSize(width, height);

        try {
            hangman = new Hangman(new File(Hangman.filename), length);
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

    public Game (int width, int height, int length, int lives)
    {
        this(false, width, height, length, lives);
    }

    public Game (int width, int height, int lives) //random length must be true
    {
        this(width, height, (int) (4 + Math.random() * 10), lives);
    }

    public Game (int length, int lives) //fullscreen must be true
    {
        this(true, 0, 0, length, lives);
    }

    public Game (int lives)
    {
        this((int) (4 + Math.random() * 10), lives);
    }

    private void initialize()
    {
        Background();
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 106, 177));
        initializeLetters();
        createUnderscoresAndLetters();

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

        checkDone();

    }

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

    private void removeUnderscoresAndLetters()
    {
        for (JLabel letter : lettersLabels)
            remove(letter);
    }

    private void Background() {
        String name = "Hangman Pirate Pictures\\" + lives + ".png";
        ImageIcon backgroundIcon = new ImageIcon(name);
        resize(backgroundIcon);
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
        if (end)
            System.exit(1);



        if (e.getSource() == exit)
            System. exit(0);
        else
            for (int i = 0; i < letters.length; i++)
                if (e.getSource() == letters[i])
                {
                    notVisible[i] = true;
                    char character = letters[i].getText().charAt(0);
                    if (hangman.playerMove(character) == 0)
                         lives --;

                    //from this line
                    for (int j = 0; j < Integer.min(hangman.gameLogic.getAnswer().length, answer.length); j++)
                        if (Character.isAlphabetic(hangman.gameLogic.getAnswer()[j])) {
                            answer[j] = hangman.gameLogic.getAnswer()[j];
                            foundLetters[j] = true;
                        }

                    //to this line is just for testing
                    initialize();
                }
    }

    private void checkDone() {
        checkWin();
        checkLose();
    }

    private void checkLose() {
        if (lives == 0)
            end = true;
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

        lives = 100;
        end = true;
        notify();
    }

    private void resize(ImageIcon icon)
    {
        Image image = icon.getImage(); // transform it
        Image newImage;
        if (!fullscreen)
            newImage = image.getScaledInstance(getWidth(), getHeight(),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        else
            newImage = image.getScaledInstance(size.width, size.height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);  // transform it back
        setContentPane(new JLabel(icon));
    }


    public static void main(String[] args) {
        new Game(1);
    }

}
