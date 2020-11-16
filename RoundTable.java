/**
 * Each players turn. Draw a tile, insert tile, play an action tile and move along the board
 * @author Jimmy Kells and Surinder Singh
 * @version 1.0
 */

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

		//https://www.tutorialspoint.com/java/lang/class_tostring.htm#:~:text=lang.-,Class.,the%20format%20returned%20by%20getName.
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
	public void movement() {
		movePlayer(currentPlayer);
		//calls the movePlayer
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
		//an array of players. cycle through the index to change players

	}

	/**
	 * draws a tile from the silk bag
	 *
	 */
	public void drawTile() {
		SilkBag.drawTile();
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
		//add the drawn tile to the player's spellbook
	}

	/**
	 * Allows an action tile to be played
	 *
	 */
	public void playActionTile() {

	}

	/**
	 * provides an array of the surrounding tiles.
	 *
	 * @param centralTile is the center of the 9x9 square of tiles
	 * @return an array of the surrounding tiles
	 */
	private FloorTile[] getSurroundingTile(int[] centralTile) {
		//provides an array of the surrounding tiles.
		//index 1 will be array to the north, 2 is east...
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
	 * @param move checks to see if the player is able to move
	 */
	private void movePlayer(boolean move) {
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
