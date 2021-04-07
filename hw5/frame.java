package cseas002.team3.hw5;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class frame extends JFrame implements ActionListener {
    JButton exit;
    JButton fullscreenButton;
    JButton play, playCLI;
    boolean fullscreen;
    ImageIcon icon = new ImageIcon("Hangman.jpg");

    public frame(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
     //   if (fullscreen)
     //       setUndecorated(true);
        setContentPane(new JLabel(new ImageIcon("Hangman.jpg"))); //background
        setSize(800, 600);
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
        setTitle("Hangman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       // setExtendedState(100);
        setResizable(true);
       // pack();
      //  if (fullscreen)
     //   getContentPane().setBackground(new Color(4, 15, 38));
        setIconImage(icon.getImage());

        //adding buttons
        if (fullscreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
            exit_button();
        }

        addButtons();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                setSize(getWidth(), getHeight());
                removeButtons();
                addButtons();
            }
        });
    }

    private void removeButtons()
    {
        remove(play);
        remove(playCLI);
        remove(fullscreenButton);
    }


    private void addButtons() {
        fullscreen_button();
        play_button();
        play_CLI_button();
    }

    private void play_button()
    {
        play = new JButton("Play Hangman with graphics!");
        play.setBounds(getWidth() / 4, getHeight() / 3,getWidth() / 6,getHeight() / 10);
        play.addActionListener(this);
        play.setVisible(true);
        add(play);
    }

    private void play_CLI_button() {
        playCLI = new JButton("Play Hangman without graphics!");
        playCLI.setBounds(getWidth() * 1100 / 1920,getHeight() / 3,getWidth() / 6,getHeight() / 10);
        playCLI.addActionListener(this);
        add(playCLI);
    }

    private void exit_button()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.addActionListener(this);
        add(exit);
    }

    private void fullscreen_button()
    {
        fullscreenButton = fullscreen ? new JButton("Exit Fullscreen") : new JButton("Fullscreen");
        fullscreenButton.setBounds(getWidth() * 4 / 10,getHeight() * 6 / 10,getWidth() / 6,getHeight() / 10);
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
            new game(fullscreen, getWidth(), getHeight());
        }
        else if (e.getSource() == playCLI)
        {
            dispose();
        }
    }

}
