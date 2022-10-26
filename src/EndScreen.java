import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndScreen implements MouseListener {

    private JPanel panel;
    private JFrame frame;

    private JLabel over;

    private JButton exit;
    private JButton play;
    private JButton auto;

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


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String args[]){
        new EndScreen(true);
    }
}
