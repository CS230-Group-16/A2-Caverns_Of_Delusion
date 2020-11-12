
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The main game class that initialises all objects.
 *
 * @author Bartosz Kubica & Marius Antemir
 * @version 0.4
 */
public class Game {

    private boolean gameInProgress;

    private Board board;
    private SilkBag silkBag;
    private RoundTable round;
    private Player[] players;
    private Leaderboard leaderboard;

    /**
     * Constructor 1 used to construct a new game.
     *
     * @param boardFileLocation location of the file to initialise the board
     * @param playerUsername names of the players in the current game
     */
    public Game(String boardFileLocation, String playerUsername[]) {

    }

    /**
     * Constructor 2 used to construct a saved game.
     *
     * @param saveboardFileLocation location of file of saved board
     * @param saveGameFileLocation location of file of saved game info
     */
    public Game(String saveboardFileLocation, String saveGameFileLocation) {

    }

    /**
     * Gets the current game state
     *
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
     *
     * @param boardFileLocation
     */
    private void createBoard(String boardFileLocation) {
        this.board = FileReader.readBoardFile(boardFileLocation);
    }

    /**
     * Displays daily message
     */
    public void displayMessage() {
        String message = getMessage("http://cswebcat.swansea.ac.uk/message?solution="
                + decodeMessage(getMessage("http://cswebcat.swansea.ac.uk/puzzle")));

        System.out.println(message);
    }

    /**
     * Decodes the message as per the instructions on the website
     *
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
     *
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
     *
     * @return Player profile
     */
    private Player playerProfile(String username) {
        return null;
    }

    /**
     * Gets the leaderboard object
     *
     * @return leaderboard object
     */
    public Leaderboard getLeaderboard() {
        return null;
    }

    /**
     * Starts the game by calling function in RoundTable
     */
    public void gameStart() {

    }

    /**
     * Starts the game won sequence once a player is on the goal tile
     */
    public void gameVon() {

    }

    /**
     * Creates a new silk bag using tiles from file
     *
     * @param tiles Array of tiles to place into the silk bag
     */
    public void createSilkBag(Tiles tiles[]) {
        this.silkBag = new SilkBag(tiles);
    }

    /**
     * Creates a new leader board using file of leader board
     *
     * @param locationFile name of the file that holds the leaderboard
     */
    public void createLeaderboard(String locationFile) {
        this.leaderboard = new Leaderboard(locationFile);
    }

    /**
     * Creates an array of players to iterate through during the game
     *
     * @param playerNames Usernames of players to place into array
     * @param numOfPlayers total number of players
     */
    public void createPlayerArray(String playerNames[], int numOfPlayers) {

    }

    /**
     * Deletes a player profile
     *
     * @param username name of profile to delete
     */
    public void deletePlayer(String username) {

    }
}