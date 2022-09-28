
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Random;

public class MSBlock {

    private Boolean bomb;
    private Boolean visible;
    private int surr = 0;

    public MSBlock(){
        visible = false;
        bomb = false;
    }

    public Boolean getBomb(){
        return bomb;
    }

    public Boolean getVisible(){
        return visible;
    }

    public int getSurr(){
        return surr;
    }

    public void setBomb(){
        bomb = true;
    }

    public void setBomb(boolean flag){
        bomb = flag;
    }

    public void setVisible(){
        visible = true;
    }

    public void setSurr(int surr){
        surr = surr;
    }


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
    public static MSBlock[][] makeBoard(int dim, int y, int x){
        System.out.println("DID THIS WORK???");
        //random numbers
        int num1;
        int num2;
        MSBlock[][] Blocks = new MSBlock[dim][dim];

        Random r = new Random();

        //Initializes board
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                Blocks[i][j] = new MSBlock();
            }
        }

        //Places bombs in to the list
        for(int bombCap = (dim*dim)/4; bombCap > 0; bombCap--){

            //Random coordinates for bomb placement
            num1 = r.nextInt(dim);
            num2 = r.nextInt(dim);

            //Skips if within initial 3x3 starting location
            if((Math.abs(y - num1) <= 1) && (Math.abs(x - num2) <= 1)){
                bombCap++;
                System.out.println("yuh");
                continue;
            }
            if(!Blocks[num1][num2].getBomb()){
                Blocks[num1][num2].setBomb();
                bombNum(Blocks, num1, num2);
            }
            else{
                bombCap++;
            }
        }



        return Blocks;
    }

    private static void bombNum(MSBlock[][] Blocks, int i, int j){
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

    public static void main(String[] args){

    }
}
