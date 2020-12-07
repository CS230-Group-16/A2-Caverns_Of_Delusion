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

    private ArrayList<String> playerList;
    private Game game;
    private String board;

    //change to personal repo
    private final String DIRECTORY = FileReader.DIRECTORY;

    ArrayList<String> players = new ArrayList<String>();

    private List<String> names = textFiles(DIRECTORY + "players/");

    private List<String> boards = boardFiles(DIRECTORY + "boards/");

    private List<String> Saves = savedFiles(DIRECTORY + "saveGames/");

    @FXML
    private MenuButton savedGames = new MenuButton();

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
        populateSaves(savedGames);

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

    private void populateSaves(MenuButton saves){

        if(saves.getItems() == null){

        }else {
            saves.getItems().clear();
            for (int i = 0; i < Saves.size(); i++) {
                MenuItem menuItem = new MenuItem(Saves.get(i).substring(0, Saves.get(i).length() - 4));
                menuItem.setOnAction(a -> {
                    System.out.println(saves.getText());
                    saves.setText(menuItem.getText());
                });
                saves.getItems().add(menuItem);
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

    private List<String> savedFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().startsWith("SavedGame")) {
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

        if (!savedGames.getText().contains("Saved Games")) {
            loadGame();
            System.out.print("load");
        }

        if(!boardType.getText().contains("Select Board Type") && !playerOne.getText().contains("Player 1") && !playerTwo.getText().contains("Player 2")) {
            menusToString();
            this.playerList = aquirePlayers(players);
            createGame(boardType.getText(), playerList);
            System.out.print("new");
        }

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

    public ArrayList<String> aquirePlayers(ArrayList<String> players){
        ArrayList<String> playerList = new ArrayList<String>();
        if (!playerOne.getText().contains("Player 1")) {
            playerList.add(playerOne.getText());
        }
        if (!playerTwo.getText().contains("Player 2")) {
            playerList.add(playerTwo.getText());
        }
        if (!playerThree.getText().contains("Player 3")) {
            playerList.add(playerThree.getText());
        }
        if (!playerFour.getText().contains("Player 4")) {
            playerList.add(playerFour.getText());
        }
        return playerList;
    }

    public void createGame(String boardType, ArrayList<String> playerList){
        if (playerList != null) {
            System.out.println(playerList);
            String playersString = "";
            for (int i = 0; i < playerList.size(); i++) {
                playersString = playersString + playerList.get(i) + ",";
            }
            String gameString = boardType + "\n" + playersString;

            FileReader.writeFile("gameConfig.txt", gameString);
        }
    }

    private void loadGame(){
        String gameLoad = savedGames.getText() + ".txt";
        String loadData = "";

        File dir = new File(DIRECTORY + "\\saveGames");
        for (File file : dir.listFiles()) {
            if (file.getName().contains(gameLoad.substring(9))) {
                loadData = loadData + file.getName() + ",";
            }
        }

        FileReader.writeFile("loadConfig.txt", loadData);
    }

}