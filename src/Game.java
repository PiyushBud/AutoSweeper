import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Game implements MouseListener, KeyListener {

    private JButton[][] tiles;
    private JFrame boardF;
    private JPanel boardP;

    private JLabel bombs;
    private int numBombs;

    private Block[][] blocks;
    private int remaining;
    private int dim;

    private boolean auto;
    private Auto solver;
    private ArrayList<Block[][]> boards;
    private int index;
    //private int ai, aj;

    public Game(int dim, boolean auto){
        if (auto){
            index = 0;
        }
        this.auto = auto;
        this.dim = dim;
        numBombs = (dim*dim)/4;
        remaining = (dim*dim) - numBombs;

        boardP = new JPanel();
        boardP.setBounds(50, 50, 900, 900);
        boardP.setLayout(new GridLayout(dim+1, dim+1, 1, 1));
        boardP.setBackground(Color.GRAY);

        bombs = new JLabel(String.valueOf(numBombs));
        bombs.setBackground(Color.gray);

        tiles = new JButton[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(new Color(64, 168, 222));
                tiles[i][j].addMouseListener(this);
                tiles[i][j].addKeyListener(this);
                boardP.add(tiles[i][j]);

            }
        }

        if(!auto) {
            boardP.add(new JLabel("Bombs:"));
            boardP.add(bombs);
        }

        boardF = new JFrame();
        boardF.setLayout(null);
        boardP.addKeyListener(this);
        boardF.add(boardP);
        boardF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardF.setSize(1000, 1500);
        boardF.addKeyListener(this);
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

        if(auto){
            if(blocks == null){
                for(int i = 0; i < tiles.length; i++){
                    for(int j = 0; j < tiles[i].length; j++){
                        if(e.getSource() == tiles[i][j]){
                            blocks = Block.makeBoard(dim, i, j);
                            //blocks = Block.cusBoard();
                            showTile(i, j);
                            solver = new Auto(blocks, i, j);
                        }
                    }
                }
                boards = solver.getList();
                blocks = boards.get(index);
            }
            return;
        }

        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(e.getSource() == tiles[i][j]) {
                    if(blocks == null && e.getButton() == 1){
                        blocks = Block.makeBoard(dim, i, j);
                        showTile(i, j);
                        return;
                    }
                    if(e.getButton() == 3){
                        flag(tiles, i, j);
                    }
                    if(e.getButton() == 1 && !blocks[i][j].getFlag()){
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 39){
            index++;
            if(index >= boards.size()){
                index = boards.size()-1;
            }
            blocks = boards.get(index);
            if(blocks == null){
                new EndScreen(true);
            }
            showBoard();

        }
        if(e.getKeyCode() == 37){
            index--;
            if(index < 0){
                index = 0;
            }
            blocks = boards.get(index);
            showBoard();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void flag(JButton[][] tiles, int i, int j){
        if(blocks[i][j].getVisible()){
            return;
        }
        if(tiles[i][j].getBackground() == Color.red){
            tiles[i][j].setBackground(new Color(64, 168, 222));
            numBombs++;
            blocks[i][j].setFlag(false);
        }
        else {
            tiles[i][j].setBackground(Color.red);
            numBombs--;
            blocks[i][j].setFlag(true);
        }
    }

    //shows a clicked tile
    private void showTile(int i, int j){
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }

        if(blocks[i][j].getBomb()){
            boardF.setVisible(false);
            boardF.dispose();
            new EndScreen(false);
            return;
        }

        if(blocks[i][j].getVisible() || blocks[i][j].getFlag()){
            return;
        }

        if(blocks[i][j].getSurr() == 0){
            showZeros(i, j);
            return;
        }

        tiles[i][j].setBackground(Color.white);
        tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));
        blocks[i][j].setVisible();
        remaining--;
        if(remaining == 0){
            new EndScreen(true);
        }
    }

    //Recursively clears zero tiles
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

                if(blocks[i][j].getVisible()) {
                    if(blocks[i][j].getCurr()){
                        tiles[i][j].setBackground(new Color(144, 238, 144));
                    }
                    else {
                        tiles[i][j].setBackground(Color.white);
                    }
                    tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));
                    continue;
                }
                else{
                    tiles[i][j].setBackground(new Color(64, 168, 222));
                    tiles[i][j].setText("");
                }
                if(blocks[i][j].getFlag()){
                    tiles[i][j].setBackground(Color.red);
                    tiles[i][j].setText("");
                }
            }
        }
    }

    public static void printBoard(Block[][] blocks){
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[i].length; j++){
                if(blocks[i][j].getFlag()){
                    System.out.print("F ");
                }
                else if(blocks[i][j].getVisible()) {
                    System.out.print(blocks[i][j].getSurr() + " ");
                }
                else{
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }
}


