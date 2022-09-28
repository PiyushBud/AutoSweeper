public class Test {
    public static void main(String[] args){
        int[][] grid = new int[10][10];
        incSurr(grid, 0,0);

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void incSurr(int[][] grid, int i, int j){
        try{
            grid[i][j+1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i][j-1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i+1][j]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i-1][j]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i+1][j+1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i+1][j-1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i-1][j+1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try{
            grid[i-1][j-1]++;
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }
}
