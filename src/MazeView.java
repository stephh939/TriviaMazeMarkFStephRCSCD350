import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

// This is where we make everything that is show
// Here we don't care about the data

public class MazeView extends BorderPane {

    private Maze maze;
    private File currentFile;
    private Stage stage;

    public MazeView(Stage primaryStage) {
        MenuBar menuBar = createMenuBar();
        maze = new Maze(5, 5);
        ToolBar toolBar = createToolBar();
        setTop(menuBar);
        setCenter(maze);
        setBottom(toolBar);
        stage = primaryStage;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(
                createFileMenu(),
                createOptionsMenu(),
                createAdminMenu()
        );

        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> onNew());
        MenuItem saveGame = new MenuItem("Save");
        saveGame.setOnAction(event -> onSave());
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(event -> onSave());
        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> onOpen());
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> System.exit(0));

        fileMenu.getItems().addAll(newGame, saveGame, saveAs, open, quit);

        return fileMenu;
    }

    private void onNew() {
    }

    private void onSave() {
        File selectedFile = currentFile ;
        if (currentFile == null ) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Trivia Maze Files", "*.xml"),
                    new FileChooser.ExtensionFilter("All Files", "*.xml"));
            if (currentFile != null)
                fileChooser.setInitialFileName(currentFile.getName());
            selectedFile = fileChooser.showSaveDialog(stage);
        }
        if (selectedFile != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(selectedFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                //objectOutputStream.writeObject(game);

            } catch (Exception ex) {
                System.out.println("ID10T ERROR: " + ex);
                return ;
            }
            currentFile = selectedFile ;
            stage.setTitle(currentFile.getName());
        }
    }

    private void onOpen() {
        FileChooser chooser = new FileChooser() ;
        chooser.setTitle("Open an .xml File");
        chooser.setInitialDirectory(new File("."));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
        File selectedFile = chooser.showOpenDialog(stage);
        if (selectedFile == null) return ;
        try {
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //game = (Game) objectInputStream.readObject();
        }
        catch (IOException ex) {
            System.out.print("Error: " + ex);
            return ;
        }

        currentFile = selectedFile ;
        stage.setTitle(currentFile.getName()) ;
    }

    private Menu createOptionsMenu() {
        Menu optionMenu = new Menu("Options");

        ToggleGroup group = new ToggleGroup();
        Menu changeCharacter = new Menu("Change Character");
        RadioMenuItem android = new RadioMenuItem("Android");
        android.setOnAction(event -> maze.changeGameCharacter("Android"));
        android.setToggleGroup(group);
        android.setSelected(true);
        RadioMenuItem apple = new RadioMenuItem("Apple");
        apple.setOnAction(event -> maze.changeGameCharacter("Apple"));
        apple.setToggleGroup(group);
        changeCharacter.getItems().addAll(android, apple);

        optionMenu.getItems().addAll(changeCharacter);

        return optionMenu;
    }

    private Menu createAdminMenu() {
        Menu admin = new Menu("Admin");
        admin.setOnAction(event -> onAdmin());
        return admin;
    }

    private void onAdmin() {

    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Label score = new Label("Unlocked doors: 4    Sealed doors: 2");

        Button fix = new Button("Fix");
        Button tnt = new Button("TNT");

        toolBar.getItems().addAll(score, fix, tnt);

        return toolBar;
    }

    protected Maze getMaze() {
        return maze;
    }

}