
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
    private final String DIRECTORY = "D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/images/";

    @FXML
    Button draw;
    @FXML
    Button endTurn;
    @FXML
    Button rotate;
    @FXML
    Label drawnType;
    @FXML
    Label turn;
    @FXML
    Label currentPlayer;
    @FXML
    Label nextPlayer;
    @FXML
    Label nextPlayer1;
    @FXML
    Label nextPlayer2;
    @FXML
    GridPane central;
    @FXML
    Pane drawnTile;
    @FXML
    HBox spells;

    private Game game;
    private FloorTile tile;
    private int width;
    private int height;

    /**
     * Initialize the controller. This method is called automatically. The following happen in this order: 1) First an instance of the controller is created (the constructor is called), 2) Next the @FXML variables are bound to the GUI components. 3) Finally, this initialize method is called. This means we cannot bind actions to buttons in the constructor, but we can in this method.
     */
    public void initialize() {
        central.setGridLinesVisible(false);
        central.setAlignment(Pos.CENTER);
        endTurn.setVisible(false);

        this.game = createGame();
        width = this.game.getBoard().getWidth();
        height = this.game.getBoard().getHeight();
        for (int i = 0; i <= (width + 1); i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH_OF_TILE_IMAGE);
            central.getColumnConstraints().add(column);
        }
        for (int i = 0; i <= (height + 1); i++) {
            RowConstraints row = new RowConstraints(HEIGHT_OF_TILE_IMAGE);
            central.getRowConstraints().add(row);
        }

        setPlayerNames();
        refreshBoard();
        refreshPlayers();
        refreshSpellBook();
        setButtons();
        startGame();
        /*
        change.setOnAction(e -> {
            GoalTile t = new GoalTile();
            this.game.getBoard().insertTile(t, true, 2, false, 0);
            refreshBoard();
            refreshPlayers();
        });
         */

        draw.setOnAction(e -> {
            Tile tile = this.game.getRound().getDrawnTile();
            drawnType.setText(tile.getType());

            if (tile.getType().equals("BACKTRACK") || tile.getType().equals("DOUBLEMOVE") || tile.getType().equals("FIRE") || tile.getType().equals("ICE")) {
                refreshSpellBook();
            } else {
                this.tile = (FloorTile) tile;
                showDrawnTile();
            }

            //ask to play action
            //move
            //end appears after the person has moved
            endTurn.setVisible(true);
            draw.setVisible(false);
        });

        endTurn.setOnAction(e -> {
            this.game.getRound().endTurn();
            changePlayers();
            refreshSpellBook();
            drawnTile.getChildren().clear();
            drawnType.setText("");
            this.game.getRound().turnStart();
            turn.setText(String.valueOf(this.game.getRound().getTurnCounter()));
            draw.setVisible(true);
            endTurn.setVisible(false);
        });

    }

    private void startGame() {
        this.game.gameStart();
        turn.setText(String.valueOf(this.game.getRound().getTurnCounter()));
    }

    private void changePlayers() {
        /*
        Player [] players = this.game.getPlayers();
        currentPlayer.setText(this.game.getRound().getCurrentPlayer().getUsername());
        nextPlayer.setText(this.game.getRound().getNextPlayer().getUsername());
        nextPlayer1.setText(players[this.game.getRound().getCounter()+1].getUsername());
        nextPlayer2.setText(players[this.game.getRound().getCounter()+2].getUsername());
         */
        nextPlayer.setText(nextPlayer1.getText());
        nextPlayer1.setText(nextPlayer2.getText());
        nextPlayer2.setText(currentPlayer.getText());
        currentPlayer.setText(this.game.getRound().getCurrentPlayer().getUsername());
    }

    private void refreshSpellBook() {
        spells.getChildren().clear();
        ArrayList<ActionTile> tiles = this.game.getRound().getCurrentPlayer().getSpellBook();
        ActionTile[] tileArr = new ActionTile[1];
        
        for (int i = 0; i < tiles.size(); i++) {
            tileArr[0] = tiles.get(i);
            try {
                Image image1 = new Image(new FileInputStream(DIRECTORY + "Final/" + tiles.get(i).getEffect() + ".png"));
                ImageView imageView = new ImageView(image1);
                imageView.setPickOnBounds(true);
                imageView.setOnMouseClicked(e -> {
                    handleSpell(tileArr[0]);
                });
                spells.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found with type: " + tiles.get(i).getType());
            }
        }
    }
    
    private void handleSpell(ActionTile t){
        if (t.getTurnDrawn() >= this.game.getRound().getTurnCounter()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("You cannot cast this spell just yet");
            a.showAndWait();
        } else {
            this.game.getRound().playActionTile(t);
        }
    }

    private void showDrawnTile() {
        try {
            Image image1 = new Image(new FileInputStream(DIRECTORY + "Final/" + this.tile.getType() + ".png"));
            ImageView imageView = new ImageView(image1);
            drawnTile.getChildren().add(imageView);
            rotate.setOnAction(e -> {
                imageView.setRotate(imageView.getRotate() + 90);
                if (this.tile.getRotation() >= 4) {
                    this.tile.setRotation(0);
                } else {
                    this.tile.setRotation(this.tile.getRotation() + 1);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found with type: " + this.tile.getType());
        }
    }

    private Game createGame() {
        //get names from screen
        String[] strArr = new String[]{"Super_Cool_Name", "grapeLord5000", "awesomeGuy", "CasualGamerGuy"};
        String[] strArr2 = new String[]{"Super_Cool_Name", "grapeLord5000", "awesomeGuy"};

        //get name of file
        Game g = new Game("board1.txt", strArr);
        return g;
    }

    private void setPlayerNames() {
        Player[] players = this.game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            switch (i) {
                case 0:
                    currentPlayer.setText(players[i].getUsername());
                    break;
                case 1:
                    nextPlayer.setText(players[i].getUsername());
                    break;
                case 2:
                    nextPlayer1.setText(players[i].getUsername());
                    break;
                case 3:
                    nextPlayer2.setText(players[i].getUsername());
                    break;
                default:
                    break;
            }
        }
    }

    private void refreshPlayers() {
        Player[] players = this.game.getPlayers();
        for (int i = 1; i <= players.length; i++) {
            changeLocation(i, this.game.getBoard().getPlayerLocation(i));
        }
    }

    private void changeLocation(int player, int[] location) {
        try {
            Image image1 = new Image(new FileInputStream(DIRECTORY + "PLAYER" + player + ".png"));
            ImageView imageView = new ImageView(image1);
            //imageView.setX(WIDTH_OF_PLAYER_IMAGE);
            //imageView.setAlignment(Pos.CENTER);
            //central.getChildren().add(imageView);
            central.add(imageView, (location[0] + 1), (location[1] + 1));
            GridPane.setHalignment(imageView, HPos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Refresh the displayed tiles.
     */
    private void refreshBoard() {
        FloorTile[][] tileMap = this.game.getBoard().getTileMap();

        try {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Image image1 = new Image(new FileInputStream(DIRECTORY + "Final/" + tileMap[j][i].getType() + ".png"));
                    ImageView imageView = new ImageView(image1);
                    imageView.setRotate(90 * tileMap[j][i].getRotation());
                    central.add(imageView, (j + 1), (i + 1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setButtons() {
        boolean[] row = this.game.getBoard().getBlockedRow();
        boolean[] column = this.game.getBoard().getBlockedColumn();

        int[] iArr = new int[1];
        for (int i = 0; i < row.length; i++) {
            AtomicInteger ordinal = new AtomicInteger(i);
            if (!row[i]) {
                Button bRow = new Button();
                bRow.setText(">");
                bRow.setOnAction(e -> {
                    if (this.tile != null) {
                        insertTile(this.tile, true, ordinal.get(), false);
                    }
                });
                Button bRow2 = new Button();
                bRow2.setText("<");
                bRow2.setOnAction(e -> {
                    if (this.tile != null) {
                        insertTile(this.tile, true, ordinal.get(), true);
                    }
                });
                central.add(bRow, 0, (i + 1));
                central.add(bRow2, (width + 1), (i + 1));
                GridPane.setHalignment(bRow, HPos.CENTER);
                GridPane.setHalignment(bRow2, HPos.CENTER);
            }
        }

        for (int i = 0; i < column.length; i++) {
            AtomicInteger ordinal = new AtomicInteger(i);
            if (!column[i]) {
                Button bCol = new Button();
                bCol.setText("V");
                bCol.setOnAction(e -> {
                    if (this.tile != null) {
                        insertTile(this.tile, false, ordinal.get(), false);
                    }
                });
                Button bCol2 = new Button();
                bCol2.setText("^");
                bCol2.setOnAction(e -> {
                    if (this.tile != null) {
                        insertTile(this.tile, false, ordinal.get(), true);
                    }
                });
                central.add(bCol, (i + 1), 0);
                central.add(bCol2, (i + 1), (height + 1));
                GridPane.setHalignment(bCol, HPos.CENTER);
                GridPane.setHalignment(bCol2, HPos.CENTER);
            }
        }
    }

    private void insertTile(FloorTile t, boolean row, int posNum, boolean flip) {
        this.game.getBoard().insertTile(t, row, posNum, flip);
        drawnTile.getChildren().clear();
        drawnType.setText("");
        refreshBoard();
        refreshPlayers();
        this.tile = null;
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
