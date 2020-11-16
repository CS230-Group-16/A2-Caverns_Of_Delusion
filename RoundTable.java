public class RoundTable {

	private int numOfPlayers;
	private int turnCounter;
	private int currentPlayer;
	
	public RoundTable(int numOfPlayers, int turnCounter, Player currentPlayer) {
		this.numOfPlayers = numOfPlayers;
		this.turnCounter = turnCounter;
		this.currentPlayer = currentPlayer;
	}
	
	public String checkTileType() {
		//Compare name of tile to 
	}
	
	public Player checkPlayer() {
		return currentPlayer;
	}
	
	public void movement() {
		//call board class to move player
	}
	
	public boolean endTurn() {
		//current player = next player
		//counter goes from 1 to the number of players in the board and loops 
		//when it gets to the end
		nextPlayer(PlayerArray(counter));
	}
	
	public Player nextPlayer(Player player) {
		currentPlayer = nextPlayer;
		nextPlayer = playerArray(counter + 1);
		//an array of players. cycle through the index to change players
		
	}
	
	public void drawTile() {
		//An array of tiles
		//use Random to draw out tiles
	}
	
	public void insertTile(FloorTile tile, boolean row, int positionNum) {
		//check ouput type
	}
	public void sendToPlayer(ActionTile tile) {
		//add the drawn tile to the players spellbook
	}
	
	public void playActionTile() {
		
	}
	
	private FloorTile[] getSurroundingTile(int[] centralTile) {
		
	}
	
	private void engulfTiles(Tile[] tile) {
		
	}
	
	private void freezeTiles(Tile[] tile) {
		
	}
	
	private void movePlayer(boolean move) {
		
	}
	
	public int getTurnCounter() {
		return turnCounter;
	}
	
	public void getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setNextPlayer(Player player){
		
	}
}
