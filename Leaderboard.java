import java.io.File;
import java.io.FileNotFoundException;
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
    ArrayList<String> t = new ArrayList<>();

    /**
     * Constructor used to make leaderboard
     */
    public Leaderboard(String locationFile) {

        File inputFile = new File(locationFile);
        try {
            Scanner inp = new Scanner(inputFile);
            while (inp.hasNext()) {
            	
            	t.add(inp.next());
            	
            	}
            for (int i= 0; i < t.size(); i++) {
            	System.out.println(t.get(i) + ", ");
            	
                //player.add(new Player(inp.next(), inp.nextInt(), inp.nextInt()));
                //testing
                /*for (int i= 0; i < player.size(); i++) {
                System.out.println(player.get(i).getUsername() + ", " + player.get(i).getWon() + ", " + player.get(i).getLost());
                } */
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

    public static void main (String[] args){
        Player p1 = new Player ("Sam", 6, 3);
        Player p2 = new Player ("Tom", 8,1);
        Player p3 = new Player ("May", 8,1);
        Player p4 = new Player ("Laura", 8,1);
        p1.saveProfile();
        p2.saveProfile();
        p3.saveProfile();
        p4.saveProfile();
        
        Leaderboard l = new Leaderboard("player.txt");
        
        //Leaderboard l1 = new Leaderboard ("Sam.txt");
        //Leaderboard l2 = new Leaderboard ("Tom.txt");

    }

}
