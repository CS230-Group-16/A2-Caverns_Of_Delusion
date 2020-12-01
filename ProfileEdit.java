import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileEdit extends Application {
    //Change directory to personal one
    private final String DIRECTORY = "E:/Users/Jimmy/Documents/Uni Work/YEAR 2/CS-230/230 code copy";
    Label gamesPlayedLbl = new Label("no player selected");
    Label gamesWonLbl = new Label();
    Label gamesLostLbl = new Label();
    Button deleteBtn = new Button();
    Button updateUserBtn = new Button();
    TextField usernameTxtbox = new TextField();
    Scanner in = new Scanner(System.in);
    int location = 0;

    public Scanner readFile(String filename) {
        File file = new File(filename);
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in directory");
            e.printStackTrace();
            System.exit(0);
        }
        return in;
    }

    public int readGamesWon(String username) {
        int gamesWon = 0;

        try {
            Scanner in = readFile(username);
            gamesWon = in.nextInt();
            in.close();
        }catch(Exception e){
            System.err.println("Player not Found");
        }

        return gamesWon;
    }

    public int readGamesLost(String username) {
        int gamesLost = 0;

        try {
            Scanner in = readFile(username);
            int gamesWon = in.nextInt();
            gamesLost = in.nextInt();
            in.close();
        }catch(Exception e){
            System.err.println("Player not Found");
        }
        return gamesLost;
    }


    List<String> textFiles(String directory) {
        List<String> textFiles = new ArrayList<String>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt")) && file.getName().startsWith("board") == false) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        stage.setWidth(400);
        stage.setHeight(500);

        VBox root = new VBox();

        MenuButton menuButton = refresh();
        usernameTxtbox.setText("Change Username");
        deleteBtn.setText("Delete Profile");
        deleteBtn.setVisible(false);
        updateUserBtn.setText("Save Username");
        updateUserBtn.setVisible(false);

        menuButton.setOnAction(e -> {
            refresh();
        });

        root.getChildren().addAll(menuButton, gamesPlayedLbl, gamesWonLbl, gamesLostLbl, usernameTxtbox,
                updateUserBtn, deleteBtn);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public MenuButton refresh() {

        List<String> fileNames;
        fileNames = textFiles(DIRECTORY);
        MenuButton menuButton = new MenuButton("PlayerEdit");

        for (int i = 0; i < fileNames.size(); i++) {
            MenuItem item1 = new MenuItem(fileNames.get(i).substring(0, fileNames.get(i).length() - 4));
            int finalI = i;
            item1.setOnAction(e -> {
                String user = item1.getText() + ".txt";
                location = fileNames.indexOf(user);
                System.out.println(fileNames);
                System.out.println(menuButton.getItems());
                int gamesPlayed = readGamesLost(user) + readGamesWon(user);
                gamesPlayedLbl.setText("games played: " + gamesPlayed);
                gamesWonLbl.setText("games won: " + readGamesWon(user));
                gamesLostLbl.setText("games lost: " + readGamesLost(user));
                usernameTxtbox.setText(fileNames.get(location).substring(0, fileNames.get(location).length() - 4));
                deleteBtn.setVisible(true);
                updateUserBtn.setVisible(true);
            });

            deleteBtn.setOnAction(e -> {
                in.close();
                String user = fileNames.get(location);
                readFile(user).close();
                File fileToDelete = new File(user);
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
                MenuItem item2 = new MenuItem("deleted value");
                fileNames.remove(location);
                menuButton.getItems().remove(location);
                //menuButton.getItems().set(location, item2);
                refresh();
            });

            updateUserBtn.setOnAction(e -> {
                String user = fileNames.get(location);
                readFile(user).close();
                File file = new File(user);
                Player player = new Player(user, readGamesWon(fileNames.get(location)), readGamesLost(fileNames.get(location)));
                player.updateUsername(usernameTxtbox.getText());
                File newName = new File(usernameTxtbox.getText() + ".txt");
                MenuItem item2 = new MenuItem(usernameTxtbox.getText() + ".txt");
                if (file.renameTo(newName)) {
                    System.out.println("Username updated successfully");
                } else {
                    System.out.println("Failed to rename user");
                }
                menuButton.getItems().set(location, item2);
                refresh();
            });
            menuButton.getItems().add(item1);
        }
        return menuButton;
    }
}