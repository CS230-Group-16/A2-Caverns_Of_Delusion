import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Each players turn. Draw a tile, insert tile, play an action tile and move along the board.
 * Also keeps track of how many rounds have passed.
 * @author Jimmy Kells and Surinder Singh.
 * @version 1.0
 */

public class RoundTable {

    private int numOfPlayers;
    private int turnCounter;
    private Player currentPlayer;
    private Player nextPlayer;
    private Player[] players;
    private int counter = 1;
    private int moveCounter = 0;
    private Board board;
    private SilkBag silkBag;
    private Tile drawnTile;

    /**
     * Creates a round table that will call multiple other methods
     * @param numOfPlayers The number of players.
     * @param turnCounter The number of turns that have been taken.
     * @param players An array of players.
     * @param board The board chosen.
     * @param silkBag The silkbag chosen.
     */
    public RoundTable(int numOfPlayers, int turnCounter, Player[] players, Board board, SilkBag silkBag) {
        this.numOfPlayers = numOfPlayers;
        this.turnCounter = turnCounter;
        this.players = players;
        this.currentPlayer = players[0];
        this.nextPlayer = players[1];
        this.board = board;
        this.silkBag = silkBag;
    }

    /**
     * A second constructor used for tables with a counter to track the current player instead of
     * using a current player variable.
     * @param numOfPlayers The number of players.
     * @param turnCounter The number of turns that have been taken.
     * @param players An array of players.
     * @param counter Gets the current player by using a counter in the players array.
     */
    public RoundTable(int numOfPlayers, int turnCounter, Player[] players, int counter) {
        this.numOfPlayers = numOfPlayers;
        this.turnCounter = turnCounter;
        this.players = players;
        this.counter = counter;
        this.currentPlayer = players[counter];
        this.nextPlayer = players[counter+1];
    }

    /**
     * Checks to see what type of tile a tile is.
     * @param tileType The type of tile drawn.
     * @return tileString The tile type in a string.
     */
    public String checkTileType(String tileType) {
        if (tileType == "StraightTile") {
            return "This is a straight tile";
        } else if (tileType == "CornerTile") {
            return "This is a corner tile";
        } else if (tileType == "TShapeTile") {
            return "This is a T Shape tile";
        } else {
            return "This is an action tile";
        }
    }

    /**
     * A method to start the turn and call the methods in the correct order.
     */
    public void turnStart() {
        drawTile();
        //playActionTile();
        //movement();
        //endTurn();
        if (board.reachedGoal(1) == true) {
            //to be coded
        }
    }

    /**
     * Gets the current player.
     * @return currentPlayer The current players turn.
     */
    public Player checkPlayer() {
        return currentPlayer;
    }

    /**
     * calls the move method from Board and updates the player path history.
     * @param playerNum The player number of the player which will move.
     * @param newLocation The location the player will end up on.
     */
    public void movement(int playerNum, int[] newLocation) {
        this.currentPlayer.updatePathHistory(newLocation);
        board.move(playerNum, newLocation);
    }

    /**
     * End the turn of current player and move it to next player.
     * Increments the turncounter.
     */
    public void endTurn() {
        //calls a method to set the next player and increments the counter
        nextPlayer();
        this.turnCounter++;
    }

    /**
     * Sets the next player.
     * @return nextPlayer The new next player.
     */
    public Player nextPlayer() {
        //setNextPlayer to current player
        setCurrentPlayer(this.nextPlayer);
        //set next player and increment counter
        //if the counter goes out of bounds of the players array it is reset to the start
        counter++;
        if ((counter) > (players.length - 1)) {
            counter = 0;
            this.nextPlayer = players[counter];
        } else {
            this.nextPlayer = players[counter];
        }
        return nextPlayer;
    }

    /**
     * Gets the counter.
     * @return counter The index of the current player in the players array.
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * Draws a tile from the silk bag and decides what to do with it based on its type.
     */
    public void drawTile() {
        Tile tile = silkBag.drawTile();
        //if its an action tile, it sends it to the players spellbook
        if (tile.getType().equals("BACKTRACK") || tile.getType().equals("DOUBLEMOVE") || tile.getType().equals("FIRE") || tile.getType().equals("ICE")) {
            ActionTile t = (ActionTile) tile;
            t.setTurnDrawn(turnCounter);
            sendToPlayer((ActionTile) t);
        } else {
            //if its a floor tile, the player must play it immediately if possible
            insertTile((FloorTile) tile);
        }
        this.drawnTile = tile;
    }

    /**
     * returns the tile drawn.
     * @return The tile drawn.
     */
    public Tile getDrawnTile() {
        return this.drawnTile;
    }

    /**
     * Adds a tile to the board.
     * @param tile The floorTile to be added on the board.
     */
    public void insertTile(FloorTile tile) {
        /*
        boolean row = true;
        int posNum = 2;
        boolean flip = false;
        int rotation = 0;
        board.insertTile(tile, row, posNum, flip, rotation);
         */
    }

    /**
     * Adds the drawn action tile to the players personal spellbook.
     * @param tile The action tile drawn.
     */
    public void sendToPlayer(ActionTile tile) {
        currentPlayer.insertTile(tile);
    }

