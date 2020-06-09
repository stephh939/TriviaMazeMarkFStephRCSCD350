import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class MazeView extends BorderPane {

    private Maze maze;
    protected Button admin;
    protected MenuItem newGame, saveGame, open;

    public MazeView() {
        MenuBar menuBar = createMenuBar();
        maze = new Maze(5, 5);
        HBox toolBar = createToolBar();
        setTop(menuBar);
        setCenter(maze);
        setBottom(toolBar);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(
                createFileMenu(),
                createOptionsMenu()
        );

        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");
        newGame = new MenuItem("New Game");
        saveGame = new MenuItem("Save");
        open = new MenuItem("Open");
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> System.exit(0));

        fileMenu.getItems().addAll(newGame, saveGame, open, quit);

        return fileMenu;
    }

    protected void createNewMaze() {
        maze = new Maze(5,5);
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

    private HBox createToolBar() {
        admin = new Button("Admin");
        admin.setPadding(new Insets(8,16,8,16));

        HBox toolBar = new HBox(admin);
        toolBar.setSpacing(8);
        toolBar.setPadding(new Insets(8));

        return toolBar;
    }

    protected Maze getMaze() {
        return maze;
    }

    // TODO: when the user gets to the exit end the game
    // User would be in xLoc = 4 and yLoc = 4
    // Put the character back to 0,0

}