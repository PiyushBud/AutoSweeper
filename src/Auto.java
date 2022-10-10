public class Auto {

    private Block[][] blocks;
    private int i;
    private int j;

    public Auto(Block[][] blocks, int i, int j){
        this.blocks = blocks;
        this.i = i;
        this.j = j;
    }

    public int next(){
        findNext();
        int[] surrounding = findSurrounding();
        if(blocks[i][j].getSurr() == (findSurrounding().length/2)){

        }
        return 0;
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

    private int[] findSurrounding(){
        int[] num = new int[16];
        int index = 0;
        try{
            if(!blocks[i][j+1].getVisible()){
                num[index] = i;
                index++;
                num[index] = j+1;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i][j-1].getVisible()){
                num[index] = i;
                index++;
                num[index] = j-1;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j].getVisible()){
                num[index] = i+1;
                index++;
                num[index] = j;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j].getVisible()){
                num[index] = i-1;
                index++;
                num[index] = j;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j+1].getVisible()){
                num[index] = i+1;
                index++;
                num[index] = j+1;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j+1].getVisible()){
                num[index] = i-1;
                index++;
                num[index] = j+1;
                index++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i+1][j-1].getVisible()){
                num[index] = i+1;
                index++;
                num[index] = j-1;
                index++;            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            if(!blocks[i-1][j-1].getVisible()){
                num[index] = i-1;
                index++;
                num[index] = j-1;
                index++;            }
        }
        catch (ArrayIndexOutOfBoundsException e){}

        return num;
    }
}
