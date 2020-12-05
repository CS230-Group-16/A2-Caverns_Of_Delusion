
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The board that the tiles and players will be placed on.
 *
 * @author Bartosz Kubica & Marius Antemir.
 * @version 1.6
 */
public class Board {

    private static final SimpleDateFormat sdfH = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    private static int[] player1Location;
    private static int[] player2Location;
    private static int[] player3Location;
    private static int[] player4Location;
    private static int width;
    private static int height;
    private FloorTile[][] tileMap;
    private SilkBag silkBag;
    private boolean[] blockedRow;
    private boolean[] blockedColumn;
    private int[] goal;

    /**
     * Constructor to create a gameboard with every players' location, gameboard height&width and all non-moving tileMap, orientation of tileMap and their locations.
     *
     * @param player1Location First player's location.
     * @param player2Location Second player's location.
     * @param player3Location Third player's location.
     * @param player4Location Fourth player's location.
     * @param width The width of the gameboard.
     * @param height The height of the gameboard.
     * @param tileMap All fixed tileMap to place on to board.
     * @param tileLocation Location of tile .
     */
    Board(int[] player1Location, int[] player2Location, int[] player3Location, int[] player4Location,
            int width, int height, FloorTile[] fixedTiles, int[][] tileLocation, FloorTile[] randTiles, SilkBag silkBag) {
        this.player1Location = player1Location;
        this.player2Location = player2Location;
        this.player3Location = player3Location;
        this.player4Location = player4Location;
        this.width = width;
        this.height = height;
        this.blockedRow = new boolean[height];
        this.blockedColumn = new boolean[width];
        //unblocks the rows and columns that tiles can be pushed into
        for (int i = 0; i < height; i++) {
            this.blockedRow[i] = false;
        }
        for (int i = 0; i < width; i++) {
            this.blockedColumn[i] = false;
        }
        //blocks tiles that should not move
        blockRowColumn(tileLocation);
        this.tileMap = new FloorTile[width][height];
        for (int i = 0; i < fixedTiles.length; i++) {
            fixedTiles[i].setFixed(true);
            placeTile(fixedTiles[i], tileLocation[i]);
        }
        this.silkBag = silkBag;
        fillBoard(randTiles);
        this.goal = tileLocation[0];
    }

    /**
     * Second constructor with different parameters.
     *
     * @param p1Loc Player 1s location.
     * @param p2Loc Player 2s location.
     * @param p3Loc Player 3s location.
     * @param p4Loc Player 4s location.
     * @param width The width of the board.
     * @param height The height of the board.
     * @param tileMap All fixed tileMap to place on to board.
     * @param blockRow Rows that a tile cannot be placed in.
     * @param blockColumn Columns that a tile cannot be placed in.
     * @param goal The goal tile location.
     */
    public Board(int[] p1Loc, int[] p2Loc, int[] p3Loc, int[] p4Loc, int width, int height, FloorTile[][] tileMap, boolean[] blockRow, boolean[] blockColumn, int[] goal) {
        this.player1Location = p1Loc;
        this.player2Location = p2Loc;
        this.player3Location = p3Loc;
        this.player4Location = p4Loc;
        this.width = width;
        this.height = height;
        this.tileMap = tileMap;
        this.blockedRow = blockRow;
        this.blockedColumn = blockColumn;
        this.goal = goal;
    }

    /**
     * Sets the silk bag to be used.
     *
     * @param silkBag Silk bag instance.
     */
    public void setSilkBag(SilkBag silkBag) {
        this.silkBag = silkBag;
    }

    public int[] getGoalLocation() {
        return this.goal;
    }

    /**
     * Gets the player's location on gameboard.
     *
     * @param player Identification of player by number.
     * @return location Player's location in [x,y] format.
     */
    public int[] getPlayerLocation(int player) {
        int[] location = {-1, -1};
        if (player == 1) {
            location = this.player1Location;
        }
        if (player == 2) {
            location = this.player2Location;
        }
        if (player == 3) {
            location = this.player3Location;
        }
        if (player == 4) {
            location = this.player4Location;
        }
        return location;
    }

