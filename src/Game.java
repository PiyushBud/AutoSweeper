import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Manages and runs the game UI. Uses a 2D array of JButtons to represent minesweeper blocks
 * and uses a 2D array of Blocks (Blocks) to store Block status data.
 * Can be run in manual standard game mode or with the auto solving function.
 * NOTE: X and Y positions start at the top left ((0,0) is at the top left position). Y-axis is inverted.
 *
 * Standard mode functions as normal minesweeper with each user action updating the UI board and the
 * internal 2D array of blocks.
 *
 * Auto mode uses the Auto class to obtain a list of 2D Block arrays to record every board state
 * in which the algorithm made a meaningful change to the board.
 */
public class Game implements MouseListener, KeyListener {

    /**
     * 2D array of JButtons in which each button represents
     * a Block.
     */
    private final JButton[][] tiles;
    /**
     * JFrame and JPanel for UI.
     */
    private final JFrame boardF;
    private final JPanel boardP;

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
    private final int dim;

    /**
     * Whether the game has been started with auto solving or not.
     */
    private final boolean auto;
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
        numBombs = (dim*dim)/Block.BombFrac;
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
                        //Checks if the current JButton is the button clicked
                        if(e.getSource() == tiles[i][j]){
                            //Creates the internal 2D block array
                            blocks = Block.makeBoard(dim, i, j);

                            //Clears the initial tile
                            showTile(i, j);

                            //Creates a new Auto class and provides it the current board
                            //and starting x,y or j,i position.
                            solver = new Auto(blocks, i, j, remaining);
                        }
                    }
                }
                boards = solver.getList(); //Gets the list of all relevant board states
                blocks = boards.get(index);//Sets the current board to the first board state in the list
            }
            return;
        }

        /*
        Searches through board to find which JButton was clicked.
        Checks if the internal 2D block array (Blocks) is uninitialized, indicating that
        this is the first left-click. Once Blocks is initialized, left-clicks will attempt to reveal
        the selected block and right clicks will attempt to flag selected block.
         */
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(e.getSource() == tiles[i][j]) {  //Finds the button that was pressed
                    //Checks if this is the first click on the board
                    if(blocks == null && e.getButton() == 1){
                        blocks = Block.makeBoard(dim, i, j);  //Creates an internal block board
                        showTile(i, j);  //Runs through the reveal algorithm at the clicked location
                        return;
                    }
                    if(e.getButton() == 3){     //Right click check
                        flag(i, j);      //Flags the block if possible
                    }
                    if(e.getButton() == 1 && !blocks[i][j].getFlag()){  //Left-click on a block
                        showTile(i, j);     //Reveals selected block
                        return;
                    }
                }
            }
        }
    }

    /**
     * Keyboard inputs for running the game on Auto mode.
     * Checks for right and left arrow key presses.
     * Right arrow key displays the next relevant board state produced by the algorithm.
     * Left arrow key displays the previous board state.
     * @param e The Keyboard input event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Right arrow key
        if(e.getKeyCode() == 39){
            index++;    //Increments index
            //Checks if index is at the max index
            if(index >= boards.size()){
                index = boards.size()-1;
            }
            //Sets the game's board to the board at index
            blocks = boards.get(index);
            if(blocks == null) {     //Auto algorithm places null board to indicate win
                new EndScreen(true);    //Launches end screen with win as true
            }
        }

        //Left arrow key
        if(e.getKeyCode() == 37){
            index--;    //Decrements index
            //Checks if index is at the first index
            if(index < 0){
                index = 0;
            }
            //Sets the game's board to the board index
            blocks = boards.get(index);
        }
        showBoard();    //Updates the UI to the new board
    }

    /**
     * Attempts to flag the selected JButton and update the corresponding Block on
     * the Blocks board.
     * @param i The Y position of the block to be flagged.
     * @param j The X position of the block to be flagged.
     */
    private void flag(int i, int j){
        //Checks if block is already visible and thus does not need to be flagged
        if(blocks[i][j].getVisible()){
            return;
        }

        //Checks if block is already flagged and removes flags the block
        if(blocks[i][j].getFlag()){
            //Resets tile background color to cyan
            tiles[i][j].setBackground(new Color(64, 168, 222));
            numBombs++;     //Increases bomb counter
            blocks[i][j].setFlag(false);    //Sets the internal block flag to false
        }
        //Block is not already flagged
        else {
            //Sets the tile background to red, indicating that the tile is flagged
            tiles[i][j].setBackground(Color.red);
            numBombs--;     //Decreases bomb counter
            blocks[i][j].setFlag(true);     //Sets the internal block flag to true
        }
    }

    /**
     * Processes the opening of a tile. Does not open the tile if the block is flagged
     * or already visible.
     *
     * Ends the game with win flag as false if a bomb is selected,
     * Ends the game with win flag as true if remaining blocks hits 0.
     *
     * Reveals the number of surrounding bombs and sets the background of the tile to white
     * otherwise. If the revealed block has 0 surrounding bombs, the program will recursively
     * open surrounding blocks and check for additional 0 blocks.
     *
     * If after clearing there remains 0 blocks to clear, the end screen is launched with the win
     * flag set to true.
     *
     * @param i The Y position of selected block.
     * @param j The X position of selected block.
     */
    private void showTile(int i, int j){
        //Out of bounds check
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }

        //Case where selected block is a bomb
        if(blocks[i][j].getBomb()){
            boardF.setVisible(false);
            boardF.dispose();
            new EndScreen(false);
            return;
        }

        //Does nothing if selected block is already visible or if it is flagged
        if(blocks[i][j].getVisible() || blocks[i][j].getFlag()){
            return;
        }

        //Case where selected block is valid for revealing
        tiles[i][j].setBackground(Color.white);     //Background set to white
        tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));  //Text set to surrounding bombs
        blocks[i][j].setVisible();  //Updates the internal block to indicate that it is visible
        remaining--;    //Decrement the remaining tiles to clear

        //Case where block has 0 surrounding bombs
        if(blocks[i][j].getSurr() == 0){
            showZeros(i, j);    //Runs through the clear 0 tiles algorithm
            return;
        }
        if(remaining == 0){     //Check if user has cleared all blocks and won
            new EndScreen(true);    //Launches end screen with win flag as true
        }
    }

    /**
     * Clears the blocks surrounding the selected block by calling the showTile method.
     * @param i The Y position of the selected block.
     * @param j The X position of the selected block.
     */
    private void showZeros(int i, int j){
        //Out of bounds case
        if(i < 0 || j < 0 || i >= dim || j >= dim){
            throw new ArrayIndexOutOfBoundsException();
        }
        /*
        Checks each surrounding block for 0 and ignores ArrayIndexOutOfBoundsException
         */
        try {
            showTile(i, j + 1);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i, j - 1);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i + 1, j);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i + 1, j + 1);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i + 1, j - 1);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i - 1, j);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i - 1, j + 1);
        } catch (ArrayIndexOutOfBoundsException e) {}
        try {
            showTile(i - 1, j - 1);
        } catch (ArrayIndexOutOfBoundsException e) {}

    }

    /**
     * Updates all blocks on the board based on data stored in each block.
     * Used primarily for running the game with the auto solver.
     */
    private void showBoard(){

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){

                if(blocks[i][j].getVisible()) {     //If the block is visible
                    if(blocks[i][j].getCurr()){     //If the block is the "current" block
                        //Highlight green
                        tiles[i][j].setBackground(new Color(144, 238, 144));
                    }
                    else {
                        //set to white otherwise
                        tiles[i][j].setBackground(Color.white);
                    }
                    //Display the number of surrounding bombs
                    tiles[i][j].setText(String.valueOf(blocks[i][j].getSurr()));
                    continue;
                }
                else{ //Block is not visible
                    //Set to cyan
                    tiles[i][j].setBackground(new Color(64, 168, 222));
                    //No text displayed
                    tiles[i][j].setText("");
                }
                //If the block is flagged
                if(blocks[i][j].getFlag()){
                    //Sets to red to indicate the tile is flagged
                    tiles[i][j].setBackground(Color.red);
                    tiles[i][j].setText("");
                }
            }
        }
    }

    /**
     * Static method to print an inputted board, for testing purposes.
     * @param blocks The board to be printed
     */
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

    /*
     Unused methods from implementing mouse and keyboard listeners.
     */
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

    @Override
    public void keyReleased(KeyEvent e) {}


}



