/**
 * The Player class creates profile for user
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 0.1
 */

public class Player {

    private String username;
    private int gamesWon;
    private int gamesLost;
    private int playerNum;
    private int[] pathHistory;
    private boolean backtrackUsed;

    /**
     * Constructor used to make player profile
     *
     * @param username  the names of the player in current game
     * @param gamesWon  the number of games won by player
     * @param gamesLost the number of games lost by player
     * @param playerNum the player number
     */
    public Player(String username, int gamesWon, int gamesLost, int playerNum) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.playerNum = playerNum;
    }

    /**
     * updates the players Username
     * @param username the name of the player in current game
     */
    public void updateUsername(String username) {
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
     * Saves the player profile
     */
    public void saveProfile() {

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
    public int[] getPathHistory() {
        return pathHistory;
    }

    /**
     * inserts tile
     * @param tile
     */
    public void insertTile(ActionTile tile) {
    }

    /**
     * updates path history
     * @param lastLocation The last location of player
     */
     public void updatePathHistory(int[] lastLocation) {
     //this.pathHistory = new pathHistory (lastLocation);
     }
}
