import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Leaderboard class creates a leaderboard for the players
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 0.4
 */

public class Leaderboard {

    private ArrayList<Player> player = new ArrayList<>();

    /**
     * Constructor used to make leaderboard
     */
    public Leaderboard(String leaderboardFile) {

    }

    /**
     * Inserts score to the player
     * @param player the player's profile
     * @param score the player's score
     */
    public void insertScore(Player player, int score) { 

    }

    /**
     * Sorts the leaderboard from highest to lowest score
     */
    private void sort() {
        Collections.sort(player, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getWon() > p2.getWon() ? -1 : 1;
            }
        });
    }


    /**
     * Updates the file
     */
    public void updateFile() {

    }
}