    /**
     * Allows a move action tile to be played.
     * @param t The action tile to be played.
     * @param player The player moved by the spell.
     */
    public void playMoveTile(ActionTile t, Player player) {
        //backtracks the selected player or double moves the user depending on what spell they play
        if ("BACKTRACK".equals(t.getType())) {
            backtrackPlayer(player);
            //the player can only be backtracked once
            player.setBackTracked(true);
        } else if ("DOUBLEMOVE".equals(t.getType())) {
            //movement();
        }
        this.currentPlayer.useSpell(t);
    }

    /**
     * Allows a fire or ice spell to be played.
     * @param t The action tile to be played.
     * @param coords The x and y coordinate at the center of the spell.
     */
    public void playEffectTile(ActionTile t, int[] coords) {
        //freezes or engulfs tiles depending on what spell the user plays
        if ("FIRE".equals(t.getType())) {
            //coords needs to wait for JavaFX to be implemented
            //the user will click on a tile and then it will turn the location of the tile into an array
            //with coords[0] as x and coords[1] as y.
            engulfTiles(coords);
        } else if ("ICE".equals(t.getType())) {
            //coords needs to wait for JavaFX to be implemented
            freezeTiles(coords);
        }
        this.currentPlayer.useSpell(t);
    }

    /**
     * Provides an array of the surrounding tiles.
     * @param centralTile The x and y coordinates at the center of the 9x9 square of tiles to be affected.
     * @return selectedTiles an array of the surrounding tiles
     */
    private FloorTile[] getSurroundingTile(int[] centralTile) {
        //takes the chosen tiles array and splits them into an x and a y variable
        int chosenx = centralTile[0];
        int choseny = centralTile[1];
        int x = 0;
        int y = 0;
        FloorTile[] selectedTiles = new FloorTile[9];
        //puts the tiles around the selected tile into an array

        selectedTiles[0] = (board.getTileAt(chosenx - 1, choseny - 1));

        //top middle
        selectedTiles[1] = (board.getTileAt(chosenx, choseny - 1));

        //top right corner
        selectedTiles[2] = (board.getTileAt(chosenx + 1, choseny - 1));

        //left
        selectedTiles[3] = (board.getTileAt(chosenx - 1, choseny));

        //centre
        selectedTiles[4] = (board.getTileAt(chosenx, choseny));

        //right
        selectedTiles[5] = (board.getTileAt(chosenx + 1, choseny));

        //bottom left corner
        selectedTiles[6] = (board.getTileAt(chosenx - 1, choseny + 1));

        //bottom
        selectedTiles[7] = (board.getTileAt(chosenx, choseny + 1));

        //bottom right corner
        selectedTiles[8] = (board.getTileAt(chosenx + 1, choseny + 1));

        return selectedTiles;
    }

    /**
     * Finds the 9x9 square surrounding the tile passed in and engulfs them all.
     * @param tile The center tile to be engulfed.
     */
    private void engulfTiles(int[] tile) {
        //gets the tiles surrounding the center tile and sets them all to engulfed
        FloorTile[] tiles = getSurroundingTile(tile);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                tiles[i].setEngulfed(true);
            }
        }

    }

    /**
     * Finds the 9x9 square surrounding the tile passed in and freezes them all.
     * @param tile the center tile to be frozen
     */
    private void freezeTiles(int[] tile) {
        //gets the tiles surrounding the center tile and sets them all to frozen
        FloorTile[] tiles = getSurroundingTile(tile);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                tiles[i].setFrozen(true);
            }
        }
    }

    /**
     * Backtracks the player to where they were 2 turns ago.
     * If that tile is engulfed, they are moved to 1 tile ago.
     * @param player The player that will be moved.
     */
    private void backtrackPlayer(Player player) {
        int[][] pathHistory;
        pathHistory = player.getPathHistory();
        //if the space they were on 2 turns ago was engulfed, go to the space 1 turn ago
        //if that space is also engulfed, do not move
        for (int i = (pathHistory.length - 1); i >= 0; i--) {
            if (pathHistory[i][0] != -1 || pathHistory[i][1] != -1) {
                if (!board.getTileAt(pathHistory[i][0], pathHistory[i][1]).isEngulfed()) {
                    board.updatePlayerLocation(player.getPlayerNum(), pathHistory[i]);
                }
            }
        }
    }

    /**
     * Gets the turn Counter.
     * @return turnCounter the number of turns that have elapsed.
     */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * Returns the current player.
     * @return currentPlayer The player whose turn it currently is.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the next player.
     * @return nextPlayer The player whose turn it is next.
     */
    public Player getNextPlayer() {
        return this.nextPlayer;
    }

    /**
     * Sets current player to the next player.
     * @param player The next player.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
    
    /**
     * converts the game to text to put into a file.
     * @return result String version of the game.
     */
    public String toText() {
        //game variables are turned to strings
        String result = "";
        result += String.valueOf(this.numOfPlayers) + ","
                + String.valueOf(this.turnCounter) + ","
                + String.valueOf(this.counter) + "\n";
        //saves the tile just drawn but not played yet
        if (this.drawnTile == null) {
            result += "null\n";
        } else {
            result += this.drawnTile.toText();
        }
        //turns the players into strings
        for (int i = 0; i < this.players.length; i++) {
            result += this.players[i].toText();
        }
        return result;
    }
}