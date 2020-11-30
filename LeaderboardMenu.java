import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LeaderboardMenu extends Application {

    private TableView<Player> table = new TableView<Player>();
    private final ObservableList<Player> data = FXCollections.observableArrayList(
            new Player("Chloe", 5, 9),
            new Player("Michelle", 8, 3)
    );

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Leaderboard");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("Leaderboard");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn username = new TableColumn("Username");
        username.setMinWidth(100);
        username.setCellValueFactory(new PropertyValueFactory<Player, String>("username"));

        TableColumn score = new TableColumn("Score");
        score.setMinWidth(100);
        score.setCellValueFactory(new PropertyValueFactory<Player, String>("gamesWon"));

        TableColumn gamesLost = new TableColumn("Games Lost");
        gamesLost.setMinWidth(200);
        gamesLost.setCellValueFactory(new PropertyValueFactory<Player, String>("gamesLost"));

        table.setItems(data);
        table.getColumns().addAll(username, score, gamesLost);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
