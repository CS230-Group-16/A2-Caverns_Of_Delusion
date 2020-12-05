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

public class mainMenu extends Application{


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

    @FXML
    private void handlePlayAction(ActionEvent event) throws IOException {
        System.out.println("Play button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("gameConfig.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    @FXML
    private void handleLeaderboardAction(ActionEvent event){
        System.out.println("Leaderboard button clicked");
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            new LeaderboardMenu().start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
