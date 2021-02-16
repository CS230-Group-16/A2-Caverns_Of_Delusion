import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Main class to run aspects of the assignment
 * @author Bartosz Kubica.
 */
public class Main extends Application {
    
    private static final String GAME_NAME = "Caverns Of Delusion";
    //private static Game g;
    
    /**
     * Creates a scene for the game to played on
     * @param primaryStage The stage
     */
    public void start(Stage primaryStage) {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            
            
            primaryStage.setTitle(GAME_NAME);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args) {
      
        launch(args);
    }
}
