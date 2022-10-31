import java.util.ArrayList;

public class Auto {

    private Block[][] board;
    private int i;
    private int j;
    private int flagCount;



    public Auto(Block[][] board, int i, int j){
        this.i = i;
        this.j = j;
        this.board = board;
        flagCount = (board.length * board[0].length)/4;
    }

    /**
     * Finds next block to check surroundings for board to flag
     * or clear.
     * @return 16 by 2 2D array with first 8 rows being coordinates to flag
     * and last 8 rows being coordinates to clear.
     */
    public int next(){
        ArrayList<Block> vis = findSurroundingVis();
        ArrayList<Block> invis = findSurroundingInvis();
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
     * Does all auto algo stuff to solve as much of the board as possible.
     * @return ArrayList of all board states
     */
    public ArrayList<Block[][]> getList(){
        int count = 0;
        int starti = i;
        int startj = j;
        ArrayList<Block[][]> retList = new ArrayList<>();
        retList.add(copyBoard());
        Game.printBoard(board);
        while(true){
            findNext();
            if(i == starti && j == startj){
                System.out.println("Out of Options Break");
                System.out.println(j+","+i);
                break;
            }
            if(next() == 0){
                starti = i;
                startj = j;
                retList.add(copyBoard());
                Game.printBoard(board);
            }

            if (count > 100000){
                System.out.println("Count Break");
                break;
            }
            count++;
        }
        System.out.println(retList.size());
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
                newBoard[l][k] = board[l][k];
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
            clearZeros(i, j);
            return;
        }

        board[l][k].setVisible();
    }

    //Recursively clears zero tiles
    private void clearZeros(int l, int k){
        board[l][k].setVisible();
        try{
            clearTile(i, j+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i, j-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i+1, j);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i+1, j+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i+1, j-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i-1, j);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i-1, j+1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            clearTile(i-1, j-1);
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }
}

