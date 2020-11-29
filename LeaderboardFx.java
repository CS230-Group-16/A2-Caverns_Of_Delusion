import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LeaderboardFx extends Application {

    private TableView<Player> scoreboard = new TableView<Player>();

    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Leaderboard");
        stage.setWidth(400);
        stage.setHeight(500);

        TableView scoreboard = new TableView();

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        scoreboard.setEditable(false);

        TableColumn username = new TableColumn("Username");
        username.setMinWidth(100);
        username.setCellValueFactory(new PropertyValueFactory<Player,String>("Username"));
        TableColumn score = new TableColumn("Score");
        score.setMinWidth(100);
        score.setCellValueFactory(new PropertyValueFactory<Player,String>("Score"));
        TableColumn gamesPlayed = new TableColumn("Games Played");
        gamesPlayed.setMinWidth(100);
        gamesPlayed.setCellValueFactory(new PropertyValueFactory<Player,String>("Games Played"));

        //table.setItems(data);
        scoreboard.getColumns().addAll(usernameCol, scoreCol, playedCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(title, scoreboard);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

}
