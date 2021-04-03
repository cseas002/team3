package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game extends JFrame implements ActionListener {
    private JButton exit;
    private final boolean fullscreen;
    private JButton[] letters = new JButton[26];

    game(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
        if (fullscreen)
            setUndecorated(true);

        setContentPane(new JLabel(new ImageIcon("Hangman.jpg"))); //background
        initialize();

        setVisible(true);
    }

    private void initialize()
    {
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        getContentPane().setBackground(new Color(40, 106, 177));
        setLayout(null);
        initializeLetters();

        //adding buttons
        if (fullscreen)
            exit_button();
    }

    private void initializeLetters() {
        for (int i = 0; i < 13; i++) {
            char letter = (char) (i + 'A');
            letters[i] = new JButton(Character.toString(letter));
            letters[i].setBounds(500 + i * 70, 900, 50, 50);
            letters[i].addActionListener(this);
            add(letters[i]);
        }

        for (int i = 13; i < 26; i++) {
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
            for (JButton letter : letters)
                if (e.getSource() == letter)
                {
                    char character = letter.getText().charAt(0);
                    //method with given character here

                    letter.setVisible(false);
                }
    }

    public static void main(String[] args) {
        new game(true);
    }
}
