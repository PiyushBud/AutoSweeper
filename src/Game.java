
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game implements MouseListener {

    private JButton[][] tiles;
    private JFrame boardF;
    private JPanel boardP;
    private JPanel score;

    private JLabel bombs;
    private int numBombs;

    private Block[][] blocks;
    private int dim;

    public Game(int dim){
        this.dim = dim;
        numBombs = (dim*dim)/4;

        boardP = new JPanel();
        boardP.setBounds(50, 50, 900, 900);
        boardP.setLayout(new GridLayout(dim+1, dim+1, 1, 1));
        boardP.setBackground(Color.GRAY);

        bombs = new JLabel(String.valueOf(numBombs));
        bombs.setBackground(Color.gray);

        score = new JPanel();
        score.add(bombs);

        tiles = new JButton[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(new Color(64, 168, 222));
                tiles[i][j].addMouseListener(this);
                boardP.add(tiles[i][j]);

            }
        }
        boardP.add(new JLabel("Bombs:"));
        boardP.add(bombs);

        boardF = new JFrame();
        boardF.setLayout(null);
        boardF.add(boardP);
        //boardF.add(score);
        boardF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardF.setSize(1000, 1500);
        boardF.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (e.getSource() == tiles[i][j]) {
                    if(blocks == null && e.getButton() == 1){
                        blocks = Block.makeBoard(dim, i, j);
                        //showBoard();
                        showTile(i, j);
                        return;
                    }
                    if(e.getButton() == 3){
                        flag(tiles, i, j);
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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void flag(JButton[][] tiles, int i, int j){
        if(tiles[i][j].getBackground() == Color.red){
            tiles[i][j].setBackground(new Color(64, 168, 222));
            numBombs++;
            bombs.setText(String.valueOf(numBombs));
        }
        else {
            tiles[i][j].setBackground(Color.red);
            numBombs--;
            bombs.setText(String.valueOf(numBombs));
        }
    }
    private void showTile(int i, int j){
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }

        if(blocks[i][j].getBomb()){
            tiles[i][j].setBackground(Color.black);
            boardF.setVisible(false);
            boardF.dispose();
            new GameOver();
            return;
        }

        if(blocks[i][j].getVisible()){
            return;
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
}


