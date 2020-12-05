
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

/**
 * Test Controller for the main game.
 *
 * @author Bartosz Kubica
 *
 */
public class TestGameController {

    private final int WIDTH_OF_TILE_IMAGE = 80;
    private final int HEIGHT_OF_TILE_IMAGE = 80;
    private final int WIDTH_OF_PLAYER_IMAGE = 35;
    private final String DIRECTORY = "D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/files/";

    @FXML
    Button draw;
    @FXML
    Button endTurn;
    @FXML
    Button rotate;
    @FXML
    Button playAction;
    @FXML
    Button up, down, left, right;
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
    private ActionTile selectedTile;
    private int[] centreCoord = null;

    /**
     * Initialize the controller. This method is called automatically. The following happen in this order: 1) First an instance of the controller is created (the constructor is called), 2) Next the @FXML variables are bound to the GUI components. 3) Finally, this initialize method is called. This means we cannot bind actions to buttons in the constructor, but we can in this method.
     */
    public void initialize() {
        central.setGridLinesVisible(false);
        central.setAlignment(Pos.CENTER);
        endTurn.setVisible(false);
        playAction.setVisible(false);
        setMoveButtons(false);

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

        this.game.getPlayers()[0].insertTile(new EffectTile("ICE", -1));
        this.game.getPlayers()[0].insertTile(new EffectTile("FIRE", -1));
        this.game.getPlayers()[0].insertTile(new MovementTile("DOUBLEMOVE", -1));
        this.game.getPlayers()[0].insertTile(new MovementTile("BACKTRACK", -1));
        this.game.getPlayers()[2].updatePathHistory(new int[]{1, 1});
        this.game.getPlayers()[2].updatePathHistory(new int[]{0, 0});

        setPlayerNames();
        refreshBoard();
        refreshPlayers();
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
                checkSpellBook();
            } else {
                this.tile = (FloorTile) tile;
                showDrawnTile();
            }

