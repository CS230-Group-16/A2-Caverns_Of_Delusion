
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

/**
 * Test Controller for the main game.
 *
 * @author Bartosz Kubica
 *
 */
public class TestGameController {

    private final int WIDTH_OF_TILE_IMAGE = 70;
    private final int HEIGHT_OF_TILE_IMAGE = 70;
    private final int WIDTH_OF_PLAYER_IMAGE = 25;    
    @FXML
    Button change;
    @FXML
    Label p1;
    @FXML
    Label p2;
    @FXML
    Label p3;
    @FXML
    Label p4;
    @FXML
    GridPane central;
    @FXML
    HBox spells;

    private Game game;

    /**
     * Initialize the controller. This method is called automatically. The following happen in this order: 
     * 1) First an instance of the controller is created (the constructor is called), 
     * 2) Next the @FXML variables are bound to the GUI components. 
     * 3) Finally, this initialize method is called. This means we cannot bind actions to buttons in the constructor, but we can in this method.
     */
    public void initialize() {
        central.setGridLinesVisible(true);
        
        this.game = createGame();
        for (int i = 0; i < this.game.getBoard().getWidth(); i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH_OF_TILE_IMAGE);
            central.getColumnConstraints().add(column);
        }
        for (int i = 0; i < this.game.getBoard().getHeight(); i++) {
            RowConstraints row = new RowConstraints(HEIGHT_OF_TILE_IMAGE);
            central.getRowConstraints().add(row);
        }
        setPlayerNames();
        refreshBoard();
        refreshPlayers();

        change.setOnAction(e -> {
            GoalTile t = new GoalTile();
            this.game.getBoard().insertTile(t, true, 2, false, 1);
            refreshBoard();
            
        });

    }

    private Game createGame() {
        //get names from screen
        String[] strArr = new String[]{"Super_Cool_Name", "grapeLord5000", "awesomeGuy", "CasualGamerGuy"};

        //get name of file
        Game g = new Game("board1.txt", strArr);
        return g;
    }
    
    private void setPlayerNames(){
        Player[] players = this.game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            switch (i) {
                case 0:
                    p1.setText(players[i].getUsername());
                    break;
                case 1:
                    p2.setText(players[i].getUsername());
                    break;
                case 2:
                    p3.setText(players[i].getUsername());
                    break;
                case 3:
                    p4.setText(players[i].getUsername());
                    break;
                default:
                    break;
            }
        }
    }

    private void refreshPlayers(){
        Player[] players = this.game.getPlayers();
        for (int i = 1; i <= players.length; i++) {
            changeLocation(i,this.game.getBoard().getPlayerLocation(i));
        }
    }
    
    private void changeLocation(int player, int[] location){
        int x = WIDTH_OF_PLAYER_IMAGE + (WIDTH_OF_TILE_IMAGE * (location[0]));
        int y = WIDTH_OF_PLAYER_IMAGE + (WIDTH_OF_TILE_IMAGE * (location[1]));

        try {
            Image image1 = new Image(new FileInputStream("D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/images/PLAYER" + player + ".png"));
            ImageView imageView = new ImageView(image1);
            imageView.setX(x);
            imageView.setY(y);
            //central.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    /**
     * Refresh the displayed tiles.
     */
    private void refreshBoard() {
        int h = this.game.getBoard().getHeight();
        int w = this.game.getBoard().getWidth();
        int x = 0;
        int y = 0;
        String tile;
        
        FloorTile[][] tileMap = this.game.getBoard().getTileMap();
        try {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if ("GOAL".equals(tileMap[j][i].getType())) {
                        tile = "GOAL";
                    } else {
                        tile = tileMap[j][i].getType() + "_" + (tileMap[j][i].getRotation()+1);
                    }
                    Image image1 = new Image(new FileInputStream("D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/images/Final/" + tile + ".png"));
                    ImageView imageView = new ImageView(image1);
                    //imageView.setX(x);
                    //imageView.setY(y);
                    //central.getChildren().add(imageView);
                    central.add(imageView,j,i);
                    //central.getChildren().add(imageView);
                    x = x + WIDTH_OF_TILE_IMAGE;
                }
                x = 0;
                y = y + WIDTH_OF_TILE_IMAGE;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * Handle the edit button. This will display a window allowing the user to edit the selected country. After the edit is complete, the displayed list will be updated.
     */
    /*
	private void handleEditButtonAction() {

			// Show a message
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot edit country");
			alert.setHeaderText(null);
			alert.setContentText("Cannot edit country as no country is selected. Please select a country first.");
			alert.showAndWait();
			
		

		// We need to use a try-catch block as the loading of the FXML file can fail.
		try {
			// Create a FXML loader for loading the Edit Country FXML file.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditCountry.fxml"));     

			// Run the loader
			BorderPane editRoot = (BorderPane)fxmlLoader.load();          
			// Access the controller that was created by the FXML loader
			//EditCountryController editController = fxmlLoader.<EditCountryController>getController();
			
			//*************
			//* Important *
			//*************
			// Tell the controller which country we are editing.
			// Remember we are passing arrows (i.e., references) around.
			// This means that the edit controller's changes to the country will be reflected here (in our list).
			//editController.setCountryForEditing(selectedCountry);
			
			// Create a scene based on the loaded FXML scene graph
			//Scene editScene = new Scene(editRoot, Main2.EDIT_WINDOW_WIDTH, Main2.EDIT_WINDOW_HEIGHT);
		    
			// Create a new stage (i.e., window) based on the edit scene
			Stage editStage = new Stage();
			//editStage.setScene(editScene);
			//editStage.setTitle(Main2.EDIT_WINDOW_TITLE);
			
			// Make the stage a modal window.
			// This means that it must be closed before you can interact with any other window from this application.
			editStage.initModality(Modality.APPLICATION_MODAL);
			
			// Show the edit scene and wait for it to be closed
			editStage.showAndWait();
			
			// The above method only returns when the window is closed.
			
			// Any changes that the edit scene made to the country will have taken place to the country object.
			// This object is part of our country list.
			// So we just need to refresh the JavaFX ListView.
			refreshCountryList();
			
		} catch (IOException e) {
			e.printStackTrace();
			// Quit the program (with an error code)
			System.exit(-1);
		}
	}
     */
}
