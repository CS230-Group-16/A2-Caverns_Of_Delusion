import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTable; 
  
/**
 * implements a frame which is the leaderboard.  
 * @author Chloe and Michelle
 * @version 0.1
 */
public class LeaderboardMenu extends Application {
    
   private final ArrayList<Player> player = new ArrayList<Player>();
   
   /**
    * this reads the leaderbaord file
    */
   private void readLeaderboard() {
	   File leaderboardFile = new File("LeaderboardFile.txt");
        try {
            Scanner profile = new Scanner(leaderboardFile);
            while (profile.hasNext()) {
            	player.add(new Player(profile.next(), profile.nextInt(), profile.nextInt()));
            }
        } catch (FileNotFoundException e) {
        	System.out.println("Cannot open " + "LeaderboardFile.txt");
        }
   } 
    
   JFrame frame; // frame
   JTable table; // Table 
   	
   /**
    * This is where the leaderboard frame is created and its dimensions are set
    */
    @Override
    public void start(Stage stage)
    { 
        frame = new JFrame(); // Frame initiallization 
        frame.setTitle("Leaderboard");  // Frame Title 
        
        Leaderboard l = new Leaderboard();
        readLeaderboard();
  
        // Data to be displayed in the JTable
        Object[][] data = { 
            //{player.get(0).getUsername(), player.get(0).getGamesWon(), player.get(0).getGamesPlayed()}
        }; 
  
        String[] columnNames = { "Username", "Games Won", "Games Played" }; // Column Names 
  
        // Initializing the JTable 
        table = new JTable(data, columnNames); 
        table.setBounds(30, 40, 200, 300); 
  
        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(table); 
        frame.add(sp);  
        frame.setSize(500, 200); // Frame Size
        frame.setVisible(true); // Frame Visible = true 
    } 
  
   // Driver  method 
   public static void main(String[] args) {
        launch(args);
    }
} 