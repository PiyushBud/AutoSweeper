
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI implements MouseListener {

    private JFrame frame;
    private JPanel panel;
    private JButton play;
    private JButton next;

    private JFrame boardF;
    private JPanel boardP;
    private JButton[][] tiles;

    private MSBlock[][] blocks;

    private int dim = 20;
    private int count = -1;

    public GUI(){
        frame = new JFrame();
        panel = new JPanel();
        play = new JButton(Integer.toString(count));
        next = new JButton("Go Next");

        play.addMouseListener(this);
        next.addMouseListener(this);

        panel.add(play);
        panel.add(next);

        frame.setSize(500, 300);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Testing");
        frame.setVisible(true);
    }

    private void gameBoard(int dim){

        this.dim = dim;
        boardP = new JPanel();
        boardP.setBounds(50, 50, 900, 900);
        boardP.setLayout(new GridLayout(dim, dim, 1, 1));
        boardP.setBackground(Color.GRAY);

        tiles = new JButton[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(new Color(64, 168, 222));
                tiles[i][j].addMouseListener(this);
                boardP.add(tiles[i][j]);

            }
        }

        boardF = new JFrame();
        boardF.setLayout(null);
        boardF.add(boardP);
        boardF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardF.setSize(1000, 1500);
        boardF.setVisible(true);
    }

    private void showTile(int i, int j){
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }

        if(blocks[i][j].getVisible()){
            return;
        }
        if(blocks[i][j].getBomb()){
            tiles[i][j].setBackground(Color.black);
            return;
            //gameOver();
        }

        if(blocks[i][j].getSurr() == 0){
            showZeros(i, j);
            return;
        }

        tiles[i][j].setBackground(Color.white);
        tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));
        blocks[i][j].setVisible();
    }

    private void showZeros(int i, int j){
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(blocks[i][j].getSurr() == 0){
            tiles[i][j].setBackground(Color.white);
            tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));
            blocks[i][j].setVisible();
            try{
                showTile(i, j+1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i, j-1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i+1, j);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i+1, j+1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i+1, j-1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i-1, j);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i-1, j+1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try{
                showTile(i-1, j-1);
            }
            catch (ArrayIndexOutOfBoundsException e){}
        }
    }

    private void showBoard(){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                if(blocks[i][j].getBomb()) {
                    tiles[i][j].setBackground(Color.black);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == play) {
            count = e.getButton();
            play.setText(Integer.toString(count));
        }

        if (e.getSource() == next) {
            frame.setVisible(false);
            frame.dispose();
            this.gameBoard(dim);
        }

        System.out.println("im fr lost now");

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (e.getSource() == tiles[i][j]) {
                    System.out.println(i + "," + j);
                    if(blocks == null && e.getButton() == 1){
                        blocks = MSBlock.makeBoard(dim, i, j);
                        //showBoard();
                        showTile(i, j);
                        return;
                    }
                    if(e.getButton() == 3){
                        tiles[i][j].setBackground(Color.red);
                        return;
                    }
                    if(e.getButton() == 1){
                        showTile(i, j);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}