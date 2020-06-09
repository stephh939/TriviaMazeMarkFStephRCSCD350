import DialogBoxes.InputDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;

public class GamesManager {

    private GamePickerTable gameFiles;
    private File currentFile;
    private Stage newWindow;
    private String workingDirectoryForGameFiles;

    public GamesManager() {
        gameFiles = new GamePickerTable();
        newWindow = new Stage();
        workingDirectoryForGameFiles = System.getProperty("user.dir") + "/src/GameFiles/";
        fillTable();
    }

    protected void saveGame(String [][] questionsID, int xLoc, int yLoc, String character) {
        File selectedFile = currentFile;
        File newFile;
        if (currentFile == null) {
            String fileName = getFileName();
            newFile = new File( workingDirectoryForGameFiles  + fileName + ".txt");
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameFiles.getItems().add(new GameName(fileName));
            currentFile = newFile;
        }
        if (selectedFile != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(selectedFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(questionsID);
                objectOutputStream.writeObject(xLoc);
                objectOutputStream.writeObject(yLoc);
                objectOutputStream.writeObject(character);

            } catch (Exception ex) {
                System.out.println("ID10T ERROR: " + ex);
                return;
            }
            currentFile = selectedFile;
        }
    }

    protected Object[] onOpen() {
        String gameName = ((GameName) gameFiles.getSelectionModel().getSelectedItem()).getGameName();
        gameName = workingDirectoryForGameFiles + gameName + ".txt";
        newWindow.close();

        File game = new File(gameName);
        Object[] gameData = new Object[4];
        try {
            FileInputStream fileInputStream = new FileInputStream(game);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String[][] questionIds = (String[][]) objectInputStream.readObject();
            int xLoc = (int) objectInputStream.readObject();
            int yLoc = (int) objectInputStream.readObject();
            String character = (String) objectInputStream.readObject();
            gameData = new Object[]{questionIds, xLoc, yLoc, character};
        }
        catch(IOException | ClassNotFoundException ex){
                System.out.print("Error: " + ex);
            }

            currentFile = game;
            return gameData;
    }

    public void fileChooser() {
        Button open = new Button("Open");
        open.setPadding(new Insets(8, 16, 8, 16));
        HBox hBox = new HBox(open);
        hBox.setPadding(new Insets(8));
        BorderPane secondaryLayout = new BorderPane();
        secondaryLayout.setTop(createTitle());
        secondaryLayout.setCenter(gameFiles);
        secondaryLayout.setBottom(hBox);

        Scene secondScene = new Scene(secondaryLayout, 250, 300);

        newWindow.setTitle("Open Game");
        newWindow.setScene(secondScene);
        newWindow.getIcons().add(new Image("Images/ApplicationImage.PNG"));
        newWindow.show();

        open.setOnAction(event -> onOpen());
    }

    private String getFileName() {
        InputDialog fileName = new InputDialog("Enter the name of the game");

        return fileName.getAnswer().trim();
    }

    private void fillTable() {
        String [] files = new File(workingDirectoryForGameFiles).list();
        for (String file: files) {
            gameFiles.getItems().add(new GameName(file.substring(0, file.length() - 4)));
        }
    }

    private Label createTitle() {
        Label title = new Label();
        title.setText("Select Game");
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font ("Verdana", 30));
        title.setPadding(new Insets(16));

        return title;
    }

    public void onNew() {
        currentFile = null;
    }

}