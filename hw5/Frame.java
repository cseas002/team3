package cseas002.team3.hw5;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class used for the first and second frames of the game.
 * This class is abstract, because {@link Options} inherits from it
 * @author Christoforos Seas 1028675
 */
public abstract class Frame extends JFrame implements ActionListener {
    private JButton exit;
    private JButton fullscreenButton;
    private JButton play;
    private boolean fullscreen;
    public static final ImageIcon ICON = new ImageIcon("Hangman.jpg");

    /**
     * Constructor which is used by all other constructors.
     * This constructor creates the frame's attitudes and behavior
     * @param fullscreen true if it's fullscreen, false if it's not
     * @param width width of the frame
     * @param height height of the frame
     */
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

    /**
     * Constructor with given width and height
     * @param width width of the frame
     * @param height height of the frame
     */
    public Frame(int width, int height)
    {
        this(false, width, height);
    }

    /**
     * Constructor with no parameters which makes a fullscreen frame
     */
    public Frame()
    {
        this(true, 0, 0);
    }

    /**
     * Method which creates the background
     * The background it the first two frames don't resize in order to
     * avoid bugs and other issues
     */
    private void Background()
    {
        ImageIcon backgroundIcon = new ImageIcon("Hangman.jpg");
        backgroundIcon = resize(backgroundIcon);
        setContentPane(new JLabel(backgroundIcon));
    }

    /**
     * initializing method to initialize the frames
     */
    protected void initialize()
    {
        setTitle("Hangman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(ICON.getImage());

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
            exitButton();
        }

    }

    /**
     * abstract method used by {@link Options}
     */
    protected abstract void removeLabels();

    /**
     * Method which removes the buttons
     */
    protected void removeButtons()
    {
        remove(play);
        remove(fullscreenButton);
    }
    /**
     * Method which adds the buttons
     */
    protected void addButtons() {
        fullscreenButton();
        playButton();
    }

    /**
     * Method which sets the attributes of play button
     */
    private void playButton()
    {
        play = new JButton("Play Hangman with graphics!");
        play.setBounds(getWidth() / 4, getHeight() / 3,getWidth() / 2,getHeight() / 10);
        play.addActionListener(this);
        play.setVisible(true);
        add(play);
    }

    /**
     * Method which sets the attributes of exit button
     */
    protected void exitButton()
    {
        exit = new JButton("X");
        exit.setBounds(getWidth() - getWidth() / 20, 0, 5 * getWidth() / 100, getHeight() / 30);
        exit.addActionListener(this);
        add(exit);
    }

    /**
     * Method which sets the attributes of fullscreen button
     */
    private void fullscreenButton()
    {
        fullscreenButton = fullscreen ? new JButton("Exit Fullscreen") : new JButton("Fullscreen");
        fullscreenButton.setBounds(getWidth() * 4 / 10,getHeight() * 6 / 10,getWidth() / 6,getHeight() / 10);
        fullscreenButton.addActionListener(this);
        add(fullscreenButton);
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
        else if (e.getSource() == fullscreenButton) {
            fullscreen = !fullscreen;
            dispose();
            if (fullscreen)
                new Frame() { protected void removeLabels() {}};
            else
                new Frame(800, 600) { protected void removeLabels() {}};
        }
        else if (e.getSource() == play) { //if the user wants to play, this frame closes and it opens another frame for options
            dispose();
            if (fullscreen)
                new Options();
            else
                new Options(getWidth(), getHeight());
        }
    }

    /**
     * fullscreen getter
     * @return true if it's fullscreen and false if it's not
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Icon's getter
     * @return ICON
     */
    public ImageIcon getIcon() {
        return ICON;
    }

    /**
     * Method which resizes the background when the user chooses options
     * to show smoother results
     * @param icon background
     * @return background image icon resized to the frame's borders
     */
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
}
