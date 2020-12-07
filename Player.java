import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Player class creates profile for user
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 1.0
 */
public class Player {

    private String username;
    private int gamesWon;
    private int gamesLost;
    private int gamesPlayed;
    private int playerNum;
    private int[][] path = {{-1,-1},{-1,-1}};
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
        this.gamesPlayed = getGamesPlayed();
    }
    
    /**
     * contructor for making player from saved game file
     * @param username
     * @param gamesWon
     * @param gamesLost
     * @param playerNum
     * @param path
     * @param backtrack
     * @param spells 
     */
    public Player(String username,int gamesWon, int gamesLost, int playerNum, int[][]path, boolean backtrack, ActionTile[] spells) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.playerNum = playerNum;
        this.path = path;
        this.backtrackUsed = backtrack;
        this.spellBook.addAll(Arrays.asList(spells));
    }

    /**
     * Updates the players Username
     * @param username the name of the player in current game
     */
    public void updateUsername(String username) {
    	FileReader.deleteFile(this.username + ".txt");
        this.username = username;
        saveProfile();
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
    public int getGamesWon() {
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
    public int getGamesLost() {
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
    
    public void deletePlayerFromFile(String username) {
       
    }
    
    public void saveProfile() {
        try {
            //this adds the players username to a separate file to make it easier when creating leaderboard
            FileWriter leaderboard = new FileWriter("player.txt", true);
            leaderboard.write(username + "\n");
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
        return this.path;
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
        this.path[1] = this.path[0];
        this.path[0] = lastLocation;
    }
    
    public ArrayList<ActionTile> getSpellBook() {
        return this.spellBook;
    }
    
    public void useSpell(ActionTile t) {
        this.spellBook.remove(t);
    }
    
    /**
     * Method to string to print player to screen
     */
    public void toStr() {
        String result = "";
        result += "Username: " + username + "\n"
                + "Games Won: " + gamesWon + "\n"
                + "Games Lost: " + gamesLost + "\n"
                + "Games Played: " + (gamesWon + gamesLost) + "\n"
                + "Backtrack: " + backtrackUsed + "\n"
                + "Player Num: " + playerNum + "\n"
                + "Path History: ";
        for (int i = 0; i < this.path.length; i++){
            result += Arrays.toString(path[i]) + ", ";
        }
        result += "\n";
        result += "Spell Book: ";
        for (int i = 0; i < this.spellBook.size(); i++) {
            result += spellBook.get(i).getEffect() + ", ";
        }
        result += "\n";
        System.out.println(result);
    }
    
    /**
     * convert to text to put into file
     * @return string version of the game
     */
    public String toText() {
        String result = "";
        result += this.username + ","
                + String.valueOf(this.gamesWon) + ","
                + String.valueOf(this.gamesLost) + ","
                + String.valueOf(this.playerNum) + ","
                + Arrays.toString(this.path[0]) + ","
                + Arrays.toString(this.path[1]) + ","
                + this.backtrackUsed + ","
                + String.valueOf(this.spellBook.size()) + "\n";;
        
        for (int i = 0; i < this.spellBook.size(); i++) {
            result += this.spellBook.get(i).toText();
        }
        return result;
    }
}