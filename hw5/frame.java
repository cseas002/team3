package cseas002.team3.hw5;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frame extends JFrame implements ActionListener{
    JButton exit;
    JButton fullscreenButton;
    JButton play, playCLI;
    boolean fullscreen;
    ImageIcon icon = new ImageIcon("Hangman.jpg");

    public frame(boolean check_fullscreen)
    {
        fullscreen = check_fullscreen;
        if (fullscreen)
            setUndecorated(true);
        setContentPane(new JLabel(new ImageIcon("Hangman.jpg"))); //background
        initialize();
        setVisible(true);
    }

    /*private void background()
    {
        JLabel test = new JLabel(new ImageIcon("Hangman.jpg"));
        test.setBounds(0, 0, 1920, 1080);
        add(test);
    }*/



    private void initialize()
    {
        revalidate();
        repaint();
        setTitle("Hangman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
       // setExtendedState(100);
        setBounds(0, 0, 800, 600);
       // pack();
        if (fullscreen)
        getContentPane().setBackground(new Color(4, 15, 38));
        //setLayout(null);
        setIconImage(icon.getImage());
        //adding buttons
        if (fullscreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            exit_button();
        }

        fullscreen_button();
        play_button();
        play_CLI_button();

        System.out.println(getWidth() + " " + getHeight());
    }

    private void play_button()
    {
        play = new JButton("Play Hangman with graphics!");
        play.setBounds(getWidth() / 3, getHeight() / 3,getWidth() / 6,getHeight() / 10);
        play.addActionListener(this);
        play.setVisible(true);
        add(play);
    }

    private void play_CLI_button() {
        playCLI = new JButton("Play Hangman without graphics!");
        playCLI.setBounds(1000,340,getWidth() / 6,getHeight() / 10);
        playCLI.addActionListener(this);
        add(playCLI);
    }

    private void exit_button()
    {
        exit = new JButton("X");
        exit.setBounds(1850, 0, 70, 30);
        exit.addActionListener(this);
        add(exit);
    }

    private void fullscreen_button()
    {
        fullscreenButton = fullscreen ? new JButton("Exit Fullscreen") : new JButton("Fullscreen");
        fullscreenButton.setBounds(getWidth() / 2,getHeight() * 6 / 10,getWidth() / 6,getHeight() / 10);
        fullscreenButton.addActionListener(this);
        add(fullscreenButton);
    }


    public static void main(String[] args)
    {
        new frame(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit)
            System.exit(0);
        else if (e.getSource() == fullscreenButton) {
            fullscreen = !fullscreen;
            dispose();
            new frame(fullscreen);
        }
        else if (e.getSource() == play) {
            dispose();
            new game(fullscreen);
        }
        else if (e.getSource() == playCLI)
        {
            dispose();
        }
    }
}
