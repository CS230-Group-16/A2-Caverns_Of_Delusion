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

public class gameConfig{

    private String[] playerList;
    private Game game;
    private String board;

    //change to personal repo
    private final String DIRECTORY = "F:\\Stuff\\230CW-TilesClasses";

    ArrayList<String> players = new ArrayList<>();

    private List<String> names = textFiles(DIRECTORY);

    private List<String> boards = boardFiles(DIRECTORY + "\\src\\files\\boards");

    @FXML
    private MenuButton boardType = new MenuButton();

    @FXML
    private MenuButton playerOne = new MenuButton();

    @FXML
    private MenuButton playerTwo = new MenuButton();

    @FXML
    private MenuButton playerThree = new MenuButton();

    @FXML
    private MenuButton playerFour = new MenuButton();



    public void initialize(){

        populate(playerOne);
        populate(playerTwo);
        populate(playerThree);
        populate(playerFour);
        populateBoards(boardType);

    }

    private void populate(MenuButton player){

        if(player.getItems() == null){

        }else {
            player.getItems().clear();
            for (int i = 0; i < names.size(); i++) {
                MenuItem menuItem = new MenuItem(names.get(i).substring(0, names.get(i).length() - 4));
                menuItem.setOnAction(a -> {
                    System.out.println(menuItem.getText());
                    player.setText(menuItem.getText());
                });
                player.getItems().add(menuItem);
            }
        }
    }

    private void populateBoards(MenuButton board){

        if(board.getItems() == null){

        }else {
            board.getItems().clear();
            for (int i = 0; i < boards.size(); i++) {
                MenuItem menuItem = new MenuItem(boards.get(i).substring(0, boards.get(i).length() - 4));
                menuItem.setOnAction(a -> {
                    System.out.println(board.getText());
                    board.setText(menuItem.getText());
                });
                board.getItems().add(menuItem);
            }
        }
    }

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

    private List<String> boardFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt"))) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
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
    private void handlePlayGameAction(ActionEvent event) throws IOException {
        System.out.println("Play! button clicked");
        menusToString();
        aquirePlayers(players);
        createGame(boardType.getText(), playerList);
        Parent root = FXMLLoader.load(getClass().getResource("BoardGUI.fxml"));

        Scene gameScene = new Scene(root);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    public void menusToString(){
        if(playerOne != null){
            players.add(playerOne.getText());
        }

        if(playerTwo != null){
            players.add(playerTwo.getText());
        }

        if(playerThree != null){
            players.add(playerThree.getText());
        }

        if(playerFour != null){
            players.add(playerFour.getText());
        }
    }

    public String[] aquirePlayers(ArrayList<String> players){
        playerList = new String[players.size()];
        for (int i = 0; i < players.size(); i++){
            playerList[i] = players.get(i);
        }
        return playerList;
    }

    public void createGame(String boardType, String[] playerList){
        if (playerList != null) {
            String playersString = "";
            for (int i = 0; i < playerList.length; i++) {
                playersString = playersString + playerList[i] + ",";
            }
            String gameString = boardType + "\n" + playersString;

            FileReader.writeFile("files\\gameConfig.txt", gameString);
        }
    }




}