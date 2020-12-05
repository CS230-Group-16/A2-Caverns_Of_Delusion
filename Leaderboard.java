import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The Leaderboard class creates a leaderboard for the players
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 0.4
 */

public class Leaderboard {

    ArrayList<Player> board = new ArrayList<>();
    private String filename;

    /**
     * Constructor used to make leaderboard
     * @param players
     * @param filename
     */
    public Leaderboard(ArrayList<Player> players, String filename) {
        this.board = players;
        this.filename = filename;
        sort();
    }
    
    /**
     * Inserts score to the player
     */
    public void insertScore(Player p) { 
    	this.board.add(p);
        sort();
    }

    public ArrayList<Player> getBoard() {
        return this.board;
    }
    /**
     * Sorts the leaderboard from highest to lowest score
     */
    private void sort() {
        Collections.sort(board, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getGamesWon() > p2.getGamesWon() ? -1 : 1;
            }
        });
    }

    /**
     * Updates the file
     */
    public void updateFile() {
        sort();
    	FileReader.deleteFile(filename);
    	FileReader.writeFile(filename, toText());
    }
    
    public String toText() {
        String result = "";
        for (int i = 0; i < this.board.size(); i++) {
            result += this.board.get(i).toText();
        }
        return result;
    }
}