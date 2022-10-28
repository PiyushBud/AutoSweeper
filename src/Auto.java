import java.lang.reflect.Array;
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
    }

    /**
     * Finds next block to check surroundings for board to flag
     * or clear.
     * @return 16 by 2 2D array with first 8 rows being coordinates to flag
     * and last 8 rows being coordinates to clear.
     */
    public ArrayList<Block> next(){
        ArrayList<Block> retArr = new ArrayList<>(16);
        ArrayList<Block> vis = findSurroundingVis();
        ArrayList<Block> invis = findSurroundingInvis();

        if(invis.size() == 0){
            return null;
        }

        if(invis.size() == board[i][j].getSurr()){
            for(Block b: invis){
                b.setFlag(true);
            }
        }

        return null;
    }

    public ArrayList<Block[][]> getList(){

        ArrayList<Block[][]> retList = new ArrayList<>();
        retList.add(board);

        return null;
    }

    private void findNext(){
        for(int k = i; k < board.length; k++){
            for(int l = j; l < board[k].length; l++){
                if(board[k][l].getVisible()){
                    i = k;
                    j = l;
                    return;
                }
            }
        }
    }

    private ArrayList<Block> findSurroundingInvis(){
        ArrayList<Block> num = new ArrayList<>(8);
        int index = 0;
        try{
            if(!board[i][j+1].getVisible()){
                num.set(index, board[i][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i][j-1].getVisible()){
                num.set(index, board[i][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j].getVisible()){
                num.set(index, board[i+1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j].getVisible()){
                num.set(index, board[i-1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j+1].getVisible()){
                num.set(index, board[i+1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j+1].getVisible()){
                num.set(index, board[i-1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i+1][j-1].getVisible()){
                num.set(index, board[i+1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!board[i-1][j-1].getVisible()){
                num.set(index, board[i-1][j-1]);
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
            if(board[i][j+1].getVisible()){
                num.set(index, board[i][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i][j-1].getVisible()){
                num.set(index, board[i][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i+1][j].getVisible()){
                num.set(index, board[i+1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i-1][j].getVisible()){
                num.set(index, board[i-1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i+1][j+1].getVisible()){
                num.set(index, board[i+1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i-1][j+1].getVisible()){
                num.set(index, board[i-1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i+1][j-1].getVisible()){
                num.set(index, board[i+1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(board[i-1][j-1].getVisible()){
                num.set(index, board[i-1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}

        return num;
    }
}

