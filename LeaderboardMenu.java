import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * implements a frame which is the leaderboard.
 *
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 0.1
 */
public class LeaderboardMenu extends Application {

    Button backBtn = new Button();
    private static Leaderboard board;
    private final static String DIRECTORY = "C:\\Users\\helwe\\Documents\\GitHub\\A2-Caverns_Of_Delusion\\files";

    /**
     * This is where the leaderboard frame is created and its dimensions are set
     *
     * @param primaryStage
     */
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        Image image = new Image(new FileInputStream(DIRECTORY + "\\images\\background3.png"));
        BackgroundSize backgroundSize = new BackgroundSize(700, 550, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundPicture = new Background(backgroundImage);
        
        TableView tableView = new TableView();
        tableView.setPlaceholder(new Label("No rows to display"));
        TableColumn<Player, String> column1 = new TableColumn<>("Username");
        column1.setMinWidth(200);
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Player, String> column2 = new TableColumn<>("Games Won");
        column2.setMinWidth(100);
        column2.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));

        TableColumn<Player, String> column3 = new TableColumn<>("Games Lost");
        column3.setMinWidth(100);
        column3.setCellValueFactory(new PropertyValueFactory<>("gamesLost"));

        TableColumn<Player, String> column4 = new TableColumn<>("Games Played");
        column4.setMinWidth(200);
        column4.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        ArrayList<Player> players = board.getBoard();

        for (int i = 0; i < players.size(); i++) {
            tableView.getItems().add(players.get(i));
        }

        VBox vbox = new VBox(tableView);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        
        backBtn.setText("Back");
        backBtn.setVisible(true);

        vbox.getChildren().add(backBtn);
        
        vbox.setBackground(backgroundPicture);

        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Leaderboard");
        primaryStage.setWidth(650);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
       ((Group) scene.getRoot()).getChildren().addAll(vbox);

        backBtn.setOnAction(e -> {
                    System.out.println("Back button clicked");
                    Parent game = null;
                    try {
                        game = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Scene gameScene = new Scene(game);
                    Stage gameStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    gameStage.setScene(gameScene);
                    gameStage.sizeToScene();
                    gameStage.show();
        });

        primaryStage.show();

    }

    // Driver  method
    public static void main(String[] args) {
        board = FileReader.readLeaderboardFile(DIRECTORY + "\\leaderboards\\leaderboard1.txt");
        launch(args);
    }
}
