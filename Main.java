
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


/**
 * Main class to run aspects of the assignment
 * @author Bartosz Kubica
 */
public class Main extends Application {
    
    public void start(Stage primaryStage) {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("testBoard.fxml"));
            Scene scene = new Scene(root, 800,800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args){
        Game g = new Game("board1.txt",new String[]{"Super_Cool_Name","grapeLord5000"});
        g.toStr();
        
        /*
        Player p1 = new Player("Michelle", 2, 1);
		Player p2 = new Player("Chloe", 5, 3);
		
		p1.saveProfile();
	    p2.saveProfile();
	    p1.updateUsername ("Blah");
	    p1.saveProfile();
*/
    }
}