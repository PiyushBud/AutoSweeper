import java.util.ArrayList;

/**
 * Class to auto solve minesweeper boards.
 * Assesses flagging and clearing based on surroundings of single blocks.
 */
public class Auto {

    /**
     * Board of minesweeper blocks.
     */
    private Block[][] board;
    /**
     * Y position of current block.
     */
    private int i;
    /**
     * X position of current block.
     */
    private int j;
    /**
     * Number of blocks left to be cleared.
     */
    private int remaining;


    /**
     * Constructor. Initializes the initial board and the starting location.
     * @param board The initial board.
     * @param i Starting y position.
     * @param j Starting x position.
     * @param remaining The number of blocks left to be cleared.
     */
    public Auto(Block[][] board, int i, int j, int remaining){
        this.i = i;
        this.j = j;
        this.board = board;
        //Number of blocks left to be cleared
        this.remaining = remaining;
    }

    /**
     * Checks the surroundings of current position and flags or clears blocks
     * based on algorithm.
     * @return Integer 0 if changes to board occur and -1 otherwise.
     */
    public int next(){
        //List of visible surrounding blocks
        ArrayList<Block> vis = findSurroundingVis(); //Currently unused

        //List of invisible surrounding blocks that are not flagged
        ArrayList<Block> invis = findSurroundingInvis();

        //Number of flags surrounding the current position
        int numFlags = countFlags();

        //The block currently checking for
        Block current = board[i][j];

        //No surrounding invisible blocks, no action required
        if(invis.size() == 0){
            return -1;
        }

        /*
        If the number of surrounding flagged and invisible blocks equal
        the number of surrounding bombs, then the algorithm will flag the remaining
        invisible blocks.
         */
        if((invis.size() + numFlags) == current.getSurr()){
            for(Block b: invis){
                b.setFlag(true);
            }
            return 0;
        }

        /*
        If the number of surrounding bombs is equal to number of surrounding flagged blocks,
        then the program will clear the blocks in the invis list.
         */
        if(current.getSurr() == numFlags){
            for(Block b: invis){
                if(!b.getFlag()) {
                    clearTile(b.getY(), b.getX());  //Clear the block at specified location
                    remaining--;    //Decrement the remaining blocks to clear
                }
            }
            return 0;
        }

        return -1;
    }

    /**
     * Runs the next algorithm and adds an updated board to a list of board states each time
     * the next algorithm changes the board. Loops until no more actions are available, max counter
     * is hit, or if all non-bomb tiles have been cleared.
     * @return ArrayList of all relevant board states to be cycled through in Game.
     */
    public ArrayList<Block[][]> getList(){
        int count = 0;
        int starti = i;
        int startj = j;
        //Creates the return list and adds the initial board
        ArrayList<Block[][]> retList = new ArrayList<>();
        retList.add(copyBoard());
        while(true){
            //Finds next visible block
            findNext();

            if(i == starti && j == startj){
                System.out.println("Out of Options Break");
                System.out.println(j+","+i);
                break;
            }

            //Next algorithm changed the board
            if(next() == 0){
                starti = i;
                startj = j;
                board[i][j].setCurr(true);
                //Adds a copy of the 
                retList.add(copyBoard());
                board[i][j].setCurr(false);
            }

            //Full clear win condition
            if(remaining == 0){
                retList.add(null);  //Places null at last location to indicate win
                break;
            }

            //Catch infinite loop
            if (count > 100000){
                System.out.println("Count Break");
                break;
            }
            count++;
        }
        return retList;
    }

    /**
     * Finds the next block to test. Simple algorithm, iterates through list
     * and finds next visible block.
     */
    private void findNext(){
        j++; //Sets to next X position
        for(int l = i; l < board.length; l++){
            for(int k = j; k < board[l].length; k++){   //l and k start at i and j
                if(board[l][k].getVisible()){   //Finds the next visible block
                    i = l;
                    j = k;
                    return;
                }
            }
            j = 0;  //Resets j
        }
        //End of array case, start at beginning
        i = 0;
        j = -1;
        findNext();
    }

    /**
     * Counts the number of surrounding blocks that are flagged.
     * @return The number of surrounding flagged blocks.
     */
    private int countFlags(){
        int count = 0;
        /*
        Checks each surrounding location and skips if index out of bounds
         */
        try{
            if(board[i][j+1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i][j-1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j-1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j+1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j+1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j-1].getFlag()){
                count++;
            }
        }
        catch (IndexOutOfBoundsException e){}
        return count;
    }

