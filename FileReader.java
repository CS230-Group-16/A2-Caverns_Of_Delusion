
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
            System.exit(0);
        }

        return in;

    }

    /**
     * Read file and create board
     *
     * @param filename name of board file to read
     * @return created board
     */
    public static Board readBoardFile(String filename) {
        Scanner in = readFile(filename);

        int width;
        int height;
        int[] player1Location = {-1, -1};
        int[] player2Location = {-1, -1};
        int[] player3Location = {-1, -1};
        int[] player4Location = {-1, -1};
        int numOfFixedTiles;
        int numOfPlayers;

        String temp = in.nextLine();
        String[] tempArr;
        
        width = Integer.parseInt(temp.split(",")[0]);
        height = Integer.parseInt(temp.split(",")[1]);

        //dont need next 2 lines from file as they are for the SilkBag
        in.nextLine();
        in.nextLine();
        
        numOfFixedTiles = Integer.parseInt(in.nextLine()) + 1;
        FloorTile[] tiles = new FloorTile[numOfFixedTiles];
        //String[] tiles = new String[numOfFixedTiles];
        int[][] tileLocations = new int[numOfFixedTiles][2];
        
        for (int i = 1; i < numOfFixedTiles; i++) {
            temp = in.nextLine();
            if (temp.contains("CORNER")) {
                tempArr = temp.split(",");
                tiles[i] = new CornerTile(Integer.parseInt(tempArr[2]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            } else if (temp.contains("TSHAPE")) {
                tempArr = temp.split(",");
                tiles[i] = new TShapeTile(Integer.parseInt(tempArr[2]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            } else if (temp.contains("STRAIGHT")) {
                tempArr = temp.split(",");
                tiles[i] = new StraightTile(Integer.parseInt(tempArr[2]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            }
        }
        
        tiles[0] = new GoalTile();
        //tiles[0] = "GOAL";
        tempArr = in.nextLine().split(",");
        tileLocations[0][0] = Integer.parseInt(tempArr[0]);
        tileLocations[0][1] = Integer.parseInt(tempArr[1]);
        
        numOfPlayers = Integer.parseInt(in.nextLine());
        
        for (int i = 0; i < numOfPlayers; i++) {
            tempArr = in.nextLine().split(","); 
            switch (i) {
                case 0:
                    player1Location[0] = Integer.parseInt(tempArr[0]);
                    player1Location[1] = Integer.parseInt(tempArr[1]);
                    break;
                case 1:
                    player2Location[0] = Integer.parseInt(tempArr[0]);
                    player2Location[1] = Integer.parseInt(tempArr[1]);
                    break;
                case 2:
                    player3Location[0] = Integer.parseInt(tempArr[0]);
                    player3Location[1] = Integer.parseInt(tempArr[1]);
                    break;
                case 3:
                    player4Location[0] = Integer.parseInt(tempArr[0]);
                    player4Location[1] = Integer.parseInt(tempArr[1]);
                    break;
                default:
                    break;
            }
        }
        
        System.out.println(Arrays.toString(player1Location) + " " + Arrays.toString(player2Location) + " " + Arrays.toString(player3Location) + " " + Arrays.toString(player4Location) + " " + width + " " + height + " " + Arrays.toString(tiles));
        Board board = new Board(player1Location, player2Location, player3Location, player4Location, width, height, tiles, tileLocations);
        return board;
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
        
        return new Player(username,gamesWon,gamesLost);
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
                        tiles[pos] = new EffectTile("FIRE",-1);
                        pos++;
                        break;
                    case 1:
                        tiles[pos] = new EffectTile("ICE",-1);
                        pos++;
                        break;
                    case 2:
                        tiles[pos] = new MovementTile("DOUBLEMOVE",-1);
                        pos++;
                        break;
                    case 3:
                        tiles[pos] = new MovementTile("BACKTRACK",-1);
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
                        tiles[pos] = new StraightTile(0);
                        pos++;
                        break;
                    case 1:
                        tiles[pos] = new TShapeTile(0);
                        pos++;
                        break;
                    case 2:
                        tiles[pos] = new CornerTile(0);
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
                        p.insertTile(new EffectTile("FIRE",-1));
                        break;
                    case "ice":
                        p.insertTile(new EffectTile("ICE",-1));
                        break;
                    case "double":
                        p.insertTile(new MovementTile("DOUBLEMOVE",-1));
                        break;
                    case "backtrack":
                        p.insertTile(new MovementTile("DOUBLEMOVE",-1));
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
