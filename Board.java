

/*
 * class represents the template of the gameboard
 * @author Bartosz Kubica&Marius Antemir
 * @version 1.0
 */
public class Board {
    private static int[] player1Location;
    private static int[] player2Location;
    private static int[] player3Location;
    private static int[] player4Location;
    private static int width;
    private static int height;

    /*
	 * Create a gameboard with every players' location, 
	 * gameboard height&width and all non-moving tiles, orientation
	 * of tiles and their locations
	 * @param player1Location first player's location
	 * @param player2Location second player's location
	 * @param player3Location third player's location
	 * @param player4Location fourth player's location
	 * @param width the width of the gameboard
	 * @param height the height of the gameboard
	 * @param tiles all fixed tiles to place on to board
	 * @param tileLocation location of tile 
     */
    Board(int[] player1Location, int[] player2Location, int[] player3Location, int[] player4Location,
            int width, int height, FloorTile[] tiles, int[][] tileLocation) {
        this.player1Location = player1Location;
        this.player2Location = player2Location;
        this.player3Location = player3Location;
        this.player4Location = player4Location;
        this.width = width;
        this.height = height;
    }

    /*
	 * gets the player's location on gameboard
	 * @param player identification of player by number
	 * @return player's location with in [x,y] format
     */
    int[] getPlayerLocation(int player) {
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
	 * updates/sets a new location for the player on the gameboard
	 * @param player identification of player by number
	 * @param newLocation the new [x,y] location of player
     */
    void updatePlayerLocation(int player, int[] newLocation) {
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

    /*
	 * inserts tile on gameboard&pushes all tiles along 
	 * @param tile FloorTile to be inserted
	 * @param row can only be inserted if true
	 * @param positionNum where the tile should be inserted(column)
	 * @param rotation orientation of the tile(0 degrees, 90 degrees, ..)
     */
    public void insertTile(FloorTile tile, Boolean row, int positionNum, int rotation) {
        //from Jimmy and Surinder

// int x;
		// int y;
		// if row == True {
		// 	y = positionNum;
		// 	x = 0;
		// } else {
		// 	x = positionNum;
		// 	y = 0;
		// }
		// insertPosition[0] = x;
		// insertPosition[1] = y;
		//
		// Board.placeTile(tile, insertPosition);
		// int xSize = Board.getSizex();
		// int ySize = Board.getSizey();
		// if (row == True && positionNum == ySize) {
		// 	SilkBag.addTile(Board.getTile(0, positionNum));
		// }
		// SilkBag.addTile(tile);
                
                
                
        ;
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
        
        
        
    }

    /*
	 * checks if a tile is 'frozen'(frozen tiles
	 * 						act as FloorTile[fixed tiles]
	 * 						until ice melts)
	 * @param positionNum the position of the tile
	 * @return whether the tile is frozen or not
     */
    private Boolean isFrozen(int positionNum) {
        return false;
    }

    /*
	 * checks if a tile is 'engulfed'(fire on
	 * 						surrounding tiles, cannot
	 * 						be moved onto if on fire) 
	 * @return whether the tiles are engulfed or not  					    
     */
    private Boolean isEngulfed() {
        return false;
    }

    /*
	 * checks if player has reached finish
	 * @return whether player is finished or not
     */
    public Boolean reachedGoal() {
        return false;
    }

    /*
	 * checks if tile can be used to make a path
	 * @param tile the tile to be checked
     */
    public Boolean checkPathway(Tile tile) { // ** needs to return boolean
    	int rotation = 0;
    	int[] pathways;
    	StraightTile t = new StraightTile(rotation);
    	
    	t.generatePathways(rotation);
    	pathways = t.getPathways();
    	
    	int i = 0;
    	do {
    		if(pathways[i] == 1) {
    			System.out.println("found path");
    			return true;
    		}
    		i += 1;
    	}while(i < pathways.length);
    	
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
    		for(int i = 0; i < finalLocation[i]; i++) {
    			if(move == true) {
    				playerPos[0] = playerPos[0] + 1;
    			}else {
    				playerPos[0] = playerPos[0] - 1;
    			}
    		}
    	}catch(Exception e) {
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
    public void placeTile(FloorTile tile, int[] location) {
        ;
    }
    
    public int getWidth(){
        return Board.width;
    }
    
    public int getHeight(){
        return Board.height;
    }
}
