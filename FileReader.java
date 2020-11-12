
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Bartosz Kubica & Marius Antemir
 * @version 0.1
 */
public class FileReader {
    
    public static Board readBoardFile(String filename) {
        Scanner in = new Scanner(System.in);
        File file = new File(filename);

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in directory");
            System.exit(0);
        }

        return FileReader.readBoardFile(in);

    }
    
    private static Board readBoardFile(Scanner in) {
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
        
        Board board = new Board(player1Location, player2Location, player3Location, player4Location, width, height, fixedTiles, tileRotation, tileLocation);

        return null;
    }
}