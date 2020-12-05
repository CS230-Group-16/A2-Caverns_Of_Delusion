import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 * Main class to run aspects of the assignment
 * @author Bartosz Kubica
 */
public class Main extends Application {
    
    private static final String GAME_NAME = "Caverns Of Delusion";
    //private static Game g;
    
    public void start(Stage primaryStage) {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("testBoard.fxml"));
            
            //test
            
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
            /*
            VBox root2 = new VBox();
            Image i = new Image(new FileInputStream("STRAIGHT.png"));
            ImageView imageView = new ImageView(i);
            root2.getChildren().add(imageView);
            Image i2 = new Image(new FileInputStream("STRAIGHT.png"));
            ImageView imageView2 = new ImageView(i2);
            imageView.setRotate(90);
            root2.getChildren().add(imageView2);
            */
            
            primaryStage.setTitle(GAME_NAME);
            Scene scene = new Scene(root, 800,800);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args){
        Game g = new Game("board1.txt",new String[]{"Super_Cool_Name","grapeLord5000","awesomeGuy"});
        //g.toStr();
        //g.displayMessage();
        
        g.getPlayers()[0].insertTile(new EffectTile("FIRE",3));
        
        g.saveGame();
        
        //System.out.println(g.toText());
        //System.out.println(g.getBoard().toText());
        
        Game g2 = new Game("SavedBoard2020.12.04.txt","SavedGame2020.12.04.txt");
        //System.out.println(g2.toText());
        //System.out.println(g2.getBoard().toText());
        //g2.getBoard().toStr();
        //g.getBoard().toStr();
        //g.getBoard().insertTile(new StraightTile(1), false, 1, false);
        //g.getBoard().toStr();
        
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



        launch(args);
    }
}