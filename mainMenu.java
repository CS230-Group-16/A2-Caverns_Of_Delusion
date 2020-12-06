
import java.io.*;

import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.control.TextArea;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.text.TextFlow;
        import javafx.stage.Stage;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;

import java.net.HttpURLConnection;
import java.net.URL;

import javafx.scene.image.Image;
        import javafx.scene.layout.Background;
        import javafx.scene.layout.BackgroundImage;
        import javafx.scene.layout.BackgroundPosition;
        import javafx.scene.layout.BackgroundRepeat;
        import javafx.scene.layout.BackgroundSize;

/**
 * class represents main menu
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.0
 */
public class mainMenu extends Application{

    @FXML
    public Label msgOfTheDay = new Label();


    /**
     * creates the main menu scene
     * @param stage To create the stage
     */

    public void start(Stage stage) throws FileNotFoundException {

        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

            msgOfTheDay.setText(displayMessage());
            msgOfTheDay.setWrapText(true);
            msgOfTheDay.setVisible(true);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        launch(args);
    }


    /**
     * handles the playing
     * @param  event to handle the playing
     * @throws IOException
     */
    @FXML
    private void handlePlayAction(ActionEvent event) throws IOException {
        System.out.println("Play button clicked");
        Parent game = FXMLLoader.load(getClass().getResource("gameConfig.fxml"));
        Scene gameScene = new Scene(game);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    /**
     * handles the leaderboard actions
     * @param event An event to handle the leaderboard action
     */
    @FXML
    private void handleLeaderboardAction(ActionEvent event) {
        System.out.println("Leaderboard button clicked");
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            new LeaderboardMenu().start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles editing palyer's action
     * @param event An event to edit the player's action
     */
    @FXML
    private void handleEditPlayerAction(ActionEvent event) {
        System.out.println("Edit Player button clicked");
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            new ProfileEdit().start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This allows the exit of the game
     * @param event An event to exit the game
     */
    @FXML
    private void handleExitApplication(ActionEvent event) {
        System.out.println("Exit Program");
        try {
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays daily message
     */
    private String displayMessage() {
        String message = getMessage("http://cswebcat.swansea.ac.uk/message?solution="
                + decodeMessage(getMessage("http://cswebcat.swansea.ac.uk/puzzle")));

        return message;
    }

    /**
     * Decodes the message as per the instructions on the web site
     * @param msg message to decode
     * @return decoded message
     */
    private String decodeMessage(String msg) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String solution = "CS-230";
        int alphabetLength = alphabet.length();
        boolean back = true;
        int move = 0;
        String[] msgArr = msg.split("");
        for (int i = 0; i < msg.length(); i++) {
            if (back) {
                move = alphabet.indexOf(msgArr[i]) - (i + 1);
                if (move < 0) {
                    move = alphabetLength - (move * -1);
                }
                solution += alphabet.charAt(move);
                back = false;
            } else {
                move = alphabet.indexOf(msgArr[i]) + (i + 1);
                if (move >= alphabetLength) {
                    move = (move - alphabetLength);
                }
                solution += alphabet.charAt(move);
                back = true;
            }
        }
        solution = solution + Integer.toString(solution.length());
        return solution;
    }

    /**
     * Gets the message from a specific URL
     * @param stringURL URL to get the message from
     * @return the message from the website
     */
    private String getMessage(String stringURL) {
        String msg = "";
        try {
            URL url = new URL(stringURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            msg = in.readLine();
            in.close();
        } catch (Exception e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return msg;
    }
}
