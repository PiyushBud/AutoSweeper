
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Game implements MouseListener {

    private JButton[][] tiles;
    private JFrame boardF;
    private JPanel boardP;
    private JPanel score;

    private JLabel bombs;
    private int numBombs;

    private Block[][] blocks;
    private int remaining;
    private int dim;

    private boolean auto;
    //private Auto solver;
    private int ai, aj;

    public Game(int dim, boolean auto){
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
                        if(auto){
                            //solver = new Auto();
                        }
                        else {
                            showTile(i, j);
                        }
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
        remaining--;
        if(remaining == 0){
            System.out.println("You win");
            System.exit(0);
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
                if(blocks[i][j].getBomb()) {
                    tiles[i][j].setBackground(Color.black);
                }
            }
        }
    }

    private ArrayList<Block> next(){
        ArrayList<Block> retArr = new ArrayList<>(16);
        ArrayList<Block> vis = findSurroundingVis();
        ArrayList<Block> invis = findSurroundingInvis();

        if(invis.size() == 0){
            return null;
        }

        if(invis.size() == blocks[ai][aj].getSurr()){
            return invis;
        }

        return null;
    }

    private void findNext(){
        for(int k = ai; k < blocks.length; k++){
            for(int l = aj; l < blocks[k].length; l++){
                if(blocks[k][l].getVisible()){
                    ai = k;
                    aj = l;
                    return;
                }
            }
        }
    }

    private ArrayList<Block> findSurroundingInvis(){
        ArrayList<Block> num = new ArrayList<>(8);
        int index = 0;
        try{
            if(!blocks[ai][aj+1].getVisible()){
                num.set(index, blocks[ai][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai][aj-1].getVisible()){
                num.set(index, blocks[ai][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai+1][aj].getVisible()){
                num.set(index, blocks[ai+1][aj]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai-1][aj].getVisible()){
                num.set(index, blocks[ai-1][aj]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai+1][aj+1].getVisible()){
                num.set(index, blocks[ai+1][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai-1][aj+1].getVisible()){
                num.set(index, blocks[ai-1][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai+1][aj-1].getVisible()){
                num.set(index, blocks[ai+1][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[ai-1][aj-1].getVisible()){
                num.set(index, blocks[ai-1][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}

        return num;
    }

    private ArrayList<Block> findSurroundingVis(){
        ArrayList<Block> num = new ArrayList<>(8);
        int index = 0;
        try{
            if(blocks[ai][aj+1].getVisible()){
                num.set(index, blocks[ai][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai][aj-1].getVisible()){
                num.set(index, blocks[ai][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai+1][aj].getVisible()){
                num.set(index, blocks[ai+1][aj]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai-1][aj].getVisible()){
                num.set(index, blocks[ai-1][aj]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai+1][aj+1].getVisible()){
                num.set(index, blocks[ai+1][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai-1][aj+1].getVisible()){
                num.set(index, blocks[ai-1][aj+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai+1][aj-1].getVisible()){
                num.set(index, blocks[ai+1][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[ai-1][aj-1].getVisible()){
                num.set(index, blocks[ai-1][aj-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}

        return num;
    }
}


