package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class game extends JFrame implements ActionListener {
    private JButton exit;
    private final boolean fullscreen;
    private final JButton[] letters = new JButton[26];
    private boolean[] notVisible = new boolean[26];
    private Hangman hangman;
    private boolean wrong = true;
    private int wrongChoose;
    private char[] lettersInWord = {'t', 'e', 's', 't'};
    private boolean[] foundLetters = new boolean[4];
    private char[] previousAnswer;

    public game(boolean fullscreen)
    {
       /* try {
            hangman = new Hangman(new File("words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        hangman = new Hangman(new String[] {"test"});
        this.fullscreen = fullscreen;
        if (fullscreen)
            setUndecorated(true);

        for (int i = 0; i < Integer.min(hangman.gameLogic.answer.length, lettersInWord.length); i++)
            lettersInWord[i] = hangman.gameLogic.answer[i];


        initialize();

        setVisible(true);
    }

    private void initialize()
    {
        Background();
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(new Color(40, 106, 177));
        setLayout(null);
        initializeLetters();
        createUnderscoresAndLetters();

        //adding buttons
        if (fullscreen)
            exit_button();
    }

    private void createUnderscoresAndLetters() {
        for (int i = 0; i < foundLetters.length; i++) {
            //if the letter is found it will print the letter, otherwise underscore
            JLabel letter = foundLetters[i] ? new JLabel(Character.toString(lettersInWord[i])) : new JLabel("_");
            add(letter);
            letter.setFont((new Font("Test", Font.PLAIN, 60)));
            letter.setBounds(1000 + i * 50, 100, 1000, 1000);
            revalidate();
            letter.setVisible(true);
        }
    }

    private void Background() {
        switch (wrongChoose) {
            case 0 -> setContentPane(new JLabel(new ImageIcon("Hang.jpg")));
            case 1 -> setContentPane(new JLabel(new ImageIcon("Hangman only head.jpg")));
            case 2 -> setContentPane(new JLabel(new ImageIcon("Hangman no hands.jpg")));
            case 3 -> setContentPane(new JLabel(new ImageIcon("Hangman no hand.jpg")));
            case 4 -> setContentPane(new JLabel(new ImageIcon("Hangman no legs.jpg")));
            case 5 -> setContentPane(new JLabel(new ImageIcon("Hangman no leg.jpg")));
            default -> setContentPane(new JLabel(new ImageIcon("Hangman dead.jpg")));
        }

        /*
        switch (wrongChoose)
        {
            case 0:
                setContentPane(new JLabel(new ImageIcon("Hang.jpg")));
                break;

            case 1:
                setContentPane(new JLabel(new ImageIcon("Hangman only head.jpg")));
                break;

            case 2:
                setContentPane(new JLabel(new ImageIcon("Hangman no hands.jpg")));
                break;

            case 3:
                setContentPane(new JLabel(new ImageIcon("Hangman no hand.jpg")));
                break;

            case 4:
                setContentPane(new JLabel(new ImageIcon("Hangman no legs.jpg")));
                break;

            case 5:
                setContentPane(new JLabel(new ImageIcon("Hangman no leg.jpg")));
                break;

            default:
                setContentPane(new JLabel(new ImageIcon("Hangman dead.jpg")));
        }
         */
        revalidate();
    }

    private void initializeLetters() {
        for (int i = 0; i < 13; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(500 + i * 70, 900, 50, 50);
            letters[i].addActionListener(this);
            add(letters[i]);
        }

        for (int i = 13; i < 26; i++) {
            if (notVisible[i])
                continue;
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(500 + (i - 13) * 70, 980, 50, 50);
            letters[i].addActionListener(this);
            add(letters[i]);
        }
    }

    private void exit_button()
    {
        exit = new JButton("X");
        exit.setBounds(1850, 0, 70, 30);
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
                    hangman.playerMove(character);
                    //System.out.println(hangman.gameLogic.answer);
                    //System.out.println(lettersInWord[0]);
                    for (int j = 0; j < hangman.gameLogic.answer.length; j++)
                        Character.toUpperCase(hangman.gameLogic.answer[j]);
                    if (Arrays.equals(hangman.gameLogic.answer, lettersInWord))
                        wrongChoose ++;

                    //from this line
                    for (int j = 0; j < Integer.min(hangman.gameLogic.answer.length, lettersInWord.length); j++)
                        if (Character.isAlphabetic(hangman.gameLogic.answer[j])) {
                            lettersInWord[j] = hangman.gameLogic.answer[j];
                            foundLetters[j] = true;
                        }

                    System.out.println();
                    for (int j = 0; j < hangman.gameLogic.answer.length; j++)
                        System.out.print(hangman.gameLogic.answer[j]);

                    System.out.println();
                    for (int j = 0; j < lettersInWord.length; j++)
                        System.out.print(lettersInWord[j]);


                    for (int j = 0; j < hangman.gameLogic.answer.length; j++)
                    {
                        if (lettersInWord[j] != hangman.gameLogic.answer[j] || !Character.isAlphabetic(hangman.gameLogic.answer[j])) {
                            wrongChoose ++;
                            break;
                        }
                        if (j == 3)
                            System.exit(0);

                    }
                    //to this line is just for testing


                    initialize();

                    //letters[i].setVisible(false);
                }
    }

    public static void main(String[] args) {
        new game(true);
    }
}
