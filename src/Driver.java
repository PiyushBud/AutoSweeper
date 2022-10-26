public class Driver {

    /**
     * Starts the menu GUI
     * @param args
     */
    public static void main(String[] args) {
        try {
            new Menu();
        }
        catch(ArrayIndexOutOfBoundsException e){}
    }

}
