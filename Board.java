
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * class represents the template of the gameboard
 * @author Bartosz Kubica&Marius Antemir
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

    /*
	 * Create a gameboard with every players' location, 
	 * gameboard height&width and all non-moving tileMap, orientation
	 * of tileMap and their locations
	 * @param player1Location first player's location
	 * @param player2Location second player's location
	 * @param player3Location third player's location
	 * @param player4Location fourth player's location
	 * @param width the width of the gameboard
	 * @param height the height of the gameboard
	 * @param tileMap all fixed tileMap to place on to board
	 * @param tileLocation location of tile 
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
        for (int i = 0; i < height; i++) {
            this.blockedRow[i] = false;
        }
        for (int i = 0; i < width; i++) {
            this.blockedColumn[i] = false;
        }
        blockRowColumn(tileLocation);
        this.tileMap = new FloorTile[width][height];
        for (int i = 0; i < fixedTiles.length; i++) {
            placeTile(fixedTiles[i],tileLocation[i]);
            fixedTiles[i].setFixed(true);
        }
        this.silkBag = silkBag;
        fillBoard(randTiles);
    }

    /*
	 * gets the player's location on gameboard
	 * @param player identification of player by number
	 * @return player's location with in [x,y] format
     */
    public int[] getPlayerLocation(int player) {
        int [] location = {-1,-1};
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
    
    public void blockRowColumn(int[][] tileLocations){     
        for (int[] tileLocation : tileLocations) {
            this.blockedColumn[tileLocation[0]] = true;
            this.blockedRow[tileLocation[1]] = true;
        }
    }
    
    public boolean[] getBlockedRow(){
        return this.blockedRow;
    }
    public boolean[] getBlockedColumn(){
        return this.blockedColumn;
    }

    
    /*
     * gives the tile at a certain position
     * @return tile at position
     */
    public FloorTile getTileAt(int x, int y) {
        return this.tileMap[x][y];
    }



    /*
	 * updates/sets a new location for the player on the gameboard
	 * @param player identification of player by number
	 * @param newLocation the new [x,y] location of player
     */
    public void updatePlayerLocation(int player, int[] newLocation) {
        if (player == 1) {
            this.player1Location = newLocation;
        }
        if (player == 2) {
            this.player2Location = newLocation;
        }
        if (player == 3) {
            this.player3Location = newLocation;
        }
        if (player == 4) {
            this.player4Location = newLocation;
        }
    }

    /**
     * Update the tile at specific location
     * @param newLocation new location of tile
     * @param t tile to be placed in that location
     */
    public void updateTileLocation(int[] newLocation, FloorTile t) {
        this.tileMap[newLocation[0]][newLocation[1]] = t;
    }

    /*
	 * inserts tile on gameboard&pushes all tileMap along 
	 * @param tile FloorTile to be inserted
	 * @param row can only be inserted if true
	 * @param positionNum where the tile should be inserted(column)
	 * @param rotation orientation of the tile(0 degrees, 90 degrees, ..)
     */
    public void insertTile(FloorTile tile, Boolean row, int positionNum, boolean flip) {
        if (!flip) {
            if (row) {
                this.silkBag.addTile(this.tileMap[width-1][positionNum]);
                for (int i = (width-1); i > 0; i--) {
                    this.tileMap[i][positionNum] = this.tileMap[i-1][positionNum];
                }
                this.tileMap[0][positionNum] = tile;
            } else {
                this.silkBag.addTile(this.tileMap[positionNum][height-1]);
                for (int i = (height-1); i > 0; i--) {
                    this.tileMap[positionNum][i] = this.tileMap[positionNum][i-1];
                }
                this.tileMap[positionNum][0] = tile;
            }
        } else if (flip) {
            if (row) {
                this.silkBag.addTile(this.tileMap[0][positionNum]);
                for (int i = 0; i < (width-1); i++) {
                    this.tileMap[i][positionNum] = this.tileMap[i+1][positionNum];
                }
                this.tileMap[width-1][positionNum] = tile;
            } else {
                this.silkBag.addTile(this.tileMap[positionNum][0]);
                for (int i = 0; i < (height-1); i++) {
                    this.tileMap[positionNum][i] = this.tileMap[positionNum][i+1];
                }
                this.tileMap[positionNum][height-1] = tile;
            }
        }
        
        //send to silkbag
    }

    /* creates a floor tile(tile with no special actions, only 
	 * 						different shapes)
	 * @param type the type of the tile
	 * @param rotation orientation of the tile(0 degrees, 90 degrees, ..)
	 * @return FloorTile tile
     */
    private FloorTile createTile(String type, int rotation) {
        return null;
    }

    /*
	 * checks if player is out of the game board
	 * @param playerNum player's number
     */
    public void pushedOut(int playerNum) {
        int[] playerPos = getPlayerLocation(playerNum);

        int x = playerPos[0];
        int y = playerPos[1];

        if (x > getWidth() || y > getHeight()) {
            System.out.println("player outside of board");
        }

    }

    /*
	 * checks if a tile is 'frozen'(frozen tileMap
	 * 						act as FloorTile[fixed tileMap]
	 * 						until ice melts)
	 * @param positionNum the position of the tile
	 * @return whether the tile is frozen or not
     */
    private Boolean isFrozen(int x, int y) {
        return this.tileMap[x][y].isFrozen();
    }

    /*
	 * checks if a group of tileMap are 'engulfed'(fire on
	 * 						surrounding tileMap, cannot
	 * 						be moved onto if on fire) 
	 * @return whether the tileMap are engulfed or not  					    
     */
    private Boolean isEngulfed(int x, int y) {
        return this.tileMap[x][y].isEngulfed();
    }

    /*
	 * checks if player has reached finish
	 * @param playerNum player's number
	 * @return whether player is finished or not
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

    /*
	 * checks if tile can be used to make a path
	 * @param tile the tile to be checked
     */
    public boolean[] checkPathway(int player) { // ** needs to return boolean
        boolean[] pathway = {false,false,false,false};
        int [] playerLocation = getPlayerLocation(player);
        int [] northPath = {0,0,0,0};
        int [] eastPath = {0,0,0,0};
        int [] southPath = {0,0,0,0};
        int [] westPath = {0,0,0,0};
        int [] currentPath = getTileAt(playerLocation[0],playerLocation[1]).getPathways();
        
        if (playerLocation[1] <= 0) {
            southPath = getTileAt(playerLocation[0],(playerLocation[1]+1)).getPathways();
        } else if (playerLocation[1] >= (getHeight()-1)) {
            northPath = getTileAt(playerLocation[0],(playerLocation[1]-1)).getPathways();
        } else {
            northPath = getTileAt(playerLocation[0],(playerLocation[1]-1)).getPathways();
            southPath = getTileAt(playerLocation[0],(playerLocation[1]+1)).getPathways();
        }
        
        if (playerLocation[0] <= 0) {
            eastPath = getTileAt((playerLocation[0]+1),playerLocation[1]).getPathways();
        } else if (playerLocation[1] >= (getWidth()-1)) {
            westPath = getTileAt((playerLocation[0]-1),playerLocation[1]).getPathways();
        } else {
            eastPath = getTileAt((playerLocation[0]+1),playerLocation[1]).getPathways();
            westPath = getTileAt((playerLocation[0]-1),playerLocation[1]).getPathways();
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

    /*
	 * when a player's turn, signifies their action/'move'
	 * @param playerNum
     */
    public void move(int playerNum,int[]newLocation) {
        //boolean[] paths = checkPathway(playerNum);
        //show where they can go
        if (newLocation[0] > 0 && newLocation[1] > 0) {
            if (newLocation[0] <= this.getWidth() && newLocation[1] <= this.getHeight()) {
                updatePlayerLocation(playerNum,newLocation);
            }
        }
    }

    /*
	 * moves the player forwards/backwards depending on boolean
	 * @param move if true player moves forward, otherwise backwards
	 * @param playerNum player's number
	 * @param finalLocation player's goal location
     */
    public void movePlayer(Boolean move, int playerNum, int[] finalLocation) {
        if (move) {
            updatePlayerLocation(playerNum, finalLocation);
        } else {
            //do we need an else??
        }

    }

    /*
	 * places tile on gameboard(used when constructing board)
	 * @param tile tile to be placed
	 * @param location where the tile should be placed
     */
    private void placeTile(FloorTile tile, int[] location) {
        this.tileMap[location[0]][location[1]] = tile;
    }
    
    /**
     * Fill rest of board with random tiles
     */
    private void fillBoard(FloorTile [] tiles){
        int nextTile = 0;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.tileMap[x][y] == null) {
                    this.tileMap[x][y] = tiles[nextTile];
                    rotateTile(x,y);
                    nextTile++;
                }
            }
        }
    }
    
    /**
     * randomly rotate tiles
     * @param x x coordinate of tile to rotate
     * @param y y coordinate of tile to rotate
     */
    private void rotateTile(int x, int y){
        Random rand = new Random();
        this.tileMap[x][y].setRotation(rand.nextInt(4));
    }

    /**
     * get width of board
     * @return width integer
     */
    public int getWidth() {
        return Board.width;
    }

    /**
     * get height of board
     * @return height integer
     */
    public int getHeight() {
        return Board.height;
    }
    
    /**
     * get created silk bag
     * @return silk bag
     */
    public SilkBag getSilkBag(){
        return this.silkBag;
    }
    
    /**
     * returns mapping of tiles on board
     * @return tile map of floor tiles
     */
    public FloorTile[][] getTileMap(){
        return this.tileMap;
    }
    
    /**
     * to string method for testing
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
     * convert to text to put into file
     * @return string version of the game
     */
    public String toText(){
        String result = "";
        result += String.valueOf(this.width) + ","
                + String.valueOf(this.height) + "\n"
                + String.valueOf(this.player1Location[0]) + "," + String.valueOf(this.player1Location[1]) + "\n"
                + String.valueOf(this.player2Location[0]) + "," + String.valueOf(this.player2Location[1]) + "\n"
                + String.valueOf(this.player3Location[0]) + "," + String.valueOf(this.player3Location[1]) + "\n"
                + String.valueOf(this.player4Location[0]) + "," + String.valueOf(this.player4Location[1]) + "\n";
        
        for (int i = 0; i < this.blockedRow.length; i++) {
            if (i == 0) {
                result += this.blockedRow[i];
            } else {
                result += "," + this.blockedRow[i];
            }
        }
        result += "\n";
        for (int i = 0; i < this.blockedColumn.length; i++) {
            if (i == 0) {
                result += this.blockedColumn[i];
            } else {
                result += "," + this.blockedColumn[i];
            }
        }
        result += "\n";
        
        for (int x = 0; x < this.tileMap.length; x++) {
            for (int y = 0 ; y < this.tileMap[x].length; y++) {
                result += String.valueOf(x) + "," + String.valueOf(y) + "," + this.tileMap[x][y].toText();
            }
        }

        
        return result;
    }
    
    public void saveBoard(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String filename = "SavedBoard" + sdf.format(timestamp) + ".txt";
        FileReader.writeFile(filename, this.toText());
    }
}
