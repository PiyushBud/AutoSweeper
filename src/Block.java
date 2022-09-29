
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

    /**
     * Constructor, sets visible and bomb to false.
     */
    public Block(){
        visible = false;
        bomb = false;
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
     * Increments the number of surrounding bombs value.
     */
    public void upSurr(){
        surr++;
    }

    /**
     * Static method to return a new square board. Sets up bombs and the number of bombs surrounding
     * each square. Plants bombs in a 4th of all Blocks. Leaves at least a 3x3 clear area around
     * the starting location.
     * @param dim Dimensions of new square board.
     * @param y The starting y location.
     * @param x The starting x location.
     * @return The created baord of Blocks.
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
                Blocks[i][j] = new Block();
            }
        }

        //Place bombs in to the list
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
