package cseas002.team3.hw5;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;


public abstract class Frame extends JFrame implements ActionListener {

    private JButton exit;
    private JButton fullscreenButton;
    private JButton play, playCLI;
    private boolean fullscreen;
    public ImageIcon icon = new ImageIcon("Hangman.jpg");

    public Frame(boolean fullscreen, int width, int height)
    {
        this.fullscreen = fullscreen;
        if (!fullscreen)
        setSize(width, height);
        Background();
        setResizable(true);
        initialize();
        setVisible(true);
    }

    public Frame(int width, int height)
    {
        this(false, width, height);
    }

    public Frame()
    {
        this(true, 0, 0);
    }

    private void Background()
    {
        ImageIcon backgroundIcon = new ImageIcon("Hangman.jpg");
        backgroundIcon = resize(backgroundIcon);
        setContentPane(new JLabel(backgroundIcon));
    }

    protected void initialize()
    {
        setTitle("Hangman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(icon.getImage());

        addButtons();
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                setSize(getWidth(), getHeight());
                removeButtons();
                addButtons();
                removeLabels();
            }
        });

        if (fullscreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(this);
            exit_button();
        }

    }

    protected abstract void removeLabels();

    protected void removeButtons()
    {
        remove(play);
        remove(playCLI);
        remove(fullscreenButton);
    }


    protected void addButtons() {
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

    protected void exit_button()
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit)
            System.exit(0);
        else if (e.getSource() == fullscreenButton) {
            fullscreen = !fullscreen;
            dispose();
            if (fullscreen)
                new Frame() { protected void removeLabels() {}};
            else
                new Frame(800, 600) { protected void removeLabels() {}};
        }
        else if (e.getSource() == play) {
            dispose();
            if (fullscreen)
                new Options();
            else
                new Options(getWidth(), getHeight());
        }
        else if (e.getSource() == playCLI)
        {
            dispose();
            int length = GraphicsLessGame.chooseLength();
            int lives = GraphicsLessGame.chooseLives();
            try {
                new GraphicsLessGame(new Hangman(new File(Hangman.filename), length).toArray(), length, lives);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    private ImageIcon resize(ImageIcon icon)
    {
        Image image = icon.getImage(); // transform it
        Image newImage;
        if (!isFullscreen())
            newImage = image.getScaledInstance(getWidth(), getHeight(),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        else
            newImage = image.getScaledInstance(Hangman.size.width, Hangman.size.height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);  // transform it back

        return icon;
    }


    public static void main(String[] args)
    {
        new Frame(false, 800, 600) { protected void removeLabels() {}};
    }

}
