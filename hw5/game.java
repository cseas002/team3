package cseas002.team3.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game extends JFrame implements ActionListener {
    JButton exit;
    game(boolean fullscreen)
    {
        if (fullscreen) {
            setUndecorated(true);
            exit_button();
        }
        initialize();

        setVisible(true);
    }

    private void initialize()
    {
        setTitle("Hangman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(40, 106, 177));
        setLayout(null);
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
    }
}
