
import java.io.File;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * Test Controller for the main game.
 *
 * @author Bartosz Kubica
 * @version 1.0
 */
public class GameController {

    private final int WIDTH_OF_TILE_IMAGE = 80;
    private final int HEIGHT_OF_TILE_IMAGE = 80;
    private final int WIDTH_OF_PLAYER_IMAGE = 35;
    private final String DIRECTORY = FileReader.DIRECTORY;

    Button backBtn = new Button();

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
    @FXML
    ImageView playerIcon;
    @FXML
    HBox playerBox;
    @FXML
    Pane player;

    private Game game;
    private FloorTile tile;
    private int width;
    private int height;
    private ActionTile selectedTile;
    private int[] centreCoord = null;
    private boolean doublemove = false;

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

        this.game.getPlayers()[0].insertTile(new EffectTile("FIRE", -1));
        this.tile = (FloorTile) this.game.getRound().getDrawnTile();
        setPlayerNames();
        refreshBoard();
        refreshPlayers();
        setButtons();
        showDrawnTile();
        startGame();

        draw.setOnAction(e -> {
            Tile tile = this.game.getRound().getDrawnTile();
            drawnType.setText(tile.getType());

            if (tile.getType().equals("BACKTRACK") || tile.getType().equals("DOUBLEMOVE") || tile.getType().equals("FIRE") || tile.getType().equals("ICE")) {
                checkSpellBook();
            } else {
                this.tile = (FloorTile) tile;
                showDrawnTile();
            }
            this.game.saveGame();

            draw.setVisible(false);
        });

        endTurn.setOnAction(e -> {
            this.game.getRound().endTurn();
            changePlayers();
            drawnTile.getChildren().clear();
            drawnType.setText("");
            this.game.getRound().turnStart();
            this.game.saveGame();
            refreshCentral();
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
                if (this.game.getBoard().getTileAt(currentLoc[0], currentLoc[1] - 1).isEngulfed()) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("That tile is on fire!");
                    a.setContentText("Pick a different tile");
                    a.showAndWait();
                } else {
                    currentLoc[1] = currentLoc[1] - 1;
                    this.game.getRound().movement(playerNum, currentLoc);
                    this.game.saveGame();
                    if (!doublemove) {
                        setMoveButtons(false);
                        endTurn.setVisible(true);
                        refreshCentral();
                        playAction.setVisible(false);
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    } else {
                        setMoveButtons(true);
                        refreshCentral();
                        doublemove = false;
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    }
                }
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
                if (this.game.getBoard().getTileAt(currentLoc[0], currentLoc[1] + 1).isEngulfed()) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("That tile is on fire!");
                    a.setContentText("Pick a different tile");
                    a.showAndWait();
                } else {
                    currentLoc[1] = currentLoc[1] + 1;
                    this.game.getRound().movement(playerNum, currentLoc);
                    this.game.saveGame();
                    if (!doublemove) {
                        setMoveButtons(false);
                        endTurn.setVisible(true);
                        refreshCentral();
                        playAction.setVisible(false);
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    } else {
                        setMoveButtons(true);
                        refreshCentral();
                        doublemove = false;
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    }
                }
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
                if (this.game.getBoard().getTileAt(currentLoc[0] + 1, currentLoc[1]).isEngulfed()) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("That tile is on fire!");
                    a.setContentText("Pick a different tile");
                    a.showAndWait();
                } else {
                    currentLoc[0] = currentLoc[0] + 1;
                    this.game.getRound().movement(playerNum, currentLoc);
                    this.game.saveGame();
                    if (!doublemove) {
                        setMoveButtons(false);
                        endTurn.setVisible(true);
                        refreshCentral();
                        playAction.setVisible(false);
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    } else {
                        setMoveButtons(true);
                        refreshCentral();
                        doublemove = false;
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    }
                }
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
                if (this.game.getBoard().getTileAt(currentLoc[0] - 1, currentLoc[1]).isEngulfed()) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("That tile is on fire!");
                    a.setContentText("Pick a different tile");
                    a.showAndWait();
                } else {
                    currentLoc[0] = currentLoc[0] - 1;
                    this.game.getRound().movement(playerNum, currentLoc);
                    this.game.saveGame();
                    if (!doublemove) {
                        setMoveButtons(false);
                        endTurn.setVisible(true);
                        refreshCentral();
                        playAction.setVisible(false);
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    } else {
                        setMoveButtons(true);
                        refreshCentral();
                        doublemove = false;
                        reachedGoal(this.game.getRound().getCurrentPlayer());
                    }
                }
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
            ChoiceDialog cd = new ChoiceDialog("", spellStrings);
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
                                        this.game.saveGame();
                                        playSFX("BACKTRACK");
                                    }
                                }
                            }
                        } else if ("DOUBLEMOVE".equals(spellStrings[i])) {
                            doublemove = true;
                            playSFX("DOUBLEMOVE");
                        } else if ("ICE".equals(spellStrings[i]) || "FIRE".equals(spellStrings[i])) {
                            Alert a3 = new Alert(Alert.AlertType.WARNING);
                            a3.setHeaderText("Pick the center tile");
                            a3.setContentText("Select the center tile to cast your spell");
                            a3.showAndWait();
                            this.selectedTile = spells.get(i);

                            this.game.saveGame();
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

    public void playSFX(String filename) {
        Media sound = new Media(new File(DIRECTORY + "sounds/" + filename + ".wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * This starts the game
     */
    private void startGame() {
        this.game.gameStart();
        turn.setText(String.valueOf(this.game.getRound().getTurnCounter()));
    }

    /**
     * To see if a player has reached the goal and won the game
     *
     * @param current The current player
     */
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
            this.game.gameWon();

            //go to main menu
            System.exit(0);

        }
    }

    /**
     * To set the move buttons
     *
     * @param set Sets the buttons to true or false
     */
    private void setMoveButtons(boolean set) {
        up.setVisible(set);
        down.setVisible(set);
        right.setVisible(set);
        left.setVisible(set);
    }

    /**
     * to see if the player can move or not
     */
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

    /**
     * To see if the spell book has any spells in it
     */
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

    /**
     * To change the current player to the next player
     */
    private void changePlayers() {
        setPlayerNames();
        /*
        try {
            Image image = new Image(new FileInputStream(DIRECTORY + "images/Final/PlaceHolders/Player_" + this.game.getRound().getCurrentPlayer().getPlayerNum() + ".png"));
            playerIcon = new ImageView(image);
            playerIcon.setFitHeight(WIDTH_OF_PLAYER_IMAGE);
            playerIcon.setFitWidth(WIDTH_OF_PLAYER_IMAGE);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
         */
    }

    /**
     * This clears the spell book
     */
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

    /**
     * to show the drawn tile
     */
    private void showDrawnTile() {
        try {
            if (this.tile != null) {
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

                        this.game.saveGame();
                    }
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found with type: " + this.tile.getType());
        }
    }

    /**
     * This creates a new game and returns it
     *
     * @return the new game
     */
    private Game createGame() {
        //get names from screen
        String[] strArr = new String[]{"Super_Cool_Name", "grapeLord5000", "awesomeGuy", "CasualGamerGuy"};
        String[] strArr2 = new String[]{"Super_Cool_Name", "grapeLord5000", "awesomeGuy"};

        //get name of file
        Game g = new Game(DIRECTORY + "boards/board1.txt", strArr2);
        //Game g = new Game(DIRECTORY + "saveGames/SavedBoard2020.12.06.14.06.15.txt", DIRECTORY + "saveGames/SavedGame2020.12.06.14.06.15.txt");
        //return g;
        return FileReader.readGameConfig(DIRECTORY + "gameConfig.txt");
    }

    /**
     * sets the players name
     */
    private void setPlayerNames() {
        Player[] players = this.game.getPlayers();
        int counter = this.game.getRound().getCounter();

        switch (counter) {
            case 0:
                player.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 1:
                player.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 2:
                player.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 3:
                player.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            default:
                break;
        }
        switch (this.game.getRound().getNumPlayers()) {
            case 2:
                currentPlayer.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer.setText(players[counter].getUsername());
                break;
            case 3:
                currentPlayer.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer1.setText(players[counter].getUsername());
                break;
            default:
                currentPlayer.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer1.setText(players[counter].getUsername());
                if ((counter + 1) >= (players.length)) {
                    counter = 0;
                } else {
                    counter++;
                }
                nextPlayer2.setText(players[counter].getUsername());
                break;
        }

    }

    /**
     * This refreshes the players
     */
    private void refreshPlayers() {
        //central.getChildren().remove(imageView);
        Player[] players = this.game.getPlayers();
        for (int i = 1; i <= players.length; i++) {
            changeLocation(i, this.game.getBoard().getPlayerLocation(i));
        }
    }

    /**
     * changes the players location
     */
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
                            boolean success = this.game.getRound().playEffectTile(selectedTile, arr);
                            if (!success) {
                                Alert a = new Alert(Alert.AlertType.WARNING);
                                a.setHeaderText("There is a player in the way!");
                                a.setContentText("You cannot cast a spell on a player");
                                a.showAndWait();
                            } else {
                                if ("FIRE".equals(selectedTile.getEffect())) {
                                    playSFX("FIRE");
                                } else {
                                    playSFX("ICE");
                                }
                                this.selectedTile = null;
                                this.spells.getChildren().clear();
                                refreshCentral();
                                playAction.setVisible(false);

                            }
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
    /**
     * this sets the button
     */
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

    /**
     * this inserts a tile into the board
     *
     * @param t The type of floor tile
     * @param row Which row you want it to be inserted into
     * @param posNum The postion of the tile to be
     * @param flip
     */
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

    /**
     * This refreshes the board, players and sets the buttons again.
     */
    private void refreshCentral() {
        central.getChildren().clear();
        refreshBoard();
        setButtons();
        refreshPlayers();

        this.game.saveGame();
    }
}
