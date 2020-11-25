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
	private Player[] players;
	private int counter = 0;
	private int moveCounter = 0;
	private Board board;
	private SilkBag silkBag;


	/**
	 *
	 * @param numOfPlayers The number of players
	 * @param turnCounter  The number of turns that have been taken
	 * @param currentPlayer The current player
	 * @param nextPlayer The player after the current player
	 */
	public RoundTable(int numOfPlayers, int turnCounter, Player currentPlayer, Player nextPlayer, Player[] players, Board board, SilkBag silkBag) {
		this.numOfPlayers = numOfPlayers;
		this.turnCounter = turnCounter;
		this.players = players;
		this.currentPlayer = currentPlayer;
		this.nextPlayer = nextPlayer;
		this.board = board;
		this.silkBag = silkBag;
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

	public void turnStart() {

		drawTile();
		//left blank for now. player selects the tile they want to play with JavaFX
		playActionTile();
		movement();
		endTurn();
		if (board.reachedGoal() == true) {
			return;
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
	 * calls the move method from Board
	 */
	public void movement () {
		board.move(currentPlayer.getPlayerNum());
	}


	/**
	 * end the turn of current player and move it to next player
	 * counter goes from 1 to the number of players in the board and loops
	 * when it gets to the end
	 *
	 */
	public void endTurn() {
		nextPlayer(players[counter + 1]);
		counter++;

		//resets the counter after it loops through all players
		if (counter > players.length) {
			counter = 0;
		}
		turnStart();

		//return back to game to increment player
	}

	/**
	 * set the next player
	 *
	 * @param player is the next player
	 * @return the next player
	 */
	public Player nextPlayer(Player player) {

		//setNextPlayer to current player
		setCurrentPlayer(player);
		//set next player
		nextPlayer = players[counter + 2];
		return nextPlayer;
	}

	/**
	 * draws a tile from the silk bag
	 *
	 */
	public void drawTile() {
		Tile tile = silkBag.drawTile();
		if (tile.getType().equals("ActionTile")) {
			sendToPlayer(tile);
		} else {
			insertTile(tile);
		}
	}

	/**
	 * Adds a tile to the board
	 *
	 * @param tile The floorTile to be added on the board
	 */
	public void insertTile(Tile tile) {
		board.insertTile(tile);
	}

	/**
	 * Adds the drawn action tile to the players personal spellbook
	 *
	 * @param tile The action tile sent to the player's spellbook
	 */
	public void sendToPlayer(Tile tile) {
		currentPlayer.getSpellBook().add(tile);
	}

	/**
	 * Allows an action tile to be played
	 *
	 */
	public void playActionTile(ActionTile t) {
		String type = checkTileType(t.getType());
		if (type == "backtrack") {
			System.out.println("Who would you like to backtrack?");
			//allow the player to click on another player to backtrack
			//check if the player selected has been backtracked before
			//PlayerSelected needs to wait for JavaFX to be implemented
			if(playerSelected.getBackTracked()) {
				System.out.println("This person has already been backtracked");
				playActionTile(t);
			}
			else {
				backtrack(playerSelected);
				playerSelected.setBacktracked(true);
			}
		}
		else if(type == "doublemove") {
			movement();
		}
		else if(type == "fire"){
			//coords needs to wait for JavaFX to be implemented
			//the user will click on a tile and then it will turn the location of the tile into an array
			//with coords[0] as x and coords[1] as y.
			engulfTiles(getSurroundingTile(coords[]));
		}
		else {
			//coords needs to wait for JavaFX to be implemented
			freezeTiles(getSurroundingTile(coords[]));
		}

	}

	/**
	 * provides an array of the surrounding tiles.
	 *
	 * @param centralTile is the center of the 9x9 square of tiles
	 * @return an array of the surrounding tiles
	 */
	private FloorTile[] getSurroundingTile(int[] centralTile) {
		//takes the chosen tiles array and splits them into an x and a y variable
		int chosenx = centralTile[0];
		int choseny = centralTile[1];
		int x = 0;
		int y = 0;
		FloorTile[] selectedTiles = new FloorTile[8];
		//This loops through all the tiles on the board
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if (i == chosenx && j == choseny) {
					//top left corner
					selectedTiles[0] = (board.getTileAt(x - 1, y - 1));

					//top middle
					selectedTiles[1] = (board.getTileAt(x, y - 1));

					//top right corner
					selectedTiles[2] = (board.getTileAt(x + 1, y - 1));

					//left
					selectedTiles[3] = (board.getTileAt(x - 1, y));

					//centre
					selectedTiles[4] = (board.getTileAt(x, y));

					//right
					selectedTiles[5] = (board.getTileAt(x + 1, y));

					//bottom left corner
					selectedTiles[6] = (board.getTileAt(x - 1, y + 1));

					//bottom
					selectedTiles[7] = (board.getTileAt(x, y + 1));

					//bottom right corner
					selectedTiles[8] = (board.getTileAt(x + 1, y + 1));
				}
			}
		}

		for (int i = 0; i < selectedTiles.length; i++) {
			board.getTileAt(selectedTiles[i]);
		}
		return selectedTiles;
	}

	/**
	 * Engulfs all the tiles passed in as arguments
	 *
	 * @param tile The tiles to be engulfed
	 */
	private void engulfTiles(int[] tile) {
		FloorTile[] tiles = getSurroundingTile(tile);
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].setEngulfed(true);
		}

	}
	//take a central tile and engulf all tiles touching it.
	//needs to check that there are no players on the fire tiles


	/**
	 * Freezes all the tiles passed in as arguments
	 *
	 * @param int[] The tiles to be frozen
	 */
	private void freezeTiles(int[] tile) {
		FloorTile[] tiles = getSurroundingTile(tile);
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].setFrozen(true);
		}
		//freezes all tiles passes in as arguments
		//take a central tile and freeze all tiles touching it.
		//checks that the tiles are on the board
	}

	/**
	 * Moves the player
	 *
	 * @param player is the player that will move.
	 */
	private void backtrackPlayer(Player currentPlayer) {

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