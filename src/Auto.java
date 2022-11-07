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
     * Number of bombs left to be flagged.
     */
    private int flagCount;


    /**
     * Constructor.
     * @param board The initial board.
     * @param i Starting y position.
     * @param j Starting x position.
     */
    public Auto(Block[][] board, int i, int j){
        this.i = i;
        this.j = j;
        this.board = board;
        //Number of
        flagCount = (board.length * board[0].length)/5;
    }

    /**
     * Checks the surroundings of current position and flags or clears blocks
     * based on algorithm.
     * @return Integer 0 if changes to board occur and -1 otherwise.
     */
    public int next(){
        //List of visible surrounding blocks
        ArrayList<Block> vis = findSurroundingVis();
        //List of invisible surrounding blocks
        ArrayList<Block> invis = findSurroundingInvis();
        //Number of flags surrounding the current position
        int numFlags = countFlags();
        Block current = board[i][j];

        if(invis.size() == 0){
            return -1;
        }

        if((invis.size() + numFlags) == current.getSurr()){
            for(Block b: invis){
                b.setFlag(true);
                flagCount--;
            }
            return 0;
        }

        if(current.getSurr() == numFlags){
            for(Block b: invis){
                if(!b.getFlag()) {
                    clearTile(b.getY(), b.getX());
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
     * @return ArrayList of all board states to be cycled through in Game.
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
            if(flagCount == 0){
                retList.add(null);
                break;
            }

            //Catch infinite loop
            if (count > 100000){
                System.out.println("Count Break");
                break;
            }
            count++;
        }
        //System.out.println(retList.size());
        return retList;
    }

    private void findNext(){
        j++;
        for(int l = i; l < board.length; l++){
            for(int k = j; k < board[l].length; k++){
                if(board[l][k].getVisible()){
                    i = l;
                    j = k;
                    return;
                }
            }
            j = 0;
        }
        i = 0;
        j = -1;
        findNext();
    }

    private int countFlags(){
        int count = 0;
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

    private Block[][] copyBoard(){
        Block[][] newBoard = new Block[board.length][board.length];
        for(int l = 0; l < board.length; l++){
            for(int k = 0; k < board[l].length; k++){
                newBoard[l][k] = new Block(l, k, board[l][k].getSurr(), board[l][k].getVisible(), board[l][k].getBomb(), board[l][k].getFlag(), board[l][k].getCurr());

            }
        }
        return newBoard;
    }

    private ArrayList<Block> findSurroundingInvis(){
        ArrayList<Block> num = new ArrayList<>(8);
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

    private ArrayList<Block> findSurroundingVis(){
        ArrayList<Block> num = new ArrayList<>(8);
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
    private void clearTile(int l, int k){
        if(l < 0 || k < 0 || l >= board.length || k >= board.length){
            throw new ArrayIndexOutOfBoundsException();
        }

        if(board[l][k].getVisible() || board[l][k].getFlag()){
            return;
        }

        if(board[l][k].getSurr() == 0){
            clearZeros(l, k);
            return;
        }

        board[l][k].setVisible();
    }

    //Recursively clears zero tiles
    private void clearZeros(int l, int k){
        board[l][k].setVisible();
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

