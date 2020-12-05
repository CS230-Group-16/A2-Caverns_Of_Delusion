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


/**
 * class represents the game configuration
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.0
 */

public class gameConfig{

    //change to personal repo
    private final String DIRECTORY = "F:\\Stuff\\230CW-TilesClasses\\src";

    private List<String> names = textFiles(DIRECTORY);

    @FXML
    private MenuButton playerOne = new MenuButton();

    @FXML
    private MenuButton playerTwo = new MenuButton();

    @FXML
    private MenuButton playerThree = new MenuButton();

    @FXML
    private MenuButton playerFour = new MenuButton();


    /**
     * This initializes the menuItems into the player's personal MenuButton.
     */
    public void initialize() {

        for (int i = 0; i < names.size(); i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a -> {
                System.out.println(menuItem.getText());
            });
            playerOne.getItems().add(menuItem);
        }

        for (int i = 0; i < names.size(); i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a -> {
                System.out.println(menuItem.getText());
            });
            playerTwo.getItems().add(menuItem);
        }

        for (int i = 0; i < names.size(); i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a -> {
                System.out.println(menuItem.getText());
            });
            playerThree.getItems().add(menuItem);
        }

        for (int i = 0; i < names.size(); i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a -> {
                System.out.println(menuItem.getText());
            });
            playerFour.getItems().add(menuItem);
        }
    }

    /*
    private void refresh(){
        for (int i = 0; i<names.size();i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a->{
                System.out.println(menuItem.getText());
                for (int j = 0; j < names.size(); j++) {
                    if (names.get(j).equals(menuItem.getText())) {
                        names.remove(j);
                    }
                }
            });
            playerOne.getItems().add(menuItem);
        }

        for (int i = 0; i<names.size();i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a->{
                System.out.println(menuItem.getText());
            });
            playerTwo.getItems().add(menuItem);
        }

        for (int i = 0; i<names.size();i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a->{
                System.out.println(menuItem.getText());
            });
            playerThree.getItems().add(menuItem);
        }

        for (int i = 0; i<names.size();i++) {
            MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
            menuItem.setOnAction(a->{
                System.out.println(menuItem.getText());
            });
            playerFour.getItems().add(menuItem);
        }
    }
    */

    /**
     * This returns a list board text files
     * @param directory 
     * @return gets a list of board text files
     */
    private List<String> textFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt")) && !file.getName().startsWith("board")) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
    }

    /**
     * this allows an back action by pressing a button
     * @param event An event to all a button to be clicked
     * @throws This throws an IOException
     */
    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        System.out.println("Back button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }
    
    /**
     * this handles the play game action by pressing a button
     * @param event An event to all a button to be clicked
     * @throws This throws an IOException
     */
    @FXML
    private void handlePlayGameAction(ActionEvent event) throws IOException {
        System.out.println("Play! button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("testBoard.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

}