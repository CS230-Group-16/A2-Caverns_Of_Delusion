
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Bartosz Kubica & Marius Antemir
 * @version 0.1
 */
public class FileReader {
    
    /**
     * Open scanner to read board file
     * @param filename name of file to be opened
     * @return created board
     */
    public Scanner readFile(String filename) {
        Scanner in = new Scanner(System.in);
        File file = new File(filename);

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in directory");
            System.exit(0);
        }

        return in;

    }
    
    /**
     * Read file and create board
     * @param in scanner
     * @return created board
     */
    private Board readBoardFile(String filename) {
        Scanner in = readFile(filename);
        
        int width = 0;
        int height = 0;
        int[] player1Location = {0,0};
        int[] player2Location = {0,0};
        int[] player3Location = {0,0};
        int[] player4Location = {0,0};
        String[] fixedTiles = {"Goal"};
        int[] tileRotation = {0,0};
        int[] tileLocation = {0,0};
        int temp;
        
        //read file
        //skip lines 2 and 3
        Board board = new Board(player1Location, player2Location, player3Location, player4Location, width, height, fixedTiles, tileRotation, tileLocation);

        return null;
    }
    
    /**
     * Read Player profile
     * @return player profile
     */
    public Player readPlayerFile(){
        Player p = new Player();
        return null;
    }
    
    /**
     * delete player profile
     * @param filename name of file to delete
     */
    public void deleteFile(String filename){
        File f = new File(filename);
        f.delete();
    }
    
    public SilkBag readSilkFile(String filename){
        Scanner in = readFile(filename);
        
        String stringTemp;
        String [] stringTempArr;
        int intTemp;
        int [] intTempArr = new int[3];
        
        stringTemp = in.nextLine();
        stringTemp = in.nextLine();
        stringTempArr = stringTemp.split(",");
        
        for(int i = 0; i < stringTempArr.length; i++){
            intTempArr[i] = Integer.parseInt(stringTempArr[i]);
        }
        
        
        return null;
    }
}