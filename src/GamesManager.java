import DialogBoxes.InputDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;

public class GamesManager implements Serializable {

    private GamePickerTable gameFiles;
    private File currentFile;
    private Stage newWindow;
    private String workingDirectoryForGameFiles;
    protected Object[] gameData;

    public GamesManager() {
        gameFiles = new GamePickerTable();
        newWindow = new Stage();
        workingDirectoryForGameFiles = System.getProperty("user.dir") + "/src/GameFiles/";
        fillTable();
    }

    protected void saveGame(Integer [][] questionsID, int xLoc, int yLoc, String character, String[][] wallsImages, String[][] walls, boolean[] fixAndTntButtons) {
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
            selectedFile = newFile;
        }
        if (selectedFile != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(currentFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(questionsID);
                objectOutputStream.writeInt(xLoc);
                objectOutputStream.writeInt(yLoc);
                objectOutputStream.writeObject(character);
                objectOutputStream.writeObject(wallsImages);
                objectOutputStream.writeObject(walls);
                objectOutputStream.writeObject(fixAndTntButtons);
            } catch (Exception ex) {
                System.out.println("ID10T ERROR: " + ex);
                return;
            }
            currentFile = selectedFile;
        }
    }

    protected void onOpen() {
        String gameName = ((GameName) gameFiles.getSelectionModel().getSelectedItem()).getGameName();
        gameName = workingDirectoryForGameFiles + gameName + ".txt";

        File game = new File(gameName);
        Object[] gameData = new Object[7];
        try {
            FileInputStream fileInputStream = new FileInputStream(game);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Integer[][] questionIds = (Integer[][]) objectInputStream.readObject();
            int xLoc = objectInputStream.readInt();
            int yLoc = objectInputStream.readInt();
            String character = (String) objectInputStream.readObject();
            String [][] wallsImages = (String[][]) objectInputStream.readObject();
            String[][] walls = (String[][]) objectInputStream.readObject();
            boolean[] fixAndTntButtons = (boolean[]) objectInputStream.readObject();
            gameData = new Object[]{questionIds, xLoc, yLoc, character, wallsImages, walls, fixAndTntButtons};
        }
        catch(IOException | ClassNotFoundException ex){
            System.out.print("Error: " + ex);
        }

        currentFile = game;
        this.gameData = gameData;
        newWindow.close();
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

        open.setOnAction(event -> onOpen());
        newWindow.setTitle("Open Game");
        newWindow.setScene(secondScene);
        newWindow.getIcons().add(new Image("Images/ApplicationImage.PNG"));
        newWindow.showAndWait();
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