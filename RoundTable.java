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
	private Player currentPlayer;
	private Player nextPlayer;

	/**
	 *
	 * @param numOfPlayers The number of players
	 * @param turnCounter  The number of turns that have been taken
	 * @param currentPlayer The current player
	 * @param nextPlayer The player after the current player
	 */
	public RoundTable(int numOfPlayers, int turnCounter, Player currentPlayer, Player nextPlayer) {
		this.numOfPlayers = numOfPlayers;
		this.turnCounter = turnCounter;
		this.currentPlayer = currentPlayer;
		this.nextPlayer = nextPlayer;
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

	public void turnStart(); {
		drawTile();

		endTurn();
		return;
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
        /*
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
        */

	/**
	 * end the turn of current player and move it to next player
	 * counter goes from 1 to the number of players in the board and loops
	 * when it gets to the end
	 *
	 */
	public boolean endTurn() {
		nextPlayer(PlayerArray(counter + 1));
		roundStart();
		//return back to game to increment player
	}

	/**
	 * set the next player
	 *
	 * @param player is the next player
	 * @return the next player
	 */
	public Player nextPlayer(Player player) {

		//setNextPlayer


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
		SilkBag.drawtile();
		if tile == actionTile {
			sendToPlayer(tile);
		} else {
			insertTile(tile);
		}
	}

	/**
	 * Adds a tile to the board
	 *
	 * @param tile The floorTile to be added on the board
	 * @param row DISCUSS
	 * @param positionNum
	 */
	public void insertTile(FloorTile tile, boolean row, int positionNum) {
		Board.insertTile(tile, row, positionNum);
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
	public void playActionTile(ActionTile t) {
		checkTileType(t);

		ActionTile play = new ActionTile();
		//
	}

	/**
	 * provides an array of the surrounding tiles.
	 *
	 * @param centralTile is the center of the 9x9 square of tiles
	 * @return an array of the surrounding tiles
	 */
	private FloorTile[][] getSurroundingTile(int[] centralTile) {
		//provides an array of the surrounding tiles.
		//index 0 will be array to the north, 1 is east...
		Board addingTiles = new Board();
		centralTile = new int[2];
		int chosenx = centralTile[0];
		int choseny = centralTile[1];
		int x = 0;
		int y = 0;
		FloorTile[][] selectedTiles = new FloorTile[];
		//This loops through all the tiles on the board
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if (i = chosenx && j == choseny) {
					//centre
					selectedTiles[0][0].add(x);
					selectedTiles[0][1].add(y);
					//To the right
					selectedTiles[1][0].add(x+1);
					selectedTiles[1][1].add(y);
					//to the left
					selectedTiles[2][0].add(x-1);
					selectedTiles[2][1].add(y);
					//top
					selectedTiles[3][0].add(x);
					selectedTiles[3][1].add(y-1);
					//bottom
					selectedTiles[4][0].add(x);
					selectedTiles[4][1].add(y+1);
					//top left corner
					selectedTiles[5][0].add(x-1);
					selectedTiles[5][1].add(y-1);
					//top right corner
					selectedTiles[6][0].add(x+1);
					selectedTiles[6][1].add(y-1);
					//bottom left corner
					selectedTiles[7][0].add(x-1);
					selectedTiles[7][1].add(y+1);
					//bottom right corner
					selectedTiles[8][0].add(x+1);
					selectedTiles[8][1].add(y+1);
				}


			}
		}
		for (int i = 0; i < selectedTiles.size(); i++) {
			addingTiles.getTile(selectedTiles[i]); //getTile() to be made in board
		}
		return selectedTiles;
	}

	/**
	 * Engulfs all the tiles passed in as arguments
	 *
	 * @param tile The tiles to be engulfed
	 */
	private void engulfTiles(Tile[] tile) {
		blockingPlayer = False;
		for (int i = 1; i <= 4; i++) {
			if (Board.getPlayerLocation(i) == tile) {
				blockingPlayer = True;
			}
		}
		if (blockingPlayer == False) {
			tile = EffectTile.engulf();
		}
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
			int x = location[0];
			int y = location[1];
			Tile currentTile = Board.getTile(x, y);
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
