/**
 * Each players turn. Draw a tile, insert tile, play an action tile and move along the board
 * @author Jimmy Kells and Surinder Singh
 * @version 1.0
 */

import javafx.scene.input.KeyEvent; 
import java.util.ArrayList;
import java.util.Random;
public class RoundTable {

	private int numOfPlayers;
	private int turnCounter;
	private int currentPlayer;

	/**
	 *
	 * @param numOfPlayers The number of players
	 * @param turnCounter  The number of turns that have been taken
	 * @param currentPlayer The current player
	 */
	public RoundTable(int numOfPlayers, int turnCounter, Player currentPlayer) {
		this.numOfPlayers = numOfPlayers;
		this.turnCounter = turnCounter;
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Compare name of tile class to tileType
	 *
	 * @param tileType is the type of tile drawn
	 * @return tileString the tile type in a string
	 */
	public String checkTileType(String tileType) {
			if(tileType == "StraightTile") {
				return "This is a straight tile";
			}
			else if(tileType == "CornerTile") {
				return "This is a corner tile";
			}
			else if(tileType == "TShapeTile") {
				return "This is a T Shape tile";
			}
			else {
				return "This is an action tile";
			}
	}

	/**
	 * gets the current player
	 * 	DISCUSS why we need getCurrentPlayer
	 * @return currentPlayer
	 */
	public Player checkPlayer() {
		return currentPlayer;
	}

	/**
	 * calls the movePlayer
	 */
	public void movement(KeyEvent e) {
		movePlayer(currentPlayer);
		//calls the movePlayer
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_UP) {
			System.out.println("Going up");
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			System.out.println("Going down");
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			System.out.println("Going left");
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			System.out.println("Going right");
		}
		else {
			System.out.println("Please press arrow keys");
		}
	}

	/**
	 * end the turn of current player and move it to next player
	 * counter goes from 1 to the number of players in the board and loops
	 * when it gets to the end
	 *
	 */
	public boolean endTurn() {
		nextPlayer(PlayerArray(counter + 1));
	}

	/**
	 * set the next player
	 *
	 * @param player is the next player
	 * @return the next player
	 */
	public Player nextPlayer(Player player) {
		setCurrentPlayer(player);
		nextPlayer = playerArray(counter + 2);
		//an ArrayList of players. cycle through the index to change players
		// .... depends on how the Player class is implemented
		List<Player> players = Arrays.asList(new Player(....), new Player(....), new Player(....), new Player(....));
		Player currentPlayer = players.get(0);
		if (endTurn()){
			//This will assign the currentPlayer to the nextPlayer and also put the previous currentPlayer at the end of the list.
			currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
		}
	}

	/**
	 * draws a tile from the silk bag
	 *
	 */
	public void drawTile() {
		Random tile = new Random():
		Random.SilkBag.drawTile();
		//An array of tiles
		//use Random to draw out tiles
	}

	/**
	 * Adds a tile to the board
	 *
	 * @param tile The floorTile to be added on the board
	 * @param row DISCUSS
	 * @param positionNum
	 */
	public void insertTile(FloorTile tile, boolean row, int positionNum) {
		SilkBag.addTile(tile);
		//check ouput type
	}

	/**
	 * Adds the drawn action tile to the players personal spellbook
	 *
	 * @param tile The action tile sent to the player's spellbook
	 */
	public void sendToPlayer(ActionTile tile) {
		Player player = new Player();
		player.spellBook.add(tile);
	}

	/**
	 * Allows an action tile to be played
	 *
	 */
	public void playActionTile() {
		ActionTile play = new ActionTile();
		//
	}

	/**
	 * provides an array of the surrounding tiles.
	 *
	 * @param centralTile is the center of the 9x9 square of tiles
	 * @return an array of the surrounding tiles
	 */
	private FloorTile[] getSurroundingTile(int centralTile) {
		//provides an array of the surrounding tiles.
		//index 0 will be array to the north, 1 is east...
		int x = 0;
		int y = 0;
		//This loops through all the tiles on the board
		for(int i = x-1; i <= x+1; i++) {
		    for(int j = y-1; j <= y+1; j++) {
		    	if (i = centralTile && j == centralTile) {
		        Floortile[0].add(i);
		        Floortile[1].add(i+1);
		        Floortile[2].add(i+2);
		        Floortile[3].add(i-(i+2));
		    	}
		    }
		}
	}

	/**
	 * Engulfs all the tiles passed in as arguments
	 *
	 * @param tile The tiles to be engulfed
	 */
	private void engulfTiles(Tile[] tile) {
		tile = EffectTile.engulf();
		//take a central tile and engulf all tiles touching it.
		//needs to check that there are no players on the fire tiles
	}

	/**
	 * Freezes all the tiles passed in as arguments
	 *
	 * @param Tile[] The tiles to be frozen
	 */
	private void freezeTiles(Tile[] tile) {
		tile = EffectTile.freeze(); //freezes all tiles passes in as arguments
		//take a central tile and freeze all tiles touching it.
		//checks that the tiles are on the board
	}

	/**
	 * Moves the player
	 *
	 * @param player is the player that will move.
	 */
	private void movePlayer(Player currentPlayer) {
		//move will check if the user has a valid pathway
		boolean move = False;
		int counter = 0;
		while move == False {
			int[] location = Board.getPlayerLocation(currentPlayer);
			Tile[] currentTile = getTile
			//find way to turn coords into a Tile
			move = Board.checkPathway(currentTile);
		}
		if move == True {
			Board.move(currentPlayer);
		} else {
			System.out.println("You cannot move this turn");
		}
		//calls board class to move player
	}

	/**
	 * Increments each time a turn is played
	 *
	 * @return turnCounter the number of turns that have been taken
	 */
	public int getTurnCounter() {
		return turnCounter;
	}

	/**
	 * returns the current player
	 *
	 * @return currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets next player to current player
	 *
	 * @param player is the next player
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = nextPlayer;
	}
}
