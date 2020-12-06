import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * The main game class that initialises all objects.
 * @author Bartosz Kubica & Marius Antemir
 * @version 1.0
 */
public class Game {

    private final String DIRECTORY = "D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/files/";
    private static final SimpleDateFormat sdfH = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private String saveGameFileName;
    private String saveBoardFileName;
    
    private boolean gameInProgress;

    private Board board;
    private SilkBag silkBag;
    private RoundTable round;
    private Player[] players;
    private Leaderboard leaderboard;

    /**
     * Constructor 1 used to construct a new game.
     * @param boardFileLocation location of the file to initialise the board
     * @param playerUsername names of the players in the current game
     */
    public Game(String boardFileLocation, String playerUsername[]) {
        createBoard(boardFileLocation);
        createPlayerArray(playerUsername, playerUsername.length);
        createSilkBag(boardFileLocation);
        this.round = new RoundTable(playerUsername.length,0,this.players,this.board,this.silkBag);
        setFileName();
    }

    /**
     * Constructor 2 used to construct a saved game.
     * @param saveBoardFileLocation location of file of saved board
     * @param saveGameFileLocation location of file of saved game info
     */
    public Game(String saveBoardFileLocation, String saveGameFileLocation) {
        this.board = FileReader.readSavedBoardFile(saveBoardFileLocation);
        this.round = FileReader.readSavedGameFileRoundTable(saveGameFileLocation);
        this.silkBag = FileReader.readSavedGameFileSilkBag(saveGameFileLocation);
        this.players = this.round.getPlayers();
        this.round.setBoard(this.board);
        this.round.setSilkBag(this.silkBag);
    }

    /**
     * Gets the current game state
     * @return True if the game is being played and False if not
     */
    private Boolean gameInProgress() {
        return this.gameInProgress;
    }

    /**
     * Creates a board using file
     * @param boardFileLocation
     */
    private void createBoard(String boardFileLocation) {
        this.board = FileReader.readBoardFile(boardFileLocation);
    }
    
    /**
     * Get method to get the board
     * @return current board
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     * Get method to get the round
     * @return current round
     */
    public RoundTable getRound() {
        return this.round;
    }

    /**
     * Displays daily message
     */
    public void displayMessage() {
        String message = getMessage("http://cswebcat.swansea.ac.uk/message?solution="
                + decodeMessage(getMessage("http://cswebcat.swansea.ac.uk/puzzle")));
        
        //printing to screen for now
        //output to label needs implementing
        System.out.println(message);
    }

    /**
     * Decodes the message as per the instructions on the web site
     * @param msg message to decode
     * @return decoded message
     */
    private String decodeMessage(String msg) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String solution = "CS-230";
        int alphabetLength = alphabet.length();
        boolean back = true;
        int move = 0;
        String[] msgArr = msg.split("");
        for (int i = 0; i < msg.length(); i++) {
            if (back) {
                move = alphabet.indexOf(msgArr[i]) - (i + 1);
                if (move < 0) {
                    move = alphabetLength - (move * -1);
                }
                solution += alphabet.charAt(move);
                back = false;
            } else {
                move = alphabet.indexOf(msgArr[i]) + (i + 1);
                if (move >= alphabetLength) {
                    move = (move - alphabetLength);
                }
                solution += alphabet.charAt(move);
                back = true;
            }
        }
        solution = solution + Integer.toString(solution.length());
        return solution;
    }

    /**
     * Gets the message from a specific URL
     * @param stringURL URL to get the message from
     * @return the message from the website
     */
    public String getMessage(String stringURL) {
        String msg = "";
        try {
            URL url = new URL(stringURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            msg = in.readLine();
            in.close();
        } catch (Exception e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * Creates a player object from profile tile
     * @return Player profile
     */
    private Player playerProfile(String username) {
        return FileReader.readPlayerFile(username);
    }

    /**
     * Gets the leaderboard object
     * @return leaderboard object
     */
    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }

    /**
     * Starts the game by calling function in RoundTable
     */
    public void gameStart() {
        round.turnStart();
    }

    /**
     * Starts the game won sequence once a player is on the goal tile
     */
    public void gameWon() {
        //game won sequence
    }

    /**
     * Creates a new silk bag using tiles from file
     * @param filename level file to create tiles and place in to silk bag
     */
    public void createSilkBag(String filename) {
        this.silkBag = this.board.getSilkBag();
    }

    /**
     * Creates a new leader board using file of leader board
     * @param locationFile name of the file that holds the leaderboard
     */
    public void createLeaderboard(String locationFile) {
        //this.leaderboard = new Leaderboard(locationFile);
    }

    /**
     * Creates an array of players to iterate through during the game
     * @param playerNames Usernames of players to place into array
     * @param numOfPlayers total number of players
     */
    public void createPlayerArray(String playerNames[], int numOfPlayers) {
        this.players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            this.players[i] = playerProfile(playerNames[i]);
            this.players[i].setPlayerNum(i+1);
        }
        for (int i = 4; i > numOfPlayers; i--) {
            this.board.updatePlayerLocation(i, new int[]{-1,-1});
        }
    }
    
    /**
     * Get method for players
     * @return players array
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Deletes a player profile
     * @param username name of profile to delete
     */
    public void deletePlayer(String username) {
        FileReader.deleteFile(username);
    }
    
    /**
     * method to print object to console
     */
    public void toStr() {
        System.out.println("Current Game state: " + this.gameInProgress);
        System.out.println();
        this.board.toStr();
        //this.leaderboard.toStr();
        //this.round.toStr();
        this.silkBag.toStr();
        System.out.println();
        System.out.println("Current Players in game: ");
        for (int i = 0; i < this.players.length; i++) {
            this.players[i].toStr();
        }
        System.out.println();
    }
    
    /**
     * convert to text to put into file
     * @return string version of the game
     */
    public String toText() {
        String result = "";
        result += this.silkBag.toText()
                + this.round.toText();
        //leaderboard;
        return result;
    }
    
    public void setFileName () {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.saveGameFileName = "saveGames/SavedGame" + sdfH.format(timestamp) + ".txt";
        this.saveBoardFileName = "saveGames/SavedBoard" + sdfH.format(timestamp) + ".txt";
    }
    /**
     * save game to text file
     */
    public void saveGame() {
        FileReader.writeFile(this.saveGameFileName, this.toText());
        this.board.saveBoard(this.saveBoardFileName);
    }
}