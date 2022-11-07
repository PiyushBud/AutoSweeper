
import java.util.Random;

/**
 * Minesweeper Block
 * Stores information about the state of the block.
 */
public class Block {

    /**
     * Flag for if the block is a bomb.
     */
    private Boolean bomb;
    /**
     * Flag for if the block is visible in the GUI.
     */
    private Boolean visible;
    /**
     * Number of bombs surrounding the block.
     */
    private int surr = 0;

    /**
     * X position of the block.
     */
    private int x;

    /**
     * Y position of the block.
     */
    private int y;

    /**
     * If the block is flagged.
     */
    private boolean flag;

    /**
     * If the block is currently being analyzed by the auto algorithm.
     */
    private boolean aCurrent;

    /**
     * Constructor for a block.
     * @param i The Y position of the block.
     * @param j The X position of the block.
     */
    public Block(int i, int j){
        y = i;
        x = j;
        visible = false;
        bomb = false;
        flag = false;
        aCurrent = false;
    }

    /**
     * Constructor for creating specific blocks. Used for creating deep copies.
     * @param i The Y position of the block.
     * @param j The X position of the block.
     * @param surr Number of surrounding bombs.
     * @param vis If visible.
     * @param bomb If is bomb.
     * @param flag If flagged.
     * @param curr If currently being analyzed by auto algorithm.
     */
    public Block(int i, int j, int surr, boolean vis, boolean bomb, boolean flag, boolean curr){
        y = i;
        x = j;
        visible = vis;
        this.bomb = bomb;
        this.flag = flag;
        this.surr = surr;
        aCurrent = curr;
    }

    /**
     * Gets bomb.
     * @return bomb flag.
     */
    public Boolean getBomb(){
        return bomb;
    }

    /**
     * Gets visible.
     * @return visible flag.
     */
    public Boolean getVisible(){
        return visible;
    }

    /**
     * Gets number of surrounding bombs.
     * @return Int number of surrounding bombs.
     */
    public int getSurr(){
        return surr;
    }

    /**
     * Gets if block is flagged.
     * @return If block is flagged.
     */
    public boolean getFlag(){
        return flag;
    }

    /**
     * Gets if block is the current block being analyzed.
     * @return
     */
    public boolean getCurr(){
        return aCurrent;
    }

    /**
     * Gets the Y position of the block.
     * @return The Y position of the block.
     */
    public int getY(){
        return y;
    }

    /**
     * Gets the X position of the block.
     * @return The X position of the block.
     */
    public int getX(){
        return x;
    }

    /**
     * Sets the bomb flag to true.
     */
    public void setBomb(){
        bomb = true;
    }

    /**
     * Sets the visible flag to true.
     */
    public void setVisible(){
        visible = true;
    }

    /**
     * Sets the flag to specified boolean.
     * @param flag New flag boolean.
     */
    public void setFlag(boolean flag){
        this.flag = flag;
    }

    /**
     * Sets the current flag to specified boolean.
     * @param curr New current flag.
     */
    public void setCurr(boolean curr){
        this.aCurrent = curr;
    }

    /**
     * Increments the number of surrounding bombs value.
     */
    public void upSurr(){
        surr++;
    }

    /**
     * Static method to create and return a new square board of blocks.
     * Plants bombs and initializes all blocks with proper values for surrounding blocks.
     * Ensures that the surrounding area of the first initial click is
     * free of bombs (minimum 3x3 area free of bombs).
     * @param dim Dimensions of new square board.
     * @param y The starting y location.
     * @param x The starting x location.
     * @return The created board of Blocks.
     */
    public static Block[][] makeBoard(int dim, int y, int x){
        //random numbers
        int num1;
        int num2;

        //Creates new board
        Block[][] Blocks = new Block[dim][dim];

        Random r = new Random();

        //Initializes board
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                Blocks[i][j] = new Block(i, j);
            }
        }

        //Place bombs on to the board
        for(int bombCap = (dim*dim)/5; bombCap > 0; bombCap--){

            //Random coordinates for bomb placement
            num1 = r.nextInt(dim);
            num2 = r.nextInt(dim);

            //Skips if within initial 3x3 starting location
            if((Math.abs(y - num1) <= 1) && (Math.abs(x - num2) <= 1)){
                bombCap++;
                continue;
            }
            //Makes the block a bomb if not already a bomb
            if(!Blocks[num1][num2].getBomb()){
                Blocks[num1][num2].setBomb();
                bombNum(Blocks, num1, num2);
            }
            //Skips otherwise
            else{
                bombCap++;
            }
        }

        return Blocks;
    }

    /**
     * Increments surr for all surrounding blocks of the bomb.
     * @param Blocks The board of blocks.
     * @param i y of the bomb.
     * @param j x of the bomb.
     */
    private static void bombNum(Block[][] Blocks, int i, int j){

        //Catches invalid indexes
        try {
            Blocks[i + 1][j].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i + 1][j + 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i + 1][j - 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i - 1][j].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i - 1][j + 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i - 1][j - 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i][j + 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            Blocks[i][j - 1].upSurr();
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }


    /*
    //For debugging purposes.
    public static Block[][] cusBoard(){
        Block[][] board = new Block[20][20];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = new Block(i, j);
            }
        }
        board[0][3].setBomb();
        board[1][3].setBomb();
        board[3][0].setBomb();
        board[3][1].setBomb();
        board[3][2].setBomb();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j].getBomb()) {
                    bombNum(board, i, j);
                }
            }
        }
        return board;
    }

     */
}
