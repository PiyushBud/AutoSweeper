import java.lang.reflect.Array;
import java.util.ArrayList;
/*
public class Auto {
/*
    private Block[][] blocks;
    private int i;
    private int j;
    */

    /*
    public Auto(int dim, int i, int j){
        this.i = i;
        this.j = j;
        this.blocks = Block.makeBoard(dim, i, j);
    }

    /**
     * Finds next block to check surroundings for blocks to flag
     * or clear.
     * @return 16 by 2 2D array with first 8 rows being coordinates to flag
     * and last 8 rows being coordinates to clear.

    public ArrayList<Block> next(int i, int j, Block[] blocks, ){
        ArrayList<Block> retArr = new ArrayList<>(16);
        ArrayList<Block> vis = findSurroundingVis();
        ArrayList<Block> invis = findSurroundingInvis();

        if(invis.size() == 0){
            return null;
        }

        if(invis.size() == blocks[i][j].getSurr()){
            return invis;
        }

        return null;
    }

    private void findNext(){
        for(int k = i; k < blocks.length; k++){
            for(int l = j; l < blocks[k].length; l++){
                if(blocks[k][l].getVisible()){
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
            if(!blocks[i][j+1].getVisible()){
                num.set(index, blocks[i][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i][j-1].getVisible()){
                num.set(index, blocks[i][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j].getVisible()){
                num.set(index, blocks[i+1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j].getVisible()){
                num.set(index, blocks[i-1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j+1].getVisible()){
                num.set(index, blocks[i+1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j+1].getVisible()){
                num.set(index, blocks[i-1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j-1].getVisible()){
                num.set(index, blocks[i+1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j-1].getVisible()){
                num.set(index, blocks[i-1][j-1]);
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
            if(blocks[i][j+1].getVisible()){
                num.set(index, blocks[i][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i][j-1].getVisible()){
                num.set(index, blocks[i][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i+1][j].getVisible()){
                num.set(index, blocks[i+1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i-1][j].getVisible()){
                num.set(index, blocks[i-1][j]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i+1][j+1].getVisible()){
                num.set(index, blocks[i+1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i-1][j+1].getVisible()){
                num.set(index, blocks[i-1][j+1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i+1][j-1].getVisible()){
                num.set(index, blocks[i+1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(blocks[i-1][j-1].getVisible()){
                num.set(index, blocks[i-1][j-1]);
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}

        return num;
    }
}
     */
