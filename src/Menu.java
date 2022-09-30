
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Lets test if this will work :)
public class Menu implements MouseListener {

    private JFrame frame;
    private JPanel panel;

    private JButton play;
    private JButton auto;
    private JButton exit;

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
            new Game(20);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}