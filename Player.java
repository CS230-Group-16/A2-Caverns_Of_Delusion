import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Player class creates profile for user
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 0.6
 */

public class Player {

    private String username;
    private int gamesWon;
    private int gamesLost;
    private int playerNum;
    private int[][] pathHistory = {{0,0},{0,0}};
    private boolean backtrackUsed;
    private ArrayList<ActionTile> spellBook = new ArrayList<>();

    /**
     * Constructor used to make player profile
     *
     * @param username  the names of the player in current game
     * @param gamesWon  the number of games won by player
     * @param gamesLost the number of games lost by player
     */
    public Player(String username, int gamesWon, int gamesLost) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }

    /**
     * Updates the players Username
     * @param username the name of the player in current game
     */
    public void updateUsername(String username) {
    	FileReader.deleteFile(this.username + ".txt");
        this.username = username;
    }

    /**
     * Gets the players Username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Increases how many games player wins
     */
    public void incWon() {
        this.gamesWon = gamesWon + 1;
    }

    /**
     * Gets how many games won
     * @return gamesWon
     */
    public int getWon() {
        return gamesWon;
    }

    /**
     * Increases how many games player looses
     */
    public void incLost() {
        this.gamesLost = gamesLost + 1;
    }

    /**
     * Gets how many games lost
     * @return gamesLost
     */
    public int getLost() {
        return gamesLost;
    }

    /**
     * Gets player number
     * @return PlayerNum
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * Sets player number
     * @param i player number of the player
     */
    public void setPlayerNum(int i) {
        this.playerNum = i;
    }

    /**
     * Gets backtrackUsed
     * @return backtrackUsed
     */
    public boolean getBackTracked() {
		return backtrackUsed;
    }

    /**
     * Sets backtrack
     * @param backtrackUsed backtracks the player
     */
    public void setBackTracked(boolean backtrackUsed) {
    	this.backtrackUsed = true;
    }

    /**
     * Saves the player profile
     */
    public void saveProfile() {
        try {
        	//this adds the players username to a separate file to make it easier when creating leaderboard
            FileWriter leaderboard = new FileWriter ("player.txt", true);
            leaderboard.write(username);
            leaderboard.close();
            FileWriter playerFile = new FileWriter(username + ".txt", false);
            playerFile.write(username + " " + gamesWon + " " + gamesLost + "\n");
            playerFile.close();
            System.out.println("Player successfully saved");
        } catch (IOException e) {
            System.out.println("Player has not saved");
        }
    }

    /**
     * Gets number of games played
     * @return gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesWon + gamesLost;
    }

    /**
     * Gets path history of player
     * @return pathHistory
     */
    public int[][] getPathHistory() {
        return this.pathHistory;
    }

    /**
     * Inserts tile
     * @param tile the tiles played
     */
     public void insertTile(ActionTile tile) {
        this.spellBook.add(tile);
     }

    /**
     * Updates path history
     * @param lastLocation The last location of player
     */
    public void updatePathHistory(int[] lastLocation) {
        this.pathHistory[1] = this.pathHistory[0];
        this.pathHistory[0][0] = lastLocation[0];
        this.pathHistory[0][1] = lastLocation[1];
    }
    
    /**
     * Method to string to print player to screen
     */
    
    public String toString() {
		String result = "";
		result += "Username: " + username + "\n";
		result += "Games Won: " + gamesWon + "\n";
		result += "Games Lost: " + gamesLost + "\n";
		result += "Games Played: " + (gamesWon + gamesLost) + "\n";
 		result += "Backtrack: " + backtrackUsed + "\n";
 		result += "Player Num: " + playerNum + "\n";
 		result += "Path History: " + Arrays.toString(this.pathHistory);
 		result += "Spell Book: ";
 		for (int i = 0; i < this.spellBook.size(); i++) {
            result += spellBook.get(i) + ", ";
        }
		result += "\n";
		return result;
}
}