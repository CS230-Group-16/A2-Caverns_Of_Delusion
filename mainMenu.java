import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * class represents main menu
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.0
 */

public class mainMenu extends Application{

	/**
	 * creates the main menu scene
	 * @param stage To create the stage
	 */
    public void start(Stage stage) {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args) {
        launch(args);
    }

    @FXML
    private Label msgOfTheDay;
    
    /**
     * handles the playing
     * @param an event to handle the playing
     * @throws an IOException
     */
    @FXML
    private void handlePlayAction(ActionEvent event) throws IOException {
        System.out.println("Play button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("gameConfig.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }
    
    /**
     * handles the leaderboard actions
     * @param event An event to handle the leaderboard action
     */
    @FXML
    private void handleLeaderboardAction(ActionEvent event) {
        System.out.println("Leaderboard button clicked");
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            new LeaderboardMenu().start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Handles editing palyer's action
     * @param event An event to edit the player's action
     */
    @FXML
    private void handleEditPlayerAction(ActionEvent event) {
        System.out.println("Edit Player button clicked");
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            new ProfileEdit().start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This allows the exit of the game
     * @param event An event to exit the game
     */
    @FXML
    private void handleExitApplication(ActionEvent event) {
        System.out.println("Exit Program");
        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}