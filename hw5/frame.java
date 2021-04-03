package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frame extends JFrame implements ActionListener{
    JButton exit;
    JButton fullscreenButton;
    JButton play, playCLI;
    boolean fullscreen;
    frame(boolean check_fullscreen)
    {
        fullscreen = check_fullscreen;
        if (fullscreen)
            setUndecorated(true);
        initialize();
        fullscreen_button();
        play_button();
        play_CLI_button();
        setVisible(true);
    }



    private void initialize()
    {
        setTitle("Hangman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(0, 0, 0));
        setLayout(null);
        if (fullscreen)
        exit_button();
    }

    private void play_button()
    {
        play = new JButton("Play Hangman with graphics!");
        play.setBounds(600,340,300,100);
        play.addActionListener(this);
        add(play);
    }

    private void play_CLI_button() {
        playCLI = new JButton("Play Hangman without graphics!");
        playCLI.setBounds(1000,340,300,100);
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
        fullscreenButton.setBounds(850,640,200,100);
        fullscreenButton.addActionListener(this);
        add(fullscreenButton);
    }


    public static void main(String[] args)
    {
        frame skata = new frame(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit)
            System.exit(0);
        else if (e.getSource() == fullscreenButton) {
            fullscreen = !fullscreen;
            dispose();
            frame skata = new frame(fullscreen);
        }
        else if (e.getSource() == play) {
            dispose();
            game my_game = new game(fullscreen);
        }
        else if (e.getSource() == playCLI)
        {
            dispose();
        }
    }
}
