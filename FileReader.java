
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Bartosz Kubica & Marius Antemir
 * @version 1.0
 */
public class FileReader {

    /**
     * Open scanner to read board file
     *
     * @param filename name of file to be opened
     * @return created board
     */
    private static Scanner readFile(String filename) {
        Scanner in = new Scanner(System.in);
        File file = new File(filename);

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in directory");
            e.printStackTrace();
        }

        return in;

    }

    /**
     * Read file and create board
     *
     * @param in scanner
     * @return created board
     */
    public static Board readBoardFile(String filename) {
        Scanner in = readFile(filename);

        int width = 0;
        int height = 0;
        int[] player1Location = {0, 0};
        int[] player2Location = {0, 0};
        int[] player3Location = {0, 0};
        int[] player4Location = {0, 0};
        String[] fixedTiles = {"Goal"};
        int[] tileRotation = {0, 0};
        int[] tileLocation = {0, 0};
        int temp;

        //read file
        //skip lines 2 and 3
        Board board = new Board(player1Location, player2Location, player3Location, player4Location, width, height, fixedTiles, tileRotation, tileLocation);

        return null;
    }

    /**
     * Read Player profile
     *
     * @param username name of user for file to be found
     * @return player profile
     */
    public static Player readPlayerFile(String username) {
        int gamesWon = 0;
        int gamesLost = 0;
        
        try {
            Scanner in = readFile(username+".txt");
            gamesWon = in.nextInt();
            gamesLost = in.nextInt();
        }catch(Exception e){
            System.err.println("Player not Found");
        }
        
        return new Player(username,gamesWon,gamesLost,0);
    }

    /**
     * delete file
     *
     * @param filename name of file to delete
     */
    public static void deleteFile(String filename) {
        File f = new File(filename);
        f.delete();
    }

    public static SilkBag readSilkFile(String filename) {
        Scanner in = readFile(filename);

        String stringTemp = in.nextLine();
        String[] stringTempArr;
        int totalTiles = 0;
        int[] actionTiles = null;
        int[] floorTiles = null;
        

        stringTemp = in.nextLine();
        stringTempArr = stringTemp.split(",");
        actionTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            actionTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += actionTiles[i];
        }
        stringTemp = in.nextLine();
        stringTempArr = stringTemp.split(",");
        floorTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            floorTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += floorTiles[i];
        }
        
        Tile[] tiles = new Tile[totalTiles];
        int pos = 0;
        
        for (int i = 0; i < actionTiles.length; i++){
            for (int y = 0; y < actionTiles[i]; y++){
                //tile[pos] = new ActionTile("fire");
                switch (i) {
                    case 0:
                        tiles[pos] = new EffectTile("FIRE");
                        pos++;
                        break;
                    case 1:
                        tiles[pos] = new EffectTile("ICE");
                        pos++;
                        break;
                    case 2:
                        tiles[pos] = new MovementTile(true);
                        pos++;
                        break;
                    case 3:
                        tiles[pos] = new MovementTile(false);
                        pos++;
                        break;
                    default:
                        break;
                }
            }
        }
        
        for (int i = 0; i < floorTiles.length; i++){
            for (int y = 0; y < floorTiles[i]; y++){
                //tile[pos] = new ActionTile("fire");
                switch (i) {
                    case 0:
                        tiles[pos] = new StraightTile("STRAIGHT",0);
                        pos++;
                        break;
                    case 1:
                        tiles[pos] = new TShapeTile("TSHAPE",0);
                        pos++;
                        break;
                    case 2:
                        tiles[pos] = new CornerTile("CORNER",0);
                        pos++;
                        break;
                    default:
                        break;
                }
            }
        }

        return new SilkBag(tiles);
    }
    
    /**
     * Read saved game file storing player info
     * @param filename name of file to open
     * @return array of player profiles
     */
    public static Player [] readSavedGameFile(String filename){
        Scanner in  = readFile(filename);
        ArrayList<Player> players = new ArrayList<>();
        String temp = "";
        String [] tempArr;
        
        while (in.hasNextLine()) {
            temp = in.nextLine();
            Player p = readPlayerFile(temp);
            temp = in.nextLine();
            tempArr = temp.split(",");
            
            for (String tempArr1 : tempArr) {
                switch (tempArr1) {
                    case "fire":
                        p.insertTile(new EffectTile("fire"));
                        break;
                    case "ice":
                        p.insertTile(new EffectTile("ice"));
                        break;
                    case "double":
                        p.insertTile(new MovementTile(true));
                        break;
                    case "backtrack":
                        p.insertTile(new MovementTile(false));
                        break;
                    default:
                        break;
                }
            }
            players.add(p);
        }
        return (Player[]) players.toArray();
    }
}
