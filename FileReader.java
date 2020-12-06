import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * This reads files.
 * @author Bartosz Kubica & Marius Antemir.
 * @version 1.0
 */
public class FileReader {
    //CHANGE TO PERSONAL DIRECTORY BEFORE RUNNING
    public static final String DIRECTORY = "D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/files/";
    
    /**
     * Open scanner to read board file.
     * @param filename Name of file to be opened.
     * @return in The scanner used to read the file.
     */
    private static Scanner readFile(String filename) {
        Scanner in = new Scanner(System.in);
        File file = new File(filename);

        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in directory");
            e.printStackTrace();
            System.out.println();
        }
        return in;
    }

    /**
     * Read file and create board.
     * @param filename Name of board file to read.
     * @return created board.
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

        //read the size of the board
        width = Integer.parseInt(temp.split(",")[0]);
        height = Integer.parseInt(temp.split(",")[1]);

        //dont need next 2 lines from file as they are for the SilkBag
        String line1 = in.nextLine();
        String line2 = in.nextLine();

        //read the fixed tiles
        numOfFixedTiles = Integer.parseInt(in.nextLine()) + 1;
        FloorTile[] fixedTiles = new FloorTile[numOfFixedTiles];
        //String[] tiles = new String[numOfFixedTiles];
        int[][] tileLocations = new int[numOfFixedTiles][2];

        //set the fixed tiles
        for (int i = 1; i < numOfFixedTiles; i++) {
            temp = in.nextLine();
            if (temp.contains("CORNER")) {
                tempArr = temp.split(",");
                fixedTiles[i] = new CornerTile(Integer.parseInt(tempArr[3]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            } else if (temp.contains("TSHAPE")) {
                tempArr = temp.split(",");
                fixedTiles[i] = new TShapeTile(Integer.parseInt(tempArr[3]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            } else if (temp.contains("STRAIGHT")) {
                tempArr = temp.split(",");
                fixedTiles[i] = new StraightTile(Integer.parseInt(tempArr[3]));
                //tiles[i] = tempArr[2];
                tileLocations[i][0] = Integer.parseInt(tempArr[0]);
                tileLocations[i][1] = Integer.parseInt(tempArr[1]);
            }
        }

        //set the goal tile
        fixedTiles[0] = new GoalTile();
        //tiles[0] = "GOAL";
        tempArr = in.nextLine().split(",");
        tileLocations[0][0] = Integer.parseInt(tempArr[0]);
        tileLocations[0][1] = Integer.parseInt(tempArr[1]);

        //fill in the rest of the board with random floor tiles
        FloorTile[] floorTiles = readFloorTile(line2);
        int numOfRandTiles = (width * height) - fixedTiles.length;
        Random rand = new Random();
        int randInt;
        FloorTile[] randTiles = new FloorTile[numOfRandTiles];
        for (int i = 0; i < randTiles.length; i++) {
            randInt = rand.nextInt(floorTiles.length);
            while (floorTiles[randInt] == null) {
                randInt = rand.nextInt(floorTiles.length);
            }
            randTiles[i] = floorTiles[randInt];
            floorTiles[randInt] = null;
        }

        //read the number of players
        numOfPlayers = Integer.parseInt(in.nextLine());

        for (int i = 0; i < numOfPlayers; i++) {
            tempArr = in.nextLine().split(",");
            //sets the locations of each player
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
        //gets the action tiles
        ActionTile[] actionTiles = readActionTile(line1);
        Tile[] tiles = new Tile[actionTiles.length + floorTiles.length];
        int pos = 0;
        for (int i = 0; i < actionTiles.length; i++) {
            tiles[pos] = actionTiles[i];
            pos++;
        }
        for (int i = 0; i < floorTiles.length; i++) {
            tiles[pos] = floorTiles[i];
            pos++;
        }

        SilkBag silkBag = new SilkBag(tiles);

        //System.out.println(Arrays.toString(player1Location) + " " + Arrays.toString(player2Location) + " " + Arrays.toString(player3Location) + " " + Arrays.toString(player4Location) + " " + width + " " + height + " " + Arrays.toString(tiles));
        Board board = new Board(player1Location, player2Location, player3Location, player4Location, width, height, fixedTiles, tileLocations, randTiles, silkBag);
        return board;
    }

    /**
     * Read Player profile.
     * @param username Name of user for file to be found.
     * @return player Profile of the player.
     */
    public static Player readPlayerFile(String username) {
        int gamesWon = 0;
        int gamesLost = 0;
        //reading the profile txt files with a scanner
        try {
            Scanner in = readFile(DIRECTORY + "players/" + username + ".txt");
            gamesWon = in.nextInt();
            gamesLost = in.nextInt();
        } catch (Exception e) {
            System.err.println("Player not Found");
        }
        return new Player(username, gamesWon, gamesLost);
    }

    /**
     * Delete file.
     * @param filename name of file to delete.
     */
    public static void deleteFile(String filename) {
        File f = new File(DIRECTORY + filename);
        f.delete();
    }

    /**
     * Reads board file and gets the action tiles to place into silkbag.
     * @param line1 Line of number of action tiles.
     * @return Tiles to add to silkbag.
     */
    public static ActionTile[] readActionTile(String line1) {
        String[] stringTempArr;
        int totalTiles = 0;
        int[] actionTiles = null;

        //gets the action tiles from the string given
        stringTempArr = line1.split(",");
        actionTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            actionTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += actionTiles[i];
        }

        ActionTile[] tiles = new ActionTile[totalTiles];
        int pos = 0;

        //creating the action tiles and adding them to the array
        for (int i = 0; i < actionTiles.length; i++) {
            for (int y = 0; y < actionTiles[i]; y++) {
                //tile[pos] = new ActionTile("fire");
                switch (i) {
                    case 0:
                        tiles[pos] = new EffectTile("FIRE", -1);
                        pos++;
                        break;
                    case 1:
                        tiles[pos] = new EffectTile("ICE", -1);
                        pos++;
                        break;
                    case 2:
                        tiles[pos] = new MovementTile("DOUBLEMOVE", -1);
                        pos++;
                        break;
                    case 3:
                        tiles[pos] = new MovementTile("BACKTRACK", -1);
                        pos++;
                        break;
                    default:
                        break;
                }
            }
        }
        return tiles;
    }

    /**
     * Reads board file and gets the floor tiles to place into silkbag.
     * @param line2 line of number of floor tiles
     * @return tiles to add to silkbag
     */
    public static FloorTile[] readFloorTile(String line2) {
        String[] stringTempArr;
        int totalTiles = 0;
        int[] floorTiles = null;

        //gets the floor tiles from the string given
        stringTempArr = line2.split(",");
        floorTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            floorTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += floorTiles[i];
        }

        FloorTile[] tiles = new FloorTile[totalTiles];
        int pos = 0;

        //creating the floor tiles and adding them to the array
        for (int i = 0; i < floorTiles.length; i++) {
            for (int y = 0; y < floorTiles[i]; y++) {
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
        return tiles;
    }

    /**
     * Read saved game file storing player info
     * @param filename Name of the file to open
     * @return Array of player profiles
     */
    public static RoundTable readSavedGameFileRoundTable(String filename) {
        //reading the parameters for RoundTable from the board file
        Scanner in = readFile(DIRECTORY + filename);
        String temp;
        String[] tempArr;
        int numTiles = Integer.parseInt(in.nextLine());
        for (int i = 0; i < numTiles; i++) {
            in.nextLine();
        }
        tempArr = in.nextLine().split(",");
        int numPlayers = Integer.parseInt(tempArr[0]);
        int turnCounter = Integer.parseInt(tempArr[1]);
        int counter = Integer.parseInt(tempArr[2]);

        boolean frozen;
        boolean engulfed;
        boolean fixed;
        boolean occupied;
        int rotation;
        Player[] players;
        Tile drawnTile;

        temp = in.nextLine();
        //sets the floor tiles for RoundTable
        if (temp.contains("CORNER")) {
            tempArr = temp.split(",");
            frozen = !"false".equals(tempArr[1]);
            engulfed = !"false".equals(tempArr[2]);
            fixed = !"false".equals(tempArr[3]);
            occupied = !"false".equals(tempArr[4]);
            rotation = Integer.parseInt(tempArr[5]);
            drawnTile = new CornerTile(frozen, engulfed, fixed, occupied, rotation);
        } else if (temp.contains("TSHAPE")) {
            tempArr = temp.split(",");
            frozen = !"false".equals(tempArr[1]);
            engulfed = !"false".equals(tempArr[2]);
            fixed = !"false".equals(tempArr[3]);
            occupied = !"false".equals(tempArr[4]);
            rotation = Integer.parseInt(tempArr[5]);
            drawnTile = new TShapeTile(frozen, engulfed, fixed, occupied, rotation);
        } else if (temp.contains("STRAIGHT")) {
            tempArr = temp.split(",");
            frozen = !"false".equals(tempArr[1]);
            engulfed = !"false".equals(tempArr[2]);
            fixed = !"false".equals(tempArr[3]);
            occupied = !"false".equals(tempArr[4]);
            rotation = Integer.parseInt(tempArr[5]);
            drawnTile = new StraightTile(frozen, engulfed, fixed, occupied, rotation);
        } else {
            drawnTile = null;
        }

        players = new Player[numPlayers];


        String username;
        int gamesWon;
        int gamesLost;
        int playerNum;
        int[][] path = new int[2][2];
        boolean backtrack;
        int numSpells;
        ActionTile [] spells;
        //sets player stats for when starting a game
        for (int i = 0; i < numPlayers; i++) {
            temp = in.nextLine();
            tempArr = temp.split(",");
            username = tempArr[0];
            gamesWon = Integer.parseInt(tempArr[1]);
            gamesLost = Integer.parseInt(tempArr[2]);
            playerNum = Integer.parseInt(tempArr[3]);
            path[0][0] = Integer.parseInt(tempArr[4].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+", ""));
            path[0][1] = Integer.parseInt(tempArr[5].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+", ""));
            path[1][0] = Integer.parseInt(tempArr[6].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+", ""));
            path[1][1] = Integer.parseInt(tempArr[7].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+", ""));
            backtrack = "true".equals(tempArr[8]);
            numSpells = Integer.parseInt(tempArr[9]);
            spells = new ActionTile[numSpells];
            //sets the player spells
            for (int j = 0; j < numSpells; j++) {
                temp = in.nextLine();
                if (temp.contains("FIRE")) {
                    tempArr = temp.split(",");
                    spells[j] = new EffectTile("FIRE", Integer.parseInt(tempArr[1]));
                } else if (temp.contains("ICE")) {
                    tempArr = temp.split(",");
                    spells[j] = new EffectTile("ICE", Integer.parseInt(tempArr[1]));
                } else if (temp.contains("BACKTRACK")) {
                    tempArr = temp.split(",");
                    spells[j] = new MovementTile("BACKTRACK", Integer.parseInt(tempArr[1]));
                } else if (temp.contains("DOUBLEMOVE")) {
                    tempArr = temp.split(",");
                    spells[j] = new MovementTile("DOUBLEMOVE", Integer.parseInt(tempArr[1]));
                }
            }
            players[i] = new Player(username, gamesWon, gamesLost, playerNum, path, backtrack, spells);
        }
        return new RoundTable(numPlayers, turnCounter, players, counter, drawnTile);
    }

    /**
     * Read game saved file to get silkbag contents.
     * @param filename File to read.
     * @return Created silk bag.
     */
    public static SilkBag readSavedGameFileSilkBag(String filename) {
        Scanner in = readFile(DIRECTORY + filename);
        String temp;
        String[] tempArr;
        boolean frozen;
        boolean engulfed;
        boolean fixed;
        boolean occupied;
        int rotation;
        ArrayList<Tile> bag = new ArrayList<Tile>();
        int numTiles = Integer.parseInt(in.nextLine());
        for (int i = 0; i < numTiles; i++) {
            //reads the file and looks for action tiles to put into the silkbag
            temp = in.nextLine();
            if (temp.contains("FIRE")) {
                tempArr = temp.split(",");
                bag.add(new EffectTile("FIRE", Integer.parseInt(tempArr[1])));
            } else if (temp.contains("ICE")) {
                tempArr = temp.split(",");
                bag.add(new EffectTile("ICE", Integer.parseInt(tempArr[1])));
            } else if (temp.contains("BACKTRACK")) {
                tempArr = temp.split(",");
                bag.add(new MovementTile("BACKTRACK", Integer.parseInt(tempArr[1])));
            } else if (temp.contains("DOUBLEMOVE")) {
                tempArr = temp.split(",");
                bag.add(new MovementTile("DOUBLEMOVE", Integer.parseInt(tempArr[1])));
                //reads the file and looks for floor tiles to put into the silkbag
            } else if (temp.contains("CORNER")) {
                tempArr = temp.split(",");
                frozen = "true".equals(tempArr[1]);
                engulfed = "true".equals(tempArr[2]);
                fixed = "true".equals(tempArr[3]);
                occupied = "true".equals(tempArr[4]);
                rotation = Integer.parseInt(tempArr[5]);
                bag.add(new CornerTile(frozen, engulfed, fixed, occupied, rotation));
            } else if (temp.contains("TSHAPE")) {
                tempArr = temp.split(",");
                frozen = "true".equals(tempArr[1]);
                engulfed = "true".equals(tempArr[2]);
                fixed = "true".equals(tempArr[3]);
                occupied = "true".equals(tempArr[4]);
                rotation = Integer.parseInt(tempArr[5]);
                bag.add(new TShapeTile(frozen, engulfed, fixed, occupied, rotation));
            } else if (temp.contains("STRAIGHT")) {
                tempArr = temp.split(",");
                frozen = "true".equals(tempArr[1]);
                engulfed = "true".equals(tempArr[2]);
                fixed = "true".equals(tempArr[3]);
                occupied = "true".equals(tempArr[4]);
                rotation = Integer.parseInt(tempArr[5]);
                bag.add(new StraightTile(frozen, engulfed, fixed, occupied, rotation));
            }
        }
        return new SilkBag(bag);
    }
    
    /**
     * This writes to a file. 
     * @param filename The name of the file.
     * @param text The text to write into the file.
     */
    public static void writeFile(String filename, String text) {
        try {
            FileWriter myWriter = new FileWriter(DIRECTORY + filename);
            myWriter.write(text);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /**
     * This reads in a file and then returns a board with the dimensions specified in the file.
     * @param filename The name of the file.
     * @return The board with the dimensions specified in the file.
     */
    public static Board readSavedBoardFile(String filename) {
        Scanner in = readFile(DIRECTORY + filename);
        String temp;
        String[] tempArr;
        int width;
        int height;
        int[] p1Loc = {-1, -1};
        int[] p2Loc = {-1, -1};
        int[] p3Loc = {-1, -1};
        int[] p4Loc = {-1, -1};
        boolean[] blockRow;
        boolean[] blockColumn;
        FloorTile[][] tileMap;

        //reads the size of the board
        temp = in.nextLine();
        tempArr = temp.split(",");
        width = Integer.parseInt(tempArr[0]);
        height = Integer.parseInt(tempArr[1]);

        //reads the player locaitons
        temp = in.nextLine();
        tempArr = temp.split(",");
        p1Loc[0] = Integer.parseInt(tempArr[0]);
        p1Loc[1] = Integer.parseInt(tempArr[1]);
        temp = in.nextLine();
        tempArr = temp.split(",");
        p2Loc[0] = Integer.parseInt(tempArr[0]);
        p2Loc[1] = Integer.parseInt(tempArr[1]);
        temp = in.nextLine();
        tempArr = temp.split(",");
        p3Loc[0] = Integer.parseInt(tempArr[0]);
        p3Loc[1] = Integer.parseInt(tempArr[1]);
        temp = in.nextLine();
        tempArr = temp.split(",");
        p4Loc[0] = Integer.parseInt(tempArr[0]);
        p4Loc[1] = Integer.parseInt(tempArr[1]);

        //finds the blocked rows
        temp = in.nextLine();
        tempArr = temp.split(",");
        blockRow = new boolean[tempArr.length];
        for (int i = 0; i < tempArr.length; i++) {
            blockRow[i] = !"false".equals(tempArr[i]);
        }
        //finds the blocked columns
        temp = in.nextLine();
        tempArr = temp.split(",");
        blockColumn = new boolean[tempArr.length];
        for (int i = 0; i < tempArr.length; i++) {
            blockColumn[i] = !"false".equals(tempArr[i]);
        }

        int x;
        int y;
        boolean frozen;
        boolean engulfed;
        boolean fixed;
        boolean occupied;
        int rotation;
        int[] goal = new int[2];

        //remove magic numbers
        tileMap = new FloorTile[width][height];
        //reads the file and sets the floor tiles
        while (in.hasNextLine()) {
            temp = in.nextLine();
            if (temp.contains("CORNER")) {
                tempArr = temp.split(",");
                x = Integer.parseInt(tempArr[0]);
                y = Integer.parseInt(tempArr[1]);
                frozen = !"false".equals(tempArr[3]);
                engulfed = !"false".equals(tempArr[4]);
                fixed = !"false".equals(tempArr[5]);
                occupied = !"false".equals(tempArr[6]);
                rotation = Integer.parseInt(tempArr[7]);
                tileMap[x][y] = new CornerTile(frozen, engulfed, fixed, occupied, rotation);
            } else if (temp.contains("TSHAPE")) {
                tempArr = temp.split(",");
                x = Integer.parseInt(tempArr[0]);
                y = Integer.parseInt(tempArr[1]);
                frozen = !"false".equals(tempArr[3]);
                engulfed = !"false".equals(tempArr[4]);
                fixed = !"false".equals(tempArr[5]);
                occupied = !"false".equals(tempArr[6]);
                rotation = Integer.parseInt(tempArr[7]);
                tileMap[x][y] = new TShapeTile(frozen, engulfed, fixed, occupied, rotation);
            } else if (temp.contains("STRAIGHT")) {
                tempArr = temp.split(",");
                x = Integer.parseInt(tempArr[0]);
                y = Integer.parseInt(tempArr[1]);
                frozen = !"false".equals(tempArr[3]);
                engulfed = !"false".equals(tempArr[4]);
                fixed = !"false".equals(tempArr[5]);
                occupied = !"false".equals(tempArr[6]);
                rotation = Integer.parseInt(tempArr[7]);
                tileMap[x][y] = new StraightTile(frozen, engulfed, fixed, occupied, rotation);
            } else if (temp.contains("GOAL")) {
                tempArr = temp.split(",");
                x = Integer.parseInt(tempArr[0]);
                y = Integer.parseInt(tempArr[1]);
                frozen = !"false".equals(tempArr[3]);
                engulfed = !"false".equals(tempArr[4]);
                fixed = !"false".equals(tempArr[5]);
                occupied = !"false".equals(tempArr[6]);
                rotation = Integer.parseInt(tempArr[7]);
                tileMap[x][y] = new GoalTile(frozen, engulfed, fixed, occupied, rotation);
                goal[0] = x;
                goal[1] = y;
            }
        }
        return new Board(p1Loc, p2Loc, p3Loc, p4Loc, width, height, tileMap, blockRow, blockColumn, goal);
    }
    
    /**
     * This reads in a file and then returns a leaderboard with the players and the file.
     * @param filename The name of the file.
     * @return A leaderboard.
     */
    public static Leaderboard readLeaderboardFile(String filename) {
        Scanner in = readFile(filename);
        String temp;
        String[] tempArr;
        ArrayList<Player> players = new ArrayList<Player>();

        while (in.hasNextLine()) {
            tempArr = in.nextLine().split(",");
            Player p = new Player(tempArr[0], Integer.parseInt(tempArr[1]), Integer.parseInt(tempArr[2]));
            players.add(p);
        }
        
        return new Leaderboard(players, filename);
    }
    
    /**
     * Read config file made in config menu to create game.
     * @param filename name of config file to open.
     * @return created game from file.
     */
    public static Game readGameConfig(String filename){
        Scanner in = readFile(DIRECTORY + filename);
        String board = in.nextLine();
        String [] players = in.nextLine().split(",");
        return new Game(DIRECTORY + "boards/" + board + ".txt",players);
    }
    
    public static Game readLoadConfig(String filename){
        Scanner in = readFile(DIRECTORY + filename);
        String [] files = in.nextLine().split(",");
        return new Game(files[0],files[1]);
    }
}