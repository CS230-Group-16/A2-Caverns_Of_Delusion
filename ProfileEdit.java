import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Edit profile name or delete profile with a GUI and also see a profiles game status.
 * @author Jimmy Kells and Surinder Singh.
 * @version 1.0
 */

public class ProfileEdit extends Application {
    //Change directory to personal one
    private final String DIRECTORY =  FileReader.DIRECTORY;
    //initialising the tools to be displayed in JavaFX
    private Label gamesPlayedLbl = new Label("no player selected");
    private Label gamesWonLbl = new Label();
    private Label gamesLostLbl = new Label();
    private Button deleteBtn = new Button();
    private Button updateUserBtn = new Button();
    Button backBtn = new Button();
    private TextField usernameTxtbox = new TextField();
    private Scanner in = new Scanner(System.in);
    private int location = 0;

    /**
     * A method to create a scanner to read a file.
     * @param filename The name of the file to be read.
     * @return Scanner The scanner to read the file.
     */
    public Scanner readFile(String filename) {
        File file = new File(DIRECTORY + "\\players\\" + filename);
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            //if the file is not found, a clear error message is shown with the file that the program was looking for
            System.out.println(file + " not found in directory: " + DIRECTORY);
            e.printStackTrace();
            System.exit(0);
        }
        return in;
    }

    /**
     * Gets the amount of games the selected profile has lost.
     * @param username The user to get the games won.
     * @return gamesWon The number of games the user has won.
     */
    public int readGamesWon(String username) {
        int gamesWon = 0;

        try {
            //reads the first line of the profile which is the games won
            Scanner in = readFile(username);
            gamesWon = in.nextInt();
            //close scanner because its no longer in use
            in.close();
        } catch (Exception e) {
            System.err.println("Player not Found");
        }
        return gamesWon;
    }

    /**
     * Gets the amount of games the selected profile has lost.
     * @param username The user to get the games lost.
     * @return gamesLost The number of games the user has lost.
     */
    public int readGamesLost(String username) {
        int gamesLost = 0;

        try {
            //reads the first and second line of profile. ignores the first line and only returns the
            //games lost located on the second line of the profile
            Scanner in = readFile(username);
            int gamesWon = in.nextInt();
            gamesLost = in.nextInt();
            //close scanner because its no longer in use
            in.close();
        } catch (Exception e) {
            System.err.println("Player not Found");
        }
        return gamesLost;
    }

    /**
     * Get all of the user files.
     * @param fileDirectory the directory of where the text files are found.
     * @return textFiles An array of profile files found.
     */
    List<String> textFiles(String fileDirectory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(fileDirectory);
        //finds all .txt files and removes any that start with the word board
        //this is just in case a board file ends up in the player folder
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt")) && file.getName().startsWith("board") == false) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
    }

    /**
     * Runs the code.
     * @param args Used to run the code.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX scene.
     * @param stage The JavaFX stage.
     * @throws Exception The error that will be thrown if something goes wrong.
     */
    public void start(Stage stage) throws Exception {

        stage.setTitle("Profile Edit");
        stage.setWidth(650);
        stage.setHeight(500);

        VBox root = new VBox();

        Image image = new Image(new FileInputStream(DIRECTORY + "\\images\\background2.png"));
        BackgroundSize backgroundSize = new BackgroundSize(700, 550, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundPicture = new Background(backgroundImage);


        //makes the GUI for before a profile is selected
        MenuButton menuButton = refresh();
        usernameTxtbox.setText("Change Username");
        deleteBtn.setText("Delete Profile");
        deleteBtn.setVisible(false);
        updateUserBtn.setText("Save Username");
        updateUserBtn.setVisible(false);
        backBtn.setText("Back");
        backBtn.setVisible(true);

        //calls refresh every time the menuButton is clicked
        menuButton.setOnAction(e -> {
            refresh();
        });

        root.setBackground(backgroundPicture);
        root.getChildren().addAll(menuButton, gamesPlayedLbl, gamesWonLbl, gamesLostLbl, usernameTxtbox,
                updateUserBtn, deleteBtn, backBtn);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Refreshes the entire scene every time a new profile is selected.
     * It also refreshes when a profile is deleted or the profile name is changed.
     * @return menubutton The updated menubutton that has the profiles with any changes made to the profiles.
     */
    public MenuButton refresh() {

        //gets the profile names into a list
        List<String> fileNames;
        fileNames = textFiles(DIRECTORY + "\\players");
        MenuButton menuButton = new MenuButton("Profile Edit");

        //creates a menuItem for each profile
        for (int i = 0; i < fileNames.size(); i++) {
            MenuItem item1 = new MenuItem(fileNames.get(i).substring(0, fileNames.get(i).length() - 4));
            item1.setOnAction(e -> {
                //fills the GUI with information for the profile selected
                String user = item1.getText() + ".txt";
                System.out.println("user: " + user);
                location = fileNames.indexOf(user);
                int gamesPlayed = readGamesLost(user) + readGamesWon(user);
                gamesPlayedLbl.setText("games played: " + gamesPlayed);
                gamesWonLbl.setText("games won: " + readGamesWon(user));
                gamesLostLbl.setText("games lost: " + readGamesLost(user));
                usernameTxtbox.setText(fileNames.get(location).substring(0, fileNames.get(location).length() - 4));
                deleteBtn.setVisible(true);
                updateUserBtn.setVisible(true);
            });
            menuButton.getItems().add(item1);

            //when the delete button is clicked, the profile currently selected will be deleted
            deleteBtn.setOnAction(e -> {
                String user = fileNames.get(location);
                File fileToDelete = new File(user);
                //deletes the file and tells the user if it failed or not
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
                //removes the file from the menubutton and filenames list
                fileNames.remove(location);
                menuButton.getItems().remove(location);
                refresh();
            });

            updateUserBtn.setOnAction(e -> {
                String user = fileNames.get(location);
                File file = new File(user);
                //creates a new player from the profile file and then updates it with the new name
                Player player = new Player(user, readGamesWon(fileNames.get(location)), readGamesLost(fileNames.get(location)));
                player.updateUsername(usernameTxtbox.getText());
                //makes a new File object from the new name and renames the old file to the new one
                File newName = new File(usernameTxtbox.getText() + ".txt");
                MenuItem item2 = new MenuItem(usernameTxtbox.getText());
                //tells the user if the username updated or failed
                if (file.renameTo(newName)) {
                    System.out.println("Username updated successfully");
                } else {
                    System.out.println("Failed to rename user");
                }
                //updates the menubutton and filenames list
                menuButton.getItems().set(location, item2);
                fileNames.set(location, usernameTxtbox.getText() + ".txt");

                //fixes bug where user couldnt click go back on the profile after updating username
                item2.setOnAction(event -> {
                    String updatedUser = item2.getText() + ".txt";
                    int gamesPlayed = readGamesLost(updatedUser) + readGamesWon(updatedUser);
                    gamesPlayedLbl.setText("games played: " + gamesPlayed);
                    gamesWonLbl.setText("games won: " + readGamesWon(updatedUser));
                    gamesLostLbl.setText("games lost: " + readGamesLost(updatedUser));
                    usernameTxtbox.setText(updatedUser.substring(0, updatedUser.length() - 4));
                });
            });

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
        }
        return menuButton;
    }
}