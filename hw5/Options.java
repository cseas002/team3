package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Options extends Frame implements ActionListener, KeyListener {
    private JButton exit, easy, normal, hard, impossible;
    private int length;
    private JTextField lengthField;
    private JLabel lengthLabel;

    public Options()
    {
        super();
    }

    public Options(int width, int height)
    {
        super(width, height);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar()))
                length = Character.getNumericValue(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    protected void initialize()
    {
        super.initialize();
        addKeyListener(this);
        addLengthLabel();
    }

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

    private void addLengthLabel()
    {
        lengthLabel = new JLabel("Type the length of the word! (0 or empty is random length)");
        lengthLabel.setForeground(Color.BLUE);
        lengthLabel.setBounds(getWidth() / 2 - getWidth() / 8, getHeight() / 9, getWidth() / 4, 20);
        add(lengthLabel);
    }

    protected void addButtons()
    {
        play_buttons();
        lengthBox();
    }

    protected void removeButtons()
    {
        remove(easy);
        remove(normal);
        remove(hard);
        remove(impossible);
        remove(lengthField);
    }

    protected void exit_button()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.addActionListener(this);
        add(exit);
    }

    private void play_buttons()
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

    private void lengthBox()
    {
        lengthField = new JTextField();
        lengthField.addKeyListener(this);
        lengthField.setBounds(getWidth() / 2 - getWidth() / 40, getHeight() / 5, 40, 40);
        add(lengthField);
    }

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

        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit)
            System.exit(0);
        if (e.getSource() == easy || e.getSource() == normal || e.getSource() ==  hard || e.getSource() ==  impossible)
        {
            int difficulty = calculateDifficulty(e.getSource());
            dispose();
            if (isFullscreen()) {
                if (length == 0)
                    new Game(difficulty);
                else
                    new Game(length, difficulty);
            }
            else
                if (length == 0)
                    new Game(getWidth(), getHeight(), difficulty);
                else
                    new Game(getWidth(), getHeight(), length, difficulty);
        }
    }

    public static void main(String[] args) {
        new Options();
    }
}
