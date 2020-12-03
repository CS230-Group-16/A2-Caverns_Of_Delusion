import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class LeaderboardMenu extends Application {
    
    

    private final TableView<Player> table = new TableView<Player>();
    private final ArrayList<Player> player = new ArrayList<Player>();
    
    private void readLeaderboard() {
        File leaderboardFile = new File("LeaderboardFile.txt");
        try {
            Scanner profile = new Scanner(leaderboardFile);
            while (profile.hasNext()) {
            	player.add(new Player(profile.next(), profile.nextInt(), profile.nextInt()));
            }
	} catch (FileNotFoundException e) {
		System.out.println("Cannot open " + "LeaderboardFile.txt");
	}
        
         /*for (int i = 0; i < player.size(); i++) {
                new Player(player.get(i).getUsername(), player.get(i).getGamesWon(), player.get(i).getGamesPlayed());
            }*/
    }
    
    private ObservableList<Player> data = FXCollections.observableArrayList(
       //new Player(player.get(0).getUsername(), player.get(0).getGamesWon(), player.get(0).getGamesPlayed())
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(new Group());
        root.setId("pane");
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
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

        TableColumn gamesPlayed = new TableColumn("Games Played");
        gamesPlayed.setMinWidth(200);
        gamesPlayed.setCellValueFactory(new PropertyValueFactory<Player, String>("gamesPlayed"));

        readLeaderboard();
        table.setItems(data);
        table.getColumns().addAll(username, score, gamesPlayed);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
