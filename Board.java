
import java.util.Arrays;
import java.util.Random;

/*
 * class represents the template of the gameboard
 * @author Bartosz Kubica&Marius Antemir
 * @version 1.2
 */
public class Board {

    private static int[] player1Location;
    private static int[] player2Location;
    private static int[] player3Location;
    private static int[] player4Location;
    private static int width;
    private static int height;
    private FloorTile[][] tileMap;
    private int[][] tileLocation;
    private SilkBag silkBag;

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
        this.tileMap = new FloorTile[width][height];
        this.tileLocation = tileLocation;
        for (int i = 0; i < fixedTiles.length; i++) {
            placeTile(fixedTiles[i],tileLocation[i]);
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
        if (player == 1) {
            return player1Location;
        }
        if (player == 2) {
            return player2Location;
        }
        if (player == 3) {
            return player3Location;
        }
        if (player == 4) {
            return player4Location;
        }

        return null;
    }

    /*
     * gives position of tile on board
     * @return 2d int array of tile location
     */
    public int[][] getTileLocation() {
        return this.tileLocation;
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
            player1Location = newLocation;
        }
        if (player == 2) {
            player2Location = newLocation;
        }
        if (player == 3) {
            player3Location = newLocation;
        }
        if (player == 4) {
            player4Location = newLocation;
        }
    }

    public void updateTileLocation(int[][] newLocation, Tile t) {
        if (t != null) {
            this.tileLocation = newLocation;
        }
    }

    /*
	 * inserts tile on gameboard&pushes all tileMap along 
	 * @param tile FloorTile to be inserted
	 * @param row can only be inserted if true
	 * @param positionNum where the tile should be inserted(column)
	 * @param rotation orientation of the tile(0 degrees, 90 degrees, ..)
     */
    public void insertTile(FloorTile tile, Boolean row, int positionNum, boolean flip, int rotation) {
        tile.setRotation(rotation);
        
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
    private Boolean isFrozen(int positionNum) {
        for (FloorTile t : Board.this.tileMap) {
            if (t.isFrozen()) {
                return true;
            }
        }
        return false;
    }

    /*
	 * checks if a group of tileMap are 'engulfed'(fire on
	 * 						surrounding tileMap, cannot
	 * 						be moved onto if on fire) 
	 * @return whether the tileMap are engulfed or not  					    
     */
    private Boolean isEngulfed() {
        //define 3x3 area
        int xLeft;
        int yTop;
        int[][] tilePos = new int[this.height][this.width];

        for (Tile t : tileMap) {
            tilePos = Board.this.getTileLocation();
            tilePos = Arrays.copyOf(tilePos, tilePos.length + 1);
            tilePos[tilePos.length - 1] = Board.this.getTileLocation()[tileMap.length];
        }

        for (int i = 0; i < Board.this.tileLocation.length; i++) {
            for (int j = 0; j < Board.this.tileLocation[i].length; j++) {
                xLeft = (i - (i % 3));
                yTop = (j - (j % 3));
            }
        }

    }

    /*
	 * checks if player has reached finish
	 * @param playerNum player's number
	 * @return whether player is finished or not
     */
    public Boolean reachedGoal(int playerNum) {
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
        return false;
    }

    /*
	 * checks if tile can be used to make a path
	 * @param tile the tile to be checked
     */
    public Boolean checkPathway(Tile tile) { // ** needs to return boolean
        //int rotation = 0;
        int[] pathways;
        ((StraightTile) tile).generatePathways(((StraightTile) tile).getRotation());
        pathways = ((StraightTile) tile).getPathways();

        int i = 0;
        do {
            if (pathways[i] == 1) {
                System.out.println("found path");
                return true;
            }
            i += 1;
        } while (i < pathways.length);

        return false;
    }

    /*
	 * when a player's turn, signifies their action/'move'
	 * @param playerNum
     */
    public void move(int playerNum) {
        ;
    }

    /*
	 * moves the player forwards/backwards depending on boolean
	 * @param move if true player moves forward, otherwise backwards
	 * @param playerNum player's number
	 * @param finalLocation player's goal location
     */
    public void movePlayer(Boolean move, int playerNum, int[] finalLocation) {
        int[] playerPos = getPlayerLocation(playerNum);

        try {
            for (int i = 0; i < finalLocation[i]; i++) {
                if (move == true) {
                    playerPos[0] = playerPos[0] + 1;
                } else {
                    playerPos[0] = playerPos[0] - 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
	 * saves board constructor to a .txt file
     */
    public void saveBoardToFile() {
        ;
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
    
    private void rotateTile(int x, int y){
        Random rand = new Random();
        if ("STRAIGHT".equals(this.tileMap[x][y].getType())) {
            this.tileMap[x][y].setRotation(rand.nextInt(2));
        } else {
            this.tileMap[x][y].setRotation(rand.nextInt(4));
        }
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
}
