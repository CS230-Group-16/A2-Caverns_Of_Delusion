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

    ArrayList<Player> player = new ArrayList<>();

    /**
     * Constructor used to make leaderboard
     */
    public Leaderboard(String locationFile) {

        File playerMain = new File(locationFile);
        try {
            Scanner input = new Scanner(playerMain);
            while (input.hasNext()) {
            	File playerName = new File(input.next() + ".txt");
            	try {
            		Scanner profile = new Scanner(playerName);
            		while (profile.hasNext()) {
            			player.add(new Player(profile.next(), profile.nextInt(), profile.nextInt()));
            		}
    	       	 	profile.close();
				} catch (Exception e) {
					System.out.println("Cannot open " + input.next() + ".txt");
				}
           input.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + locationFile);
        }
    }

    /**
     * Inserts score to the player
     * @param player the player's profile
     * @param score the player's score
     */
    public void insertScore() { 
    	sort();
    	try {
    		FileWriter LeaderboardFile = new FileWriter("LeaderboardFile.txt", true);
			for (int i = 0; i < player.size(); i++) {
				LeaderboardFile.write(player.get(i).getUsername() + " " + player.get(i).getGamesWon() + " " + player.get(i).getGamesPlayed()+ "\n");
				LeaderboardFile.close();
		        System.out.println("Player added to the leaderboard");
			}
		} catch (IOException e) {
			System.out.println("Player could not be added to the leaderboard");
		}
    }

    /**
     * Sorts the leaderboard from highest to lowest score
     */
    private void sort() {
        Collections.sort(player, new Comparator<Player>() {
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
    	FileReader.deleteFile("LeaderboardFile.txt");
    	insertScore();
    }
}
