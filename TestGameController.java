
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
import javafx.scene.layout.Pane;

/**
 * Test Controller for the main game.
 *
 * @author Bartosz Kubica
 *
 */
public class TestGameController {

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
    Pane central;

    private Game game;

    /**
     * Initialize the controller. This method is called automatically. The following happen in this order: 1) First an instance of the controller is created (the constructor is called), 2) Next the @FXML variables are bound to the GUI components. 3) Finally, this initialize method is called. This means we cannot bind actions to buttons in the constructor, but we can in this method.
     */
    public void initialize() {
        this.game = createGame();

        change.setOnAction(e -> {
            change();
        });

    }

    private Game createGame() {
        //get names from screen
        String[] strArr = new String[]{"Super_Cool_Name", "grapeLord5000"};

        //get name of file
        Game g = new Game("board1.txt", strArr);
        Player[] players = g.getPlayers();
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

        int h = g.getBoard().getHeight();
        int w = g.getBoard().getWidth();
        int x = 0;
        int y = 0;
        
        Random rand = new Random();
        String [] images = new String[]{"Corner_1","Corner_2","Corner_3","Corner_4","Straight_1","Straight_1","Straight_2","Goal","TShape_1","TShape_2","TShape_3","TShape_4"};
        
        try {
            
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    Image image1 = new Image(new FileInputStream("D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/images/Final/" + images[rand.nextInt(images.length)] + ".png"));
                    ImageView imageView = new ImageView(image1);
                    imageView.setX(x);
                    imageView.setY(y);
                    central.getChildren().add(imageView);
                    x = x + 70;
                }
                x = 0;
                y = y + 70;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return g;
    }

    /**
     * Refresh the displayed countries.
     */
    private void refreshCountryList() {
        // Clear the displayed list

    }

    /**
     * Handle the behavior of the delete button. This will delete the selected country and refresh the displayed list.
     */
    private void change() {

        Pane root2 = new Pane();
        Label l = new Label("Hello");
        root2.getChildren().add(l);
        Button b = new Button("button");
        root2.getChildren().add(b);
        Stage primaryStage = new Stage();

        Scene scene2 = new Scene(root2, 800, 800);
        primaryStage.setScene(scene2);
        primaryStage.show();
        Stage stage = (Stage) change.getScene().getWindow();
        stage.close();

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
