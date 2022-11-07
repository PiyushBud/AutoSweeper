import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Manages and runs the game UI. Displays grid of JButtons which act as the minesweeper blocks.
 * Can be run in manual standard game mode or with the auto solving function.
 * NOTE: X and Y positions start at the top left ((0,0) is at the top left position). Y-axis is inverted.
 *
 * Standard mode functions as normal minesweeper with each user action updating the UI board and the
 * internal 2D array of blocks.
 *
 * Auto mode uses the Auto class to obtain a list of 2D Block arrays to record every board state in which the
 * algorithm made a meaningful change to the board.
 */
public class Game implements MouseListener, KeyListener {

    /**
     * 2D array of JButtons in which each button represents
     * a Block.
     */
    private JButton[][] tiles;
    /**
     * JFrame and JPanel for UI.
     */
    private JFrame boardF;
    private JPanel boardP;

    /**
     * JLabel to display the number of bombs left
     * under the assumption that all the user's flags
     * are correct.
     */
    private JLabel bombs;
    /**
     * Number of bombs left.
     */
    private int numBombs;

    /**
     * 2D array of blocks to hold the internal data
     * for the minesweeper board.
     */
    private Block[][] blocks;
    /**
     * Remaining blocks for testing if user has cleared the board.
     */
    private int remaining;
    /**
     * The dimension of the square board.
     */
    private int dim;

    /**
     * Whether the game has been started with auto solving or not.
     */
    private boolean auto;
    /**
     * Auto solver class to provide solved board states.
     */
    private Auto solver;
    /**
     * List of board states given by auto solver.
     */
    private ArrayList<Block[][]> boards;
    /**
     * Current index in the list of board states.
     */
    private int index;

    /**
     * Constructor to set up the UI elements.
     * @param dim Dimensions of the square board.
     * @param auto Flag for if the game is loaded with the auto solver.
     */
    public Game(int dim, boolean auto){

        this.auto = auto;
        this.dim = dim;

        //Initialize the number of bombs
        numBombs = (dim*dim)/5;
        remaining = (dim*dim) - numBombs;

        //Sets up JPanel sizes
        boardP = new JPanel();
        boardP.setBounds(50, 50, 900, 900);
        //Gridlayout with extra space for remaining bombs label
        boardP.setLayout(new GridLayout(dim+1, dim+1, 1, 1));
        boardP.setBackground(Color.GRAY);

        //Initializes 2D array of buttons
        tiles = new JButton[dim][dim];

        //Initializes each button
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(new Color(64, 168, 222));     //Cyan background color
                tiles[i][j].addMouseListener(this);     //Listens for mouse inputs
                tiles[i][j].addKeyListener(this);   //Listens for keyboard inputs (used for auto solver)
                boardP.add(tiles[i][j]);    //Add the button to the board
            }
        }

        //Standard game
        //Adds the remaining bombs label
        if(!auto) {
            bombs = new JLabel(String.valueOf(numBombs));
            bombs.setBackground(Color.gray);
            boardP.add(new JLabel("Bombs:"));
            boardP.add(bombs);
        }
        //Auto game
        else{
            index = 0;
        }

        //Frame set up
        boardF = new JFrame();
        boardF.setLayout(null);
        boardP.addKeyListener(this);
        boardF.add(boardP);
        boardF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardF.setSize(1000, 1500);
        boardF.addKeyListener(this);
        boardF.setVisible(true);
    }

    /**
     * Event in which some button is clicked.
     * Released function used as it provided a smoother overall experience
     * while playing the game.
     * @param e The data from the event in which a button is clicked.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //Game is running with the auto solver
        if(auto){
            if(blocks == null){

                for(int i = 0; i < tiles.length; i++){
                    for(int j = 0; j < tiles[i].length; j++){
                        if(e.getSource() == tiles[i][j]){ //Checks if the current JButton is the button clicked
                            //Creates the internal 2D block array
                            blocks = Block.makeBoard(dim, i, j);

                            //Clears the initial tile
                            showTile(i, j);

                            //Creates a new Auto class and provides it the current board
                            //and starting x,y or j,i position.
                            solver = new Auto(blocks, i, j);
                        }
                    }
                }
                boards = solver.getList(); //Gets the list of all relevant board states
                blocks = boards.get(index); //Sets the current board to the first board state in the list
            }
            return;
        }

        //
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

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}



