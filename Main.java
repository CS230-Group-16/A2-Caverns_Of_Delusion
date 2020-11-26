
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * Main class to run aspects of the assignment
 * @author Bartosz Kubica
 */
public class Main extends Application {
    
    //private static Game g;
    
    public void start(Stage primaryStage) {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("testBoard.fxml"));
            
            
            
            //For moving between panes/scenes:
            /*
            Pane root2 = new Pane();
            Label l = new Label("Hello");
            root2.getChildren().add(l);
            Button b = new Button("button");
            root2.getChildren().add(b);
            
            b.setOnAction((ActionEventevent) -> {
                Scene scene2 = new Scene(root2, 800,800);
                primaryStage.setScene(scene2);
                primaryStage.show();
            });
            */
            Scene scene = new Scene(root, 800,800);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args){
        //this.g = new Game("board1.txt",new String[]{"Super_Cool_Name","grapeLord5000"});
        //g.toStr();
        //g.displayMessage();
        
        launch(args);
        /*
        Player p1 = new Player("Michelle", 2, 1);
		Player p2 = new Player("Chloe", 5, 3);
		
		p1.saveProfile();
	    p2.saveProfile();
	    p1.updateUsername ("Blah");
	    p1.saveProfile();
*/
        //List<Player> players = new ArrayList<>();

        //players.add(new Player("L", 98, 78));
        //players.add(new Player("M", 88, 56));
        //players.add(new Player("N", 90, 67));

        //System.out.println("before " + players);




    }
}