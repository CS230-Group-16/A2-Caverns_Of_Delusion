import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    private ArrayList<ActionTile> spellBook = new ArrayList<ActionTile>();

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
     * updates the players Username
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
     * increases how many games won?
     */
    public void incWon() {
        this.gamesWon = gamesWon + 1;
    }

    /**
     * gets how many games won
     * @return gamesWon
     */
    public int getWon() {
        return gamesWon;
    }

    /**
     * increases how many games lost
     */
    public void incLost() {
        this.gamesLost = gamesLost + 1;
    }

    /**
     * gets how many games lost
     * @return gamesLost
     */
    public int getLost() {
        return gamesLost;
    }

    /**
     * gets number of players
     * @return PlayerNum
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * sets number of the player
     * @param i player number of the player
     */
    public void setPlayerNum(int i) {
        this.playerNum = i;
    }
    
    public boolean getBackTracked() {
		return backtrackUsed;
    	
    }
    
    public void setBackTracked() {
    	this.backtrackUsed = true;
    }

    /**
     * Saves the player profile
     */
    public void saveProfile() {
        try {
            FileWriter playerFile = new FileWriter(username + ".txt", false);
            playerFile.write(username + " " + gamesWon + " " + gamesLost + "\n");
            playerFile.close();
            System.out.println("Player successfully saved");
        } catch (IOException e) {
            System.out.println("Player has not saved");
        }
    }

    /**
     * gets number of games played
     * @return gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesWon + gamesLost;
    }

    /**
     * gets path history of player
     * @return pathHistory
     */
    public int[][] getPathHistory() {
        return this.pathHistory;
    }

    /**
     * inserts tile
     * @param tile
     */

     public void insertTile(ActionTile tile) {
        this.spellBook.add(tile);
     }


    /**
     * updates path history
     * @param lastLocation The last location of player
     */
    public void updatePathHistory(int[] lastLocation) {
        this.pathHistory[1] = this.pathHistory[0];
        this.pathHistory[0][0] = lastLocation[0];
        this.pathHistory[0][1] = lastLocation[1];
    }
}