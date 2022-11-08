
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu implements MouseListener {

    /**
     * Frame for UI
     */
    private JFrame frame;
    /**
     * Panel for UI
     */
    private JPanel panel;

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
     * Sets up the menu screen with the options
     * Play, Auto Solve, and Exit.
     */
    public Menu(){
        frame = new JFrame();
        panel = new JPanel();

        play = new JButton("Play");
        auto = new JButton("Auto Solve");
        exit = new JButton("Exit");


        play.addMouseListener(this);
        auto.addMouseListener(this);
        exit.addMouseListener(this);

        panel.add(play);
        panel.add(auto);
        panel.add(exit);

        frame.setSize(500, 300);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("AutoSweeper");
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
        if(e.getSource() == exit){ //Exit
            System.exit(0);
        }

        if (e.getSource() == play) { //Play
            frame.setVisible(false);
            frame.dispose();
            new Game(20, false); //Start new game without auto solver
        }

        if (e.getSource() == auto){ //Run with Auto
            frame.setVisible(false);
            frame.dispose();
            new Game(20, true); //Run the game with auto solver
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