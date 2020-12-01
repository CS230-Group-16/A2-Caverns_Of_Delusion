import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gameConfig extends Application {

    @FXML
    private MenuButton playerOne = new MenuButton();

    @FXML
    private MenuButton playerTwo = new MenuButton();

    @FXML
    private MenuButton playerThree = new MenuButton();

    @FXML
    private MenuButton playerFour = new MenuButton();


    public void start(Stage stage) {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("gameConfig.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        System.out.println("Back button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    @FXML
    private void handlePlayGameAction(ActionEvent event){
        System.out.println("Play! button clicked");
    }



}