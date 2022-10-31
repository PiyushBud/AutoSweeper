
import java.util.Random;

/**
 * Minesweeper Block
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
     * Number of surrounding bombs.
     */
    private int surr = 0;

    private int x;

    private int y;

    private boolean flag;

    /**
     * Constructor, sets visible and bomb to false.
     */
    public Block(int i, int j){
        y = i;
        x = j;
        visible = false;
        bomb = false;
        flag = false;
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

    public boolean getFlag(){
        return flag;
    }

    public int getY(){
        return y;
    }

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

    public void setFlag(boolean flag){
        this.flag = flag;
    }

    /**
     * Increments the number of surrounding bombs value.
     */
    public void upSurr(){
        surr++;
    }

    /**
     * Static method to return a new square board. Sets up bombs and the number of bombs surrounding
     * each square. Plants bomb in a 4th of all Blocks. Leaves at least a 3x3 clear area around
     * the starting location.
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
        for(int bombCap = (dim*dim)/4; bombCap > 0; bombCap--){

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
}
