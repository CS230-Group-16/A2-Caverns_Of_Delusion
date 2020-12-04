
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
        String line1 = in.nextLine();
        String line2 = in.nextLine();

        numOfFixedTiles = Integer.parseInt(in.nextLine()) + 1;
        FloorTile[] fixedTiles = new FloorTile[numOfFixedTiles];
        //String[] tiles = new String[numOfFixedTiles];
        int[][] tileLocations = new int[numOfFixedTiles][2];

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

        fixedTiles[0] = new GoalTile();
        //tiles[0] = "GOAL";
        tempArr = in.nextLine().split(",");
        tileLocations[0][0] = Integer.parseInt(tempArr[0]);
        tileLocations[0][1] = Integer.parseInt(tempArr[1]);

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
     * Read Player profile
     *
     * @param username name of user for file to be found
     * @return player profile
     */
    public static Player readPlayerFile(String username) {
        int gamesWon = 0;
        int gamesLost = 0;

        try {
            Scanner in = readFile(username + ".txt");
            gamesWon = in.nextInt();
            gamesLost = in.nextInt();
        } catch (Exception e) {
            System.err.println("Player not Found");
        }

        return new Player(username, gamesWon, gamesLost);
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

    /**
     * Reads board file and gets the tiles to place into silkbag
     *
     * @param line1 line of number of action tiles
     * @return tiles to add to silkbag
     */
    public static ActionTile[] readActionTile(String line1) {
        String[] stringTempArr;
        int totalTiles = 0;
        int[] actionTiles = null;

        stringTempArr = line1.split(",");
        actionTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            actionTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += actionTiles[i];
        }

        ActionTile[] tiles = new ActionTile[totalTiles];
        int pos = 0;

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
     * Reads board file and gets the tiles to place into silkbag
     *
     * @param line2 line of number of floor tiles
     * @return tiles to add to silkbag
     */
    public static FloorTile[] readFloorTile(String line2) {
        String[] stringTempArr;
        int totalTiles = 0;
        int[] floorTiles = null;

        stringTempArr = line2.split(",");
        floorTiles = new int[stringTempArr.length];
        for (int i = 0; i < stringTempArr.length; i++) {
            floorTiles[i] = Integer.parseInt(stringTempArr[i]);
            totalTiles += floorTiles[i];
        }

        FloorTile[] tiles = new FloorTile[totalTiles];
        int pos = 0;

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
     *
     * @param filename name of file to open
     * @return array of player profiles
     */
    public static RoundTable readSavedGameFileRoundTable(String filename) {
        Scanner in = readFile(filename);
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
            frozen = !"false".equals(tempArr[3]);
            engulfed = !"false".equals(tempArr[4]);
            fixed = !"false".equals(tempArr[5]);
            occupied = !"false".equals(tempArr[6]);
            rotation = Integer.parseInt(tempArr[7]);
            drawnTile = new TShapeTile(frozen, engulfed, fixed, occupied, rotation);
        } else if (temp.contains("STRAIGHT")) {
            tempArr = temp.split(",");
            frozen = !"false".equals(tempArr[3]);
            engulfed = !"false".equals(tempArr[4]);
            fixed = !"false".equals(tempArr[5]);
            occupied = !"false".equals(tempArr[6]);
            rotation = Integer.parseInt(tempArr[7]);
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

        return new RoundTable(numPlayers, turnCounter, players, counter);
    }

    /**
     * read game saved file to get silkbag contents
     *
     * @param filename file to read
     * @return created silk bag
     */
    public static SilkBag readSavedGameFileSilkBag(String filename) {
        Scanner in = readFile(filename);
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

    public static void writeFile(String filename, String text) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(text);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Board readSavedBoardFile(String filename) {
        Scanner in = readFile(filename);
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

        temp = in.nextLine();
        tempArr = temp.split(",");
        width = Integer.parseInt(tempArr[0]);
        height = Integer.parseInt(tempArr[1]);

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

        temp = in.nextLine();
        tempArr = temp.split(",");
        blockRow = new boolean[tempArr.length];
        for (int i = 0; i < tempArr.length; i++) {
            blockRow[i] = !"false".equals(tempArr[i]);
        }
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

        //remove magic numbers
        tileMap = new FloorTile[width][height];
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
                tileMap[x][y] = new CornerTile( frozen, engulfed, fixed, occupied, rotation);
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
            }
        }
        return new Board(p1Loc, p2Loc, p3Loc, p4Loc, width, height, tileMap, blockRow, blockColumn);
    }
}
