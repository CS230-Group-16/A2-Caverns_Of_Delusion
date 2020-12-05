import java.awt.Color;
        import java.io.File;
        import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
        import java.util.Scanner;
        import javafx.application.Application;
        import static javafx.application.Application.launch;
        import static javafx.application.Application.launch;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
        import javax.swing.JFrame;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;

/**
 * implements a frame which is the leaderboard.
 *
 * @author Chloe and Michelle
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
    public void start(Stage primaryStage) {
        TableView tableView = new TableView();
        tableView.setPlaceholder(new Label("No rows to display"));
        TableColumn<Player, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Player, String> column2 = new TableColumn<>("Games Won");
        column2.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));

        TableColumn<Player, String> column3 = new TableColumn<>("Games Lost");
        column3.setCellValueFactory(new PropertyValueFactory<>("gamesLost"));

        TableColumn<Player, String> column4 = new TableColumn<>("Games Played");
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

        backBtn.setText("Back");
        backBtn.setVisible(true);

        vbox.getChildren().add(backBtn);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

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