            draw.setVisible(false);
        });

        endTurn.setOnAction(e -> {
            this.game.getRound().endTurn();
            changePlayers();
            drawnTile.getChildren().clear();
            drawnType.setText("");
            this.game.getRound().turnStart();
            turn.setText(String.valueOf(this.game.getRound().getTurnCounter()));
            this.tile = null;
            draw.setVisible(true);
            endTurn.setVisible(false);
            spells.getChildren().clear();
            playAction.setVisible(false);
        });

        up.setOnAction(e -> {
            int playerNum = this.game.getRound().getCurrentPlayer().getPlayerNum();
            int[] currentLoc = this.game.getBoard().getPlayerLocation(playerNum);
            boolean[] paths = this.game.getBoard().checkPathway(playerNum);
            if (paths[0]) {
                currentLoc[1] = currentLoc[1] - 1;
                this.game.getRound().movement(playerNum, currentLoc);
                setMoveButtons(false);
                endTurn.setVisible(true);
                refreshCentral();
                playAction.setVisible(false);
                reachedGoal(this.game.getRound().getCurrentPlayer());
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("You can't move through that!");
                a.setContentText("You cannot move this way");
                a.showAndWait();
            }
        });

        down.setOnAction(e -> {
            int playerNum = this.game.getRound().getCurrentPlayer().getPlayerNum();
            int[] currentLoc = this.game.getBoard().getPlayerLocation(playerNum);
            boolean[] paths = this.game.getBoard().checkPathway(playerNum);
            if (paths[2]) {
                currentLoc[1] = currentLoc[1] + 1;
                this.game.getRound().movement(playerNum, currentLoc);
                setMoveButtons(false);
                endTurn.setVisible(true);
                refreshCentral();
                playAction.setVisible(false);
                reachedGoal(this.game.getRound().getCurrentPlayer());
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("You can't move through that!");
                a.setContentText("You cannot move this way");
                a.showAndWait();
            }
        });

        right.setOnAction(e -> {
            int playerNum = this.game.getRound().getCurrentPlayer().getPlayerNum();
            int[] currentLoc = this.game.getBoard().getPlayerLocation(playerNum);
            boolean[] paths = this.game.getBoard().checkPathway(playerNum);
            if (paths[1]) {
                currentLoc[0] = currentLoc[0] + 1;
                this.game.getRound().movement(playerNum, currentLoc);
                setMoveButtons(false);
                endTurn.setVisible(true);
                refreshCentral();
                playAction.setVisible(false);
                reachedGoal(this.game.getRound().getCurrentPlayer());
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("You can't move through that!");
                a.setContentText("You cannot move this way");
                a.showAndWait();
            }
        });

        left.setOnAction(e -> {
            int playerNum = this.game.getRound().getCurrentPlayer().getPlayerNum();
            int[] currentLoc = this.game.getBoard().getPlayerLocation(playerNum);
            boolean[] paths = this.game.getBoard().checkPathway(playerNum);
            if (paths[3]) {
                currentLoc[0] = currentLoc[0] - 1;
                this.game.getRound().movement(playerNum, currentLoc);
                setMoveButtons(false);
                endTurn.setVisible(true);
                refreshCentral();
                playAction.setVisible(false);
                reachedGoal(this.game.getRound().getCurrentPlayer());
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("You can't move through that!");
                a.setContentText("You cannot move this way");
                a.showAndWait();
            }

        });

        playAction.setOnAction(e -> {
            //warning to other players looking
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("All players look away!");
            a.setContentText(this.game.getRound().getCurrentPlayer().getUsername() + " wants to cast a spell");
            a.showAndWait();

            //pick spell from spell book
            ArrayList<ActionTile> spells = this.game.getRound().getCurrentPlayer().getSpellBook();
            String[] spellStrings = new String[spells.size()];
            for (int i = 0; i < spells.size(); i++) {
                spellStrings[i] = spells.get(i).getEffect();
            }
            ChoiceDialog cd = new ChoiceDialog(spellStrings[0], spellStrings);
            cd.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
            refreshSpellBook();
            cd.setHeaderText("Pick a spell!");
            cd.setContentText("Pick spell to cast");
            cd.showAndWait();
            for (int i = 0; i < spells.size(); i++) {
                if (spellStrings[i] == cd.getSelectedItem()) {
                    if (spells.get(i).getTurnDrawn() >= this.game.getRound().getTurnCounter()) {
                        Alert a3 = new Alert(Alert.AlertType.WARNING);
                        a3.setHeaderText("Spell Cast!");
                        a3.setContentText("You cannot cast this spell just yet");
                        a3.showAndWait();
                    } else {
                        if ("BACKTRACK".equals(spellStrings[i])) {
                            String[] players = new String[this.game.getPlayers().length];
                            Player p;
                            for (int x = 0; x < this.game.getPlayers().length; x++) {
                                players[x] = this.game.getPlayers()[x].getUsername();
                            }
                            ChoiceDialog cd2 = new ChoiceDialog(players[0], players);
                            cd2.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
                            cd2.setHeaderText("Pick a player!");
                            cd2.setContentText("Pick a player to cast your spell");
                            cd2.showAndWait();
                            for (int y = 0; y < players.length; y++) {
                                if (players[y] == cd2.getSelectedItem()) {
                                    p = this.game.getPlayers()[y];
                                    boolean moved = this.game.getRound().playMoveTile(spells.get(i), p);
                                    if (!moved) {
                                        Alert a3 = new Alert(Alert.AlertType.WARNING);
                                        a3.setHeaderText("You cannot move that player");
                                        a3.setContentText("Select a different player");
                                        a3.showAndWait();
                                    } else {
                                        refreshCentral();
                                    }
                                }
                            }
                        } else if ("DOUBLEMOVE".equals(spellStrings[i])) {
                            //do something
                        } else if ("ICE".equals(spellStrings[i]) || "FIRE".equals(spellStrings[i])) {
                            Alert a3 = new Alert(Alert.AlertType.WARNING);
                            a3.setHeaderText("Pick the center tile");
                            a3.setContentText("Select the center tile to cast your spell");
                            a3.showAndWait();
                            this.selectedTile = spells.get(i);
                        }
                    }
                }
            }

            //either pick tile or pick player depending on tile type
            //playActionTile
            this.spells.getChildren().clear();
            checkMovement();
        });

    }

    private void startGame() {
        this.game.gameStart();
        turn.setText(String.valueOf(this.game.getRound().getTurnCounter()));
    }

    private void reachedGoal(Player current) {
        int[] location = this.game.getBoard().getPlayerLocation(current.getPlayerNum());
        int[] goal = this.game.getBoard().getGoalLocation();
        if (location[0] == goal[0] && location[1] == goal[1]) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("A Player Has Reached The Goal!");
            a.setContentText(current.getUsername() + " has won the game");
            a.showAndWait();
            draw.setVisible(false);
            endTurn.setVisible(false);
            rotate.setVisible(false);
            playAction.setVisible(false);
            setMoveButtons(false);
            drawnType.setText("");
            drawnTile.getChildren().clear();
            spells.getChildren().clear();
            //go to main menu
        }
    }

    private void setMoveButtons(boolean set) {
        up.setVisible(set);
        down.setVisible(set);
        right.setVisible(set);
        left.setVisible(set);
    }

    private void checkMovement() {
        boolean[] paths = this.game.getBoard().checkPathway(this.game.getRound().getCurrentPlayer().getPlayerNum());
        if (!paths[0] && !paths[1] && !paths[2] && !paths[3]) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("You're Trapped!");
            a.setContentText("There is no where for you to go!");
            a.showAndWait();
            endTurn.setVisible(true);
            setMoveButtons(false);
        } else {
            setMoveButtons(true);
        }
    }

    private void checkSpellBook() {
        if (this.game.getRound().getCurrentPlayer().getSpellBook().size() <= 0) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("You have no spells in your spell book!");
            a.setContentText("You dont have any spells to play");
            a.showAndWait();
        } else {
            playAction.setVisible(true);
        }
        checkMovement();
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
                Image image1 = new Image(new FileInputStream(DIRECTORY + "images/Final/ActionCards/" + tiles.get(i).getEffect() + ".png"));
                ImageView imageView = new ImageView(image1);
                imageView.setFitHeight(WIDTH_OF_TILE_IMAGE);
                imageView.setFitWidth(WIDTH_OF_TILE_IMAGE);
                spells.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found with type: " + tiles.get(i).getType());
            }
        }
    }

    private void showDrawnTile() {
        try {
            Image image1 = new Image(new FileInputStream(DIRECTORY + "images/Final/FloorTiles/" + this.tile.getType() + ".png"));
            ImageView imageView = new ImageView(image1);
            drawnTile.getChildren().add(imageView);
            rotate.setOnAction(e -> {
                if (this.tile != null) {
                    imageView.setRotate(imageView.getRotate() + 90);
                    if (this.tile.getRotation() >= 4) {
                        this.tile.setRotation(0);
                    } else {
                        this.tile.setRotation(this.tile.getRotation() + 1);
                    }
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
        Game g = new Game(DIRECTORY + "boards/board1.txt", strArr);
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
        //central.getChildren().remove(imageView);
        Player[] players = this.game.getPlayers();
        for (int i = 1; i <= players.length; i++) {
            changeLocation(i, this.game.getBoard().getPlayerLocation(i));
        }
    }

    private void changeLocation(int player, int[] location) {
        try {
            Image image1 = new Image(new FileInputStream(DIRECTORY + "images/Final/PlaceHolders/Player_" + player + ".png"));
            ImageView imageView = new ImageView(image1);
            imageView.setFitHeight(WIDTH_OF_PLAYER_IMAGE);
            imageView.setFitWidth(WIDTH_OF_PLAYER_IMAGE);
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
        central.getChildren().clear();
        FloorTile[][] tileMap = this.game.getBoard().getTileMap();
        Image image = null;
        ImageView imageView;

        try {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (tileMap[j][i].isFrozen() && tileMap[j][i].isFixed()) {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/IceTiles/" + tileMap[j][i].getType() + "_FIXED.png"));
                        imageView = new ImageView(image);
                    } else if (tileMap[j][i].isEngulfed() && tileMap[j][i].isFixed()) {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/FireTiles/" + tileMap[j][i].getType() + "_FIXED.png"));
                        imageView = new ImageView(image);
                    } else if (tileMap[j][i].isEngulfed()) {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/FireTiles/" + tileMap[j][i].getType() + ".png"));
                        imageView = new ImageView(image);
                    } else if (tileMap[j][i].isFrozen()) {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/IceTiles/" + tileMap[j][i].getType() + ".png"));
                        imageView = new ImageView(image);
                    } else if (tileMap[j][i].isFixed()) {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/FixedTiles/" + tileMap[j][i].getType() + ".png"));
                        imageView = new ImageView(image);
                    } else {
                        image = new Image(new FileInputStream(DIRECTORY + "images/Final/FloorTiles/" + tileMap[j][i].getType() + ".png"));
                        imageView = new ImageView(image);
                    }

                    imageView.setRotate(90 * tileMap[j][i].getRotation());
                    int[] arr = {j, i};
                    imageView.setOnMouseClicked(e -> {
                        centreCoord = arr;
                        if (selectedTile != null) {
                            this.game.getRound().playEffectTile(selectedTile, arr);
                            this.selectedTile = null;
                            this.spells.getChildren().clear();
                            refreshCentral();
                            playAction.setVisible(false);
                        }
                    });
                    central.add(imageView, (j + 1), (i + 1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //refreshPlayers();
    }

    //hue 0.5 for frozen
    //hue -0.5 for fire
    private void setButtons() {
        boolean[] row = this.game.getBoard().getBlockedRow();
        boolean[] column = this.game.getBoard().getBlockedColumn();

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
        boolean inserted = this.game.getBoard().insertTile(t, row, posNum, flip);
        if (inserted) {
            drawnTile.getChildren().clear();
            drawnType.setText("");
            refreshCentral();
            this.tile = null;
            checkSpellBook();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("You cannot insert it there!");
            a.setContentText("There is a frozen tile in the way\ntry somewhere else");
            a.showAndWait();
        }

    }

    private void refreshCentral() {
        central.getChildren().clear();
        refreshBoard();
        setButtons();
        refreshPlayers();
    }
}
