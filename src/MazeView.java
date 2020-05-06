import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// This is where we make everything that is show
// Here we don't care about the data

public class MazeView  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image("ApplicationImage.PNG"));
        primaryStage.setTitle("Trivia Maze");
        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenuBar();
        Maze maze = new Maze(4,4);
        ToolBar toolBar = createToolBar();

        root.setTop(menuBar);
        root.setCenter(maze);
        root.setBottom(toolBar);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
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
        MenuItem saveGame = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem open = new MenuItem("Open");
        MenuItem quit = new MenuItem("Quit");

        fileMenu.getItems().addAll(newGame, saveGame, saveAs, open, quit);

        return fileMenu;
    }

    private Menu createOptionsMenu() {
        Menu optionMenu = new Menu("Options");
        MenuItem changeGenre = new MenuItem("Change Genre");
        MenuItem changeCharacter = new MenuItem("Change Character");
        MenuItem changeMazeSize = new MenuItem("Change Maze Size");

        optionMenu.getItems().addAll(changeGenre, changeCharacter, changeMazeSize);

        return optionMenu;
    }

    private Menu createAdminMenu() {
        Menu admin = new Menu("Admin");

        return admin;
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Label score = new Label("Unlocked doors: 4    Sealed doors: 2");

        Button fix = new Button("Fix");
        Button tnt = new Button("TNT");

        toolBar.getItems().addAll(score, fix, tnt);

        return toolBar;
    }
}
