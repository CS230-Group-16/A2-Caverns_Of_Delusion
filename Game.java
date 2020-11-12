/**
 * The main game class that initialises all objects.
 * @author Bartosz Kubica & Marius Antemir
 * @version 0.1
 */
public class Game {
    
    private Boolean gameInProgress;
    
    private Board board;
    private SilkBag silkBag;
    private RoundTable round;
    private Player [] players;
    private Leaderboard leaderboard;
    
    /**
     * Constructor 1 used to construct a new game.
     * @param boardFileLocation location of the file to initialise the board
     * @param playerUsername names of the players in the current game
     */
    public Game (String boardFileLocation, String playerUsername[]) {
        
    }
    
    /**
     * Constructor 2 used to construct a saved game.
     * @param saveboardFileLocation location of file of saved board
     * @param saveGameFileLocation location of file of saved game info
     */
    public Game (String saveboardFileLocation, String saveGameFileLocation) {
        
    }
    
    /**
     * Gets the current game state
     * @return True if the game is being played and False if not
     */
    public Boolean gameInProgress() {
        return this.gameInProgress;
    }
    
    /**
     * Saves the current game to a file
     */
    public void saveGame() {
        
    }
    
    /**
     * Creates a board using file
     * @param boardFileLocation 
     */
    private void createBoard(String boardFileLocation) {
        
    }
    
    /**
     * Displays daily message
     */
    public void displayMessage() {
        
    }
    
    /**
     * Creates a player object from profile tile
     * @return Player profile
     */
    private Player playerProfile(String username) {
        return null;
    }
    
    /**
     * Gets the leaderboard object
     * @return leaderboard object
     */
    public Leaderboard getLeaderboard(){
        return null;
    }
    
    /**
     * Starts the game by calling function in RoundTable
     */
    public void gameStart(){
        
    }
    
    /**
     * Starts the game won sequence once a player is on the goal tile
     */
    public void gameVon(){
        
    }
    
    /**
     * Creates a new silk bag using tiles from file
     * @param tiles Array of tiles to place into the silk bag
     */
    public void createSilkBag(Tiles tiles[]){
        
    }
    
    /**
     * Creates a new leader board using file of leader board
     * @return 
     */
    public Leaderboard createLeaderboard(String locationFile){
        return null;
    }
    
    /**
     * Creates an array of players to iterate through during the game
     * @param playerNames Usernames of players to place into array
     * @param numOfPlayers total number of players
     */
    public void createPlayerArray(String playerNames[], int numOfPlayers) {
        
    }
    
    /**
     * Deletes a player profile
     * @param username name of profile to delete
     */
    public void deletePlayer(String username){
        
    }
}