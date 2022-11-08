import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndScreen implements MouseListener {

    /**
     * Frame for UI
     */
    private JFrame frame;
    /**
     * Panel for UI
     */
    private JPanel panel;

    /**
     * Label displaying either "You Win!" or "Game Over!"
     */
    private JLabel over;

    /**
     * Play button to launch standard game.
     */
    private JButton play;
    /**
     * Auto button to launch the game with the auto solver enabled.
     */
    private JButton auto;
    /**
     * Exit button.
     */
    private JButton exit;

    /**
     * Constructor. Sets up UI depending on the state of the win boolean.
     * @param win Boolean for what kind of end screen to display.
     */
    public EndScreen(boolean win){
        if (win){
            frame = new JFrame();
            panel = new JPanel();

            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.add(Box.createRigidArea(new Dimension(0,5)));


            over = new JLabel("You Win!");

            play = new JButton("Play");
            auto = new JButton("Auto Solve");
            exit = new JButton("Exit");


            play.addMouseListener(this);
            auto.addMouseListener(this);
            exit.addMouseListener(this);

            panel.add(over);
            panel.add(play);
            panel.add(auto);
            panel.add(exit);


            frame.setSize(120, 150);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("GG you win");
            frame.setVisible(true);
            return;
        }

        frame = new JFrame();
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0,5)));


        over = new JLabel("Game Over!");

        play = new JButton("Play");
        auto = new JButton("Auto Solve");
        exit = new JButton("Exit");


        play.addMouseListener(this);
        auto.addMouseListener(this);
        exit.addMouseListener(this);

        panel.add(over);
        panel.add(play);
        panel.add(auto);
        panel.add(exit);


        frame.setSize(120, 150);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Over");
        frame.setVisible(true);


    }

    /**
     * Called when mouse click is released.
     * Starts Play, Auto, or Exit depending on
     * the button clicked.
     * @param e The mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == exit){
            System.exit(0);
        }

        if (e.getSource() == play) {
            frame.setVisible(false);
            frame.dispose();
            new Game(20, false);
        }

        if (e.getSource() == auto){
            frame.setVisible(false);
            frame.dispose();
            new Game(20, true);
        }
    }

    /*
    Unused methods from implementing the mouse listener interface.
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }


}