    /**
     * Sets the row and column to be blocked if the tile is blocked.
     *
     * @param tileLocations 2d array of blocked tiles.
     */
    public void blockRowColumn(int[][] tileLocations) {
        for (int[] tileLocation : tileLocations) {
            this.blockedColumn[tileLocation[0]] = true;
            this.blockedRow[tileLocation[1]] = true;
        }
    }

    /**
     * gets the blocked row.
     *
     * @return blockedRow The blocked row.
     */
    public boolean[] getBlockedRow() {
        return this.blockedRow;
    }

    /**
     * gets the blocked column.
     *
     * @return blockedRow The blocked column.
     */
    public boolean[] getBlockedColumn() {
        return this.blockedColumn;
    }

    /**
     * turns an x and y coordinate into a tile.
     *
     * @param x X location of tile.
     * @param y Y location of tile.
     * @return The tile at the position given.
     */
    public FloorTile getTileAt(int x, int y) {
        return this.tileMap[x][y];
    }

    /**
     * updates/sets a new location for the player on the gameboard.
     *
     * @param player Identification of player by number.
     * @param newLocation The new [x,y] location of the player.
     */
    public void updatePlayerLocation(int player, int[] newLocation) {
        switch (player) {
            case 1:
                if (newLocation[0] != -1 && newLocation[1] != -1) {
                    this.tileMap[this.player1Location[0]][this.player1Location[1]].setOccupied(false);
                    this.player1Location = newLocation;
                    this.tileMap[newLocation[0]][newLocation[1]].setOccupied(true);
                }
                break;
            case 2:
                if (newLocation[0] != -1 && newLocation[1] != -1) {
                    this.tileMap[this.player2Location[0]][this.player2Location[1]].setOccupied(false);
                    this.player2Location = newLocation;
                    this.tileMap[newLocation[0]][newLocation[1]].setOccupied(true);
                }
                break;
            case 3:
                if (newLocation[0] != -1 && newLocation[1] != -1) {
                    this.tileMap[this.player3Location[0]][this.player3Location[1]].setOccupied(false);
                    this.player3Location = newLocation;
                    this.tileMap[newLocation[0]][newLocation[1]].setOccupied(true);
                }
                break;
            case 4:
                if (newLocation[0] != -1 && newLocation[1] != -1) {
                    this.tileMap[this.player4Location[0]][this.player4Location[1]].setOccupied(false);
                    this.player4Location = newLocation;
                    this.tileMap[newLocation[0]][newLocation[1]].setOccupied(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Update the tile at specific location.
     *
     * @param newLocation New location of tile.
     * @param t Tile to be placed in that location.
     */
    public void updateTileLocation(int[] newLocation, FloorTile t) {
        this.tileMap[newLocation[0]][newLocation[1]] = t;
    }

    /**
     * inserts tile on gameboard and pushes all tileMap along.
     *
     * @param tile FloorTile to be inserted.
     * @param row Can only be inserted if true.
     * @param positionNum Where the tile should be inserted (column).
     * @param flip The side of the board the tile will be inserted into.
     * @return True if tile was successfully inserted, false otherwise.
     */
    public boolean insertTile(FloorTile tile, boolean row, int positionNum, boolean flip) {
        if (row) {
            for (int i = 0; i < this.width; i++) {
                if (this.tileMap[i][positionNum].isFrozen()) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < this.width; i++) {
                if (this.tileMap[i][positionNum].isFrozen()) {
                    return false;
                }
            }
        }
        if (!flip) {
            if (row) {
                //insert a tile into the default side of the row
                this.silkBag.addTile(this.tileMap[width - 1][positionNum]);
                for (int i = (width - 1); i > 0; i--) {
                    this.tileMap[i][positionNum] = this.tileMap[i - 1][positionNum];
                }
                this.tileMap[0][positionNum] = tile;
            } else {
                //insert a tile to the default side of the column
                this.silkBag.addTile(this.tileMap[positionNum][height - 1]);
                for (int i = (height - 1); i > 0; i--) {
                    this.tileMap[positionNum][i] = this.tileMap[positionNum][i - 1];
                }
                this.tileMap[positionNum][0] = tile;
            }
            pushedPlayer(row, positionNum, flip);
        } else if (flip) {
            //insert a tile into the opposite side of the row
            if (row) {
                this.silkBag.addTile(this.tileMap[0][positionNum]);
                for (int i = 0; i < (width - 1); i++) {
                    this.tileMap[i][positionNum] = this.tileMap[i + 1][positionNum];
                }
                this.tileMap[width - 1][positionNum] = tile;
            } else {
                //insert a tile to the opposite side of the column
                this.silkBag.addTile(this.tileMap[positionNum][0]);
                for (int i = 0; i < (height - 1); i++) {
                    this.tileMap[positionNum][i] = this.tileMap[positionNum][i + 1];
                }
                this.tileMap[positionNum][height - 1] = tile;
            }
            pushedPlayer(row, positionNum, flip);
        }
        return true;
    }

    /**
     * Checks if a player needs to be moved due to inserted tile.
     *
     * @param row True if the inserted tile was pushed in a row, false for a column.
     * @param positionNum Number of row/column where tile was inserted (0 - width/height).
     * @param flip True if opposite edges are used, false otherwise.
     */
    public void pushedPlayer(boolean row, int positionNum, boolean flip) {
        int[] intArr;

        if (row) {
            if (player1Location[1] == positionNum) {
                intArr = player1Location;
                if (!flip) {
                    intArr[0] = intArr[0] + 1;
                    updatePlayerLocation(1, intArr);
                } else {
                    intArr[0] = intArr[0] - 1;
                    updatePlayerLocation(1, intArr);
                }
            }
            if (player2Location[1] == positionNum) {
                intArr = player2Location;
                if (!flip) {
                    intArr[0] = intArr[0] + 1;
                    updatePlayerLocation(2, intArr);
                } else {
                    intArr[0] = intArr[0] - 1;
                    updatePlayerLocation(2, intArr);
                }
            }
            if (player3Location[1] == positionNum) {
                intArr = player3Location;
                if (!flip) {
                    intArr[0] = intArr[0] + 1;
                    updatePlayerLocation(3, intArr);
                } else {
                    intArr[0] = intArr[0] - 1;
                    updatePlayerLocation(3, intArr);
                }
            }
            if (player4Location[1] == positionNum) {
                intArr = player4Location;
                if (!flip) {
                    intArr[0] = intArr[0] + 1;
                    updatePlayerLocation(4, intArr);
                } else {
                    intArr[0] = intArr[0] - 1;
                    updatePlayerLocation(4, intArr);
                }
            }
        } else {
            if (player1Location[0] == positionNum) {
                intArr = player1Location;
                if (!flip) {
                    intArr[1] = intArr[1] + 1;
                    updatePlayerLocation(1, intArr);
                } else {
                    intArr[1] = intArr[1] - 1;
                    updatePlayerLocation(1, intArr);
                }
            }
            if (player2Location[0] == positionNum) {
                intArr = player2Location;
                if (!flip) {
                    intArr[1] = intArr[1] + 1;
                    updatePlayerLocation(2, intArr);
                } else {
                    intArr[1] = intArr[1] - 1;
                    updatePlayerLocation(2, intArr);
                }
            }
            if (player3Location[0] == positionNum) {
                intArr = player2Location;
                if (!flip) {
                    intArr[1] = intArr[1] + 1;
                    updatePlayerLocation(3, intArr);
                } else {
                    intArr[1] = intArr[1] - 1;
                    updatePlayerLocation(3, intArr);
                }
            }
            if (player4Location[0] == positionNum) {
                intArr = player2Location;
                if (!flip) {
                    intArr[1] = intArr[1] + 1;
                    updatePlayerLocation(4, intArr);
                } else {
                    intArr[1] = intArr[1] - 1;
                    updatePlayerLocation(4, intArr);
                }
            }
        }
    }

    /**
     * Checks if a tile is frozen (frozen tileMap acts as FloorTile[fixed tileMap] until ice melts).
     *
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @return Whether the tile is frozen or not.
     */
    private Boolean isFrozen(int x, int y) {
        return this.tileMap[x][y].isFrozen();
    }

    /**
     * Checks if a group of tileMap are engulfed (fire on surrounding tileMap, cannot be moved onto if on fire).
     *
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @return Whether the tileMap are engulfed or not.
     */
    private Boolean isEngulfed(int x, int y) {
        return this.tileMap[x][y].isEngulfed();
    }

    /**
     * Checks if a player has reached the finish.
     *
     * @param playerNum Player's number.
     * @return Whether player is has reached the goal.
     */
    public Boolean reachedGoal(int playerNum) {
        /*
        int[] playerLocation = getPlayerLocation(playerNum);
        int x = playerLocation[0];
        int y = playerLocation[1];
        int[][] goalLocation = {};

        for (int i = 0; i < Board.this.tileMap.length; i++) {
            if (this.tileMap[i].getType().equals("Goal")) {
                goalLocation = this.getTileLocation();
            }

        }

        for (int i = 0; i < Board.this.tileLocation.length; i++) {
            for (int j = 0; j < Board.this.tileLocation[i].length; j++) {
                if (goalLocation[i][j] == goalLocation[x][y]) {
                    return true;
                }
            }
        }
         */
        return false;
    }

    /**
     * Checks if a player has a valid path to move
     *
     * @param player The player who is trying to move
     */
    public boolean[] checkPathway(int player) { // ** needs to return boolean
        boolean[] pathway = {false, false, false, false};
        int[] playerLocation = getPlayerLocation(player);
        int[] northPath = {0, 0, 0, 0};
        int[] eastPath = {0, 0, 0, 0};
        int[] southPath = {0, 0, 0, 0};
        int[] westPath = {0, 0, 0, 0};
        int[] currentPath = getTileAt(playerLocation[0], playerLocation[1]).getPathways();

        //find pathways above and below the player
        if (playerLocation[1] <= 0) {
            southPath = getTileAt(playerLocation[0], (playerLocation[1] + 1)).getPathways();
        } else if (playerLocation[1] >= (this.height - 1)) {
            northPath = getTileAt(playerLocation[0], (playerLocation[1] - 1)).getPathways();
        } else {
            northPath = getTileAt(playerLocation[0], (playerLocation[1] - 1)).getPathways();
            southPath = getTileAt(playerLocation[0], (playerLocation[1] + 1)).getPathways();
        }
        //find pathways to the sides of the player
        if (playerLocation[0] <= 0) {
            eastPath = getTileAt((playerLocation[0] + 1), playerLocation[1]).getPathways();
        } else if (playerLocation[0] >= (this.width - 1)) {
            westPath = getTileAt((playerLocation[0] - 1), playerLocation[1]).getPathways();
        } else {
            eastPath = getTileAt((playerLocation[0] + 1), playerLocation[1]).getPathways();
            westPath = getTileAt((playerLocation[0] - 1), playerLocation[1]).getPathways();
        }
        /*
        System.out.println("North: " + Arrays.toString(northPath));
        System.out.println("East: " + Arrays.toString(eastPath));
        System.out.println("South: " + Arrays.toString(southPath));
        System.out.println("West: " + Arrays.toString(westPath));
        System.out.println("Current: " + Arrays.toString(currentPath));
         */

        //need to change magic numbers
        if (currentPath[0] == 1 && northPath[2] == 1) {
            pathway[0] = true;
        }
        if (currentPath[1] == 1 && eastPath[3] == 1) {
            pathway[1] = true;
        }
        if (currentPath[2] == 1 && southPath[0] == 1) {
            pathway[2] = true;
        }
        if (currentPath[3] == 1 && westPath[1] == 1) {
            pathway[3] = true;
        }
        //System.out.println("Pathway: " + Arrays.toString(pathway));
        return pathway;
    }

    /**
     * On a player's turn, the player will move to a new tile if theres a valid pathway.
     *
     * @param playerNum Player's number.
     * @param newLocation the new location of where the player will be.
     */
    public void move(int playerNum, int[] newLocation) {
        if (newLocation[0] > 0 && newLocation[1] > 0) {
            if (newLocation[0] <= this.getWidth() && newLocation[1] <= this.getHeight()) {
                updatePlayerLocation(playerNum, newLocation);
            }
        }
    }

    /**
     * Moves the player forwards/backwards depending on boolean.
     *
     * @param move If true player moves forward, otherwise backwards.
     * @param playerNum Player's number.
     * @param finalLocation Where the player wants to move to.
     */
    public void movePlayer(Boolean move, int playerNum, int[] finalLocation) {
        if (move) {
            updatePlayerLocation(playerNum, finalLocation);
        }
    }

    /**
     * places a tile on gameboard(used when constructing board)
     *
     * @param tile Tile to be placed
     * @param location Where the tile should be placed
     */
    private void placeTile(FloorTile tile, int[] location) {
        this.tileMap[location[0]][location[1]] = tile;
    }

    /**
     * Fills the rest of the board with random tiles.
     */
    private void fillBoard(FloorTile[] tiles) {
        int nextTile = 0;
        //goes through all of the x and y coordinates of the board and fills them with a random tile
        //if the tile isnt already set
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.tileMap[x][y] == null) {
                    this.tileMap[x][y] = tiles[nextTile];
                    rotateTile(x, y);
                    nextTile++;
                }
            }
        }
    }

    /**
     * Randomly rotates tiles.
     *
     * @param x The x coordinate of tile to rotate.
     * @param y The y coordinate of tile to rotate.
     */
    private void rotateTile(int x, int y) {
        Random rand = new Random();
        this.tileMap[x][y].setRotation(rand.nextInt(4));
    }

    /**
     * Get the width of the board.
     *
     * @return width The board's width.
     */
    public int getWidth() {
        return Board.width;
    }

    /**
     * Get the height of the board.
     *
     * @return height The board's height.
     */
    public int getHeight() {
        return Board.height;
    }

    /**
     * Get the created silk bag.
     *
     * @return silkBag The silk bag created.
     */
    public SilkBag getSilkBag() {
        return this.silkBag;
    }

    /**
     * Gets the mapping of tiles on board.
     *
     * @return tileMap The tileMap of floor tiles.
     */
    public FloorTile[][] getTileMap() {
        return this.tileMap;
    }

    /**
     * Converts the board to a string for testing purposes.
     */
    public void toStr() {
        System.out.println("---Board---");
        System.out.println("Player 1 location: " + Arrays.toString(Board.player1Location));
        System.out.println("Player 2 location: " + Arrays.toString(Board.player2Location));
        System.out.println("Player 3 location: " + Arrays.toString(Board.player3Location));
        System.out.println("Player 4 location: " + Arrays.toString(Board.player4Location));
        System.out.println("Width: " + Board.width);
        System.out.println("Height: " + Board.height);
        System.out.println();
        System.out.println("Tile Map:");
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                System.out.println("X: " + x + " Y: " + y);
                this.tileMap[x][y].toStr();
            }
        }
        System.out.println("-----------");
        System.out.println();
    }

    /**
     * convert the board to text to put into a save file.
     *
     * @return result A string version of the game.
     */
    public String toText() {
        String result = "";
        result += String.valueOf(this.width) + ","
                + String.valueOf(this.height) + "\n"
                + String.valueOf(this.player1Location[0]) + "," + String.valueOf(this.player1Location[1]) + "\n"
                + String.valueOf(this.player2Location[0]) + "," + String.valueOf(this.player2Location[1]) + "\n"
                + String.valueOf(this.player3Location[0]) + "," + String.valueOf(this.player3Location[1]) + "\n"
                + String.valueOf(this.player4Location[0]) + "," + String.valueOf(this.player4Location[1]) + "\n";
        //gets all the blocked rows as strings
        for (int i = 0; i < this.blockedRow.length; i++) {
            if (i == 0) {
                result += this.blockedRow[i];
            } else {
                result += "," + this.blockedRow[i];
            }
        }
        result += "\n";
        //gets all the blocked columns as strings
        for (int i = 0; i < this.blockedColumn.length; i++) {
            if (i == 0) {
                result += this.blockedColumn[i];
            } else {
                result += "," + this.blockedColumn[i];
            }
        }
        result += "\n";
        //gets the tiles as strings
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result += String.valueOf(x) + "," + String.valueOf(y) + "," + this.tileMap[x][y].toText();
            }
        }
        return result;
    }

    /**
     * saves the board to a file.
     */
    public void saveBoard() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String filename = "SavedBoard" + sdf.format(timestamp) + ".txt";
        FileReader.writeFile(filename, this.toText());
    }
}
