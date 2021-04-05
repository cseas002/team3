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
    private final boolean[] notVisible = new boolean[26];
    private final Hangman hangman;
    private int wrongChoose;
    private final char[] answer;
    private final boolean[] foundLetters;
    private int length;

    public game(boolean fullscreen)
    {
        /*
        length = (int) (4 + Math.random() * 10);
        try {
            hangman = new Hangman(new File("words.txt"), length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }//*/
        hangman = new Hangman(new String[] {"Bob","dog","pub","pad","wog","pog","gog","org"});
        this.fullscreen = fullscreen;
        if (fullscreen)
            setUndecorated(true);

        length = 3;
        foundLetters = new boolean[length];
        answer = hangman.gameLogic.getAnswer();

        initialize();

        setVisible(true);

    }

    private void initialize()
    {
        Background();
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(new Color(40, 106, 177));
        setLayout(null);
        initializeLetters();
        checkDone();

        //adding buttons
        if (fullscreen) {
            exit_button();
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
        }
        createUnderscoresAndLetters();
    }

    private void createUnderscoresAndLetters() {
        for (int i = 0; i < foundLetters.length; i++) {
            //if the letter is found it will print the letter, otherwise underscore
            JLabel letter = foundLetters[i] ? new JLabel(Character.toString(answer[i])) : new JLabel("_");
            add(letter);
            letter.setFont((new Font("Test", Font.PLAIN, 60)));
            letter.setBounds(getWidth() / 2 + i * getWidth() / 40, getHeight() / 10, getWidth() / 2, getHeight());
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
                    if (hangman.playerMove(character) == 0)
                         wrongChoose ++;

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
        if (wrongChoose >= 6)
            //class for losing
            System.exit(0);
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

        //class for winning
      //  revalidate();
       // setVisible(true);
       // long time = System.currentTimeMillis();
       // while (System.currentTimeMillis() - time < 5000);
        System.exit(1);
    }

    public static void main(String[] args) {
        new game(true);
    }
}
