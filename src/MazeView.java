import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;

// Testing help / Cheat:
// xDimension and yDimension are the size of the maze
// Used this to make a 2X2 for quick testing of...
// 1. Getting trapped and ending the game
// 2. Save, Open, New
// 3. Exit

public class MazeView extends BorderPane {

    private Maze maze;
    protected Button admin;
    protected MenuItem newGame, saveGame, open;
    protected final int xDimension = 2, yDimension = 2;

    public MazeView() {
        MenuBar menuBar = createMenuBar();
        maze = new Maze(xDimension, yDimension);
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
        maze.createBoard(xDimension,yDimension);
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

        Button howTo = new Button("How-To");
        howTo.setPadding(new Insets(8,16,8,16));
        howTo.setOnAction(event -> onHowTo());

        HBox toolBar = new HBox(admin, howTo);
        toolBar.setSpacing(8);
        toolBar.setPadding(new Insets(8));

        return toolBar;
    }

    private void onHowTo() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(System.getProperty("user.dir") + "/Trivia Maze How-To.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    protected Maze getMaze() {
        return maze;
    }

}