package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for options (difficulty and length)
 * extends [parent package name].team3.hw5.Frame class {@link Frame}
 * AND NOT {@link java.awt.Frame}
 * implements {@link ActionListener}, and {@link KeyListener}
 * @author Christoforos Seas 1028675
 */
public class Options extends Frame implements ActionListener, KeyListener {
    private JButton exit, easy, normal, hard, impossible;
    private int length;
    private JTextField lengthField;
    private JLabel lengthLabel;

    /**
     * Default constructor, using this when fullscreen is true
     */
    public Options()
    {
        super();
    }

    /**
     * Constructor with given width and height of the frame
     * @param width width of frame
     * @param height height of frame
     */
    public Options(int width, int height)
    {
        super(width, height);
    }

    /**
     * KeyListener's keyTyped method
     * @param e Key event typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * KeyListener's keyPressed method
     * if the character pressed is a digit, it will be saved as the length of the word
     * @param e Key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar()))
                length = Character.getNumericValue(e.getKeyChar());
    }

    /**
     * KeyListener's keyReleased method
     * @param e Key release event
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Extending {@link Frame} initialize method
     * Uses parent's initializing, removing resizable privilege
     * in order to avoid bugs and adding this class' ({@link Options})
     * key listeners and labels
     */
    protected void initialize()
    {
        super.initialize();
        setResizable(false);
        addKeyListener(this);
        addLengthLabel();
    }

    /**
     * Overriding parent's class {@link Frame}
     * method removeLabels {@link Frame#removeLabels()}
     */
    @Override
    protected void removeLabels() {
        remove(lengthField);
        remove(lengthLabel);
        remove(easy);
        remove(normal);
        remove(hard);
        remove(impossible);
        initialize();
    }

    /**
     * Adding Length Labels method
     */
    private void addLengthLabel()
    {
        lengthLabel = new JLabel("Type the length of the word! (0 or empty is random length)");
        lengthLabel.setForeground(Color.BLUE);
        lengthLabel.setBounds(getWidth() / 2 - getWidth() / 8, getHeight() / 9, getWidth() / 4, 20);
        add(lengthLabel);
    }

    /**
     * addButtons() method overriding from {@link Frame#addButtons()}
     */
    protected void addButtons()
    {
        playButtons();
        lengthBox();
    }

    /**
     * removeButtons() method overriding from {@link Frame#removeButtons()}
     */
    protected void removeButtons()
    {
        remove(easy);
        remove(normal);
        remove(hard);
        remove(impossible);
        remove(lengthField);
    }

    /**
     * exitButton() method overriding from {@link Frame#exitButton()}
     */
    protected void exitButton()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.addActionListener(this);
        add(exit);
    }

    /**
     * Adding play buttons method
     */
    private void playButtons()
    {
        easy = new JButton("Easy");
        easy.setBounds(getWidth() / 10, getHeight() - getHeight() / 3, getWidth() / 5, getHeight() / 5);
        easy.addActionListener(this);
        easy.setVisible(true);

        normal = new JButton("Normal");
        normal.setBounds(getWidth() / 2 - getWidth() / 5, getHeight() - getHeight() / 3, getWidth() / 5, getHeight() / 5);
        normal.addActionListener(this);
        normal.setVisible(true);

        hard = new JButton("Hard");
        hard.setBounds(getWidth() / 2, getHeight() - getHeight() / 3, getWidth() / 5, getHeight() / 5);
        hard.addActionListener(this);
        hard.setVisible(true);

        impossible = new JButton("Impossible!");
        impossible.setBounds(getWidth() / 2 + getWidth() / 5, getHeight() - getHeight() / 3, getWidth() / 5, getHeight() / 5);
        impossible.addActionListener(this);
        impossible.setVisible(true);

        add(easy);
        add(normal);
        add(hard);
        add(impossible);
    }

    /**
     * method to create length box to type the length
     */
    private void lengthBox()
    {
        lengthField = new JTextField();
        lengthField.addKeyListener(this);
        lengthField.setBounds(getWidth() / 2 - getWidth() / 40, getHeight() / 5, 40, 40);
        add(lengthField);
    }

    /**
     * method which returns the number of lives depending on difficulty choosing
     * @param difficulty the difficulty chosen
     * @return number of lives the player will have
     */
    private int calculateDifficulty(Object difficulty)
    {
        if (difficulty == easy)
            return 23;
        if (difficulty == normal)
            return 20;
        if (difficulty == hard)
            return 10;
        if (difficulty == impossible)
            return 6;

        //needs to return something, if we get an error it will be shown
        return -1;
    }

    /**
     * overriding {@link ActionListener}'s actionPerformed {@link ActionListener#actionPerformed(ActionEvent)} method
     * @param e Action event which occured
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            dispose();
            System.exit(0);
        }
        //if the player chooses a difficulty
        if (e.getSource() == easy || e.getSource() == normal || e.getSource() ==  hard || e.getSource() ==  impossible)
        {
            int difficulty = calculateDifficulty(e.getSource()); //it will calculate the player's lives
            dispose(); //close the screen
            if (isFullscreen()) {
                if (length == 0)
                    new Game(difficulty);  //creates new game with random lives and given difficulty (more details in Game class constructor)
                else
                    new Game(length, difficulty); //creates new game with given lives and difficulty (more details in Game class constructor)
            }
            else //if fullscreen is false it does the same jobs but with given width and height
                if (length == 0)
                    new Game(getWidth(), getHeight(), difficulty);
                else
                    new Game(getWidth(), getHeight(), length, difficulty);
        }
    }
}
