
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * implements a frame which is the leaderboard.
 *
 * @author Michelle Bhaskaran and Chloe Thomas
 * @version 1.0
 */
public class LeaderboardMenu extends Application {

    Button backBtn = new Button();
    private static Leaderboard board;
    private final static String DIRECTORY = FileReader.DIRECTORY;
    TableView tableView;

    /**
     * This is where the leaderboard frame is created and its dimensions are set
     *
     * @param primaryStage stage to show scene
     */
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        board = FileReader.readLeaderboardFile(DIRECTORY + "/leaderboards/leaderboard1.txt");
        
        Image image = new Image(new FileInputStream(DIRECTORY + "images/background4.png"));
        BackgroundImage bgImage = new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(1.0, 1.0, true, true, true, true)
        );
      
        tableView = new TableView();
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

        

        VBox vbox = new VBox(tableView);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        backBtn.setText("Back");
        backBtn.setVisible(true);


        List<String> files = textFiles(DIRECTORY + "leaderboards/");

        MenuButton menuButton = new MenuButton("Leaderboards");
        for (int i = 0; i < files.size(); i++) {
            AtomicInteger ordinal = new AtomicInteger(i);
            MenuItem menuItem1 = new MenuItem(files.get(i));
            menuItem1.setOnAction(e -> {
                board = FileReader.readLeaderboardFile(DIRECTORY + "leaderboards/" + files.get(ordinal.get()));
                refresh();
            });
            menuButton.getItems().add(menuItem1);
        }

        HBox box = new HBox();
        box.getChildren().addAll(backBtn, menuButton);

        vbox.getChildren().add(box);

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
        launch(args);
    }

    public void refresh() {
        tableView.getItems().clear();
        ArrayList<Player> players = board.getBoard();

        for (int i = 0; i < players.size(); i++) {
            tableView.getItems().add(players.get(i));
        }
    }

    private List<String> textFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt"))) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
    }
}