    /**
     * Creates deep copy of the board to store in the list of board states.
     * @return A deep copy of the current board.
     */
    private Block[][] copyBoard(){
        Block[][] newBoard = new Block[board.length][board.length];
        for(int l = 0; l < board.length; l++){
            for(int k = 0; k < board[l].length; k++){
                //New block with using extended constructor to copy relevant data
                newBoard[l][k] = new Block(l, k, board[l][k].getSurr(), board[l][k].getVisible(),
                        board[l][k].getBomb(), board[l][k].getFlag(), board[l][k].getCurr());
            }
        }
        return newBoard;
    }

    /**
     * Finds all the surrounding blocks that are not visible and not flagged.
     * @return List of blocks that are "invis".
     */
    private ArrayList<Block> findSurroundingInvis(){
        ArrayList<Block> num = new ArrayList<>(8); //Max size of 8
        /*
        Checks each surrounding block for if visible and if flagged.
        Skips if out of bounds.
         */
        try{
            if(!board[i][j+1].getVisible() && !board[i][j+1].getFlag()){
                num.add(board[i][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i][j-1].getVisible() && !board[i][j-1].getFlag()){
                num.add(board[i][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j].getVisible() && !board[i+1][j].getFlag()){
                num.add(board[i+1][j]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j].getVisible() && !board[i-1][j].getFlag()){
                num.add(board[i-1][j]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j+1].getVisible() && !board[i+1][j+1].getFlag()){
                num.add(board[i+1][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j+1].getVisible() && !board[i-1][j+1].getFlag()){
                num.add(board[i-1][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j-1].getVisible() && !board[i+1][j-1].getFlag()){
                num.add(board[i+1][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j-1].getVisible() && !board[i-1][j-1].getFlag()){
                num.add(board[i-1][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}

        return num;
    }

    /**
     * Finds all surrounding blocks that are visible.
     * @return List of surrounding blocks that are visible.
     */
    private ArrayList<Block> findSurroundingVis(){
        ArrayList<Block> num = new ArrayList<>(8); //Max size of 8
        /*
        Checks each surrounding block for if visible.
        Skips if out of bounds.
         */
        try{
            if(board[i][j+1].getVisible()){
                num.add(board[i][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i][j-1].getVisible()){
                num.add(board[i][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j].getVisible()){
                num.add(board[i+1][j]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j].getVisible()){
                num.add(board[i-1][j]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j+1].getVisible()){
                num.add(board[i+1][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j+1].getVisible()){
                num.add(board[i-1][j+1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i+1][j-1].getVisible()){
                num.add(board[i+1][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}
        try{
            if(board[i-1][j-1].getVisible()){
                num.add(board[i-1][j-1]);
            }
        }
        catch (IndexOutOfBoundsException e){}

        return num;
    }

    /**
     * Processes the clearing of a tile. Does not clear the block if it is flagged
     * or already visible.
     *
     * Sets the selected block to visible otherwise.
     * If the block has 0 surrounding bombs, the program will recursively
     * clear surrounding blocks and check for additional 0 blocks.
     *
     * @param l Y position of selected block.
     * @param k X position of selected block.
     */
    private void clearTile(int l, int k){
        //Out of bounds case
        if(l < 0 || k < 0 || l >= board.length || k >= board.length){
            throw new ArrayIndexOutOfBoundsException();
        }

        //Check if block is already visible or flagged
        if(board[l][k].getVisible() || board[l][k].getFlag()){
            return;
        }

        //Sets the block to be visible
        board[l][k].setVisible();

        //Check for if the cleared block has 0 surrounding bombs.
        if(board[l][k].getSurr() == 0){
            clearZeros(l, k);
            return;
        }
    }

    /**
     * Clears the blocks surrounding the selected block by calling the clearTile method.
     * @param l The Y position of the selected block.
     * @param k The X position of the selected block.
     */
    private void clearZeros(int l, int k){
        /*
        Checks each surrounding block for 0 and ignores out of bounds
         */
        try{
            clearTile(l, k+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l, k-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l+1, k);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l+1, k+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l+1, k-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l-1, k);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l-1, k+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(l-1, k-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }
}

