import DialogBoxes.InputDialog;
import DialogBoxes.WarningDialog;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.File;
import java.sql.SQLException;

public class MazeController {

    private MazeView view;
    private Scene mainScene, adminScene;
    private Stage stage;
    private MazeConnection connection;
    private GamesManager gamesManager;

    protected void buildView(Stage primaryStage) throws SQLException {
        gamesManager = new GamesManager();
        connection = new MazeConnection();
        stage = primaryStage;
        primaryStage.getIcons().add(new Image("Images/ApplicationImage.PNG"));
        primaryStage.setTitle("Trivia Maze");

        view = new MazeView(primaryStage);
        mainScene = new Scene(view, 800, 800);
        mainScene.setOnKeyPressed(event -> {
			try {
				onKeyPressed(event);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
        view.admin.setOnAction(event -> onAdmin());

        AdminView adminView = new AdminView();
        adminScene = new Scene(adminView, 800, 800);

        adminView.getClose().setOnAction(click -> onClose());
        view.newGame.setOnAction(event -> onNew());
        view.saveGame.setOnAction(event -> onSave());
        view.open.setOnAction(event -> onOpen());

        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void onOpen() {
        gamesManager.fileChooser();
        Object[] gameData = gamesManager.onOpen();
        connection.setQuestionsIDs((String[][]) gameData[0]);
        view.getMaze().clearCharacter();
        view.getMaze().xLoc = (int) gameData[1];
        view.getMaze().yLoc = (int) gameData[2];
        view.getMaze().character = (String) gameData[3];
        view.getMaze().refreshCharacterLocation();
    }

    private void onSave() {
        String [][] questionsIds = connection.getQuestionsIDs();
        int xLoc = view.getMaze().xLoc;
        int yLoc = view.getMaze().yLoc;
        String character = view.getMaze().character;
        gamesManager.saveGame(questionsIds, xLoc, yLoc, character);
    }

    private void onNew() {
        view.createNewMaze();
        connection.fillQuestionsIdArray();
        gamesManager.onNew();
    }

    private void onAdmin() {
        InputDialog login = new InputDialog("Enter the admin password");
        login.setTitle("Admin Login");
        if (login.getAnswer().equals("root")) {
            stage.setScene(adminScene);
        }
        else {
            WarningDialog wrongPassword = new WarningDialog("You entered the incorrect password");
            wrongPassword.showAndWait();
        }
    }

    private void onClose() {
        stage.setScene(mainScene);
    }

    private void onKeyPressed(KeyEvent keyEvent) throws SQLException {
        Maze maze = view.getMaze();
        switch (keyEvent.getCode()){
            case UP :
                if (maze.canMoveUp()) {
                    boolean correctAnswer = true;
                    if (!maze.upDoorUnlocked()) {
                        String question = connection.getQuestion(maze.xLoc - 1, maze.yLoc);
                        String answer = maze.showWallDialog(question);
                        boolean isCorrect = connection.checkAnswer(maze.xLoc - 1, maze.yLoc, answer);
                        if (isCorrect) {
                            AudioPlayer.playCorrectSound();
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc, maze.yLoc - 1);
                        }
                        else {
                            AudioPlayer.playWrongSound();
                            maze.sealDoor(maze.xLoc, maze.yLoc - 1);
                            correctAnswer = false;
                        }
                    }
                    view.getMaze().moveUp(correctAnswer);
                }
                break;

            case DOWN :
                if (maze.canMoveDown()) {
                    boolean correctAnswer = true;
                    if (!maze.downDoorUnlocked()) {
                        String question = connection.getQuestion(maze.xLoc, maze.yLoc + 1);
                        String answer = maze.showWallDialog(question);
                        boolean isCorrect = connection.checkAnswer(maze.xLoc, maze.yLoc + 1, answer);
                        if (isCorrect) {
                            AudioPlayer.playCorrectSound();
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc, maze.yLoc + 1);
                        }
                        else {
                            AudioPlayer.playWrongSound();
                            maze.sealDoor(maze.xLoc, maze.yLoc + 1);
                            correctAnswer = false;
                        }
                    }
                    view.getMaze().moveDown(correctAnswer);
                }
                break;

            case RIGHT :
                if (maze.canMoveRight()) {
                    boolean correctAnswer = true;
                    if (!maze.rightDoorUnlocked()) {
                        String question = connection.getQuestion(maze.xLoc + 1, maze.yLoc);
                        String answer = maze.showWallDialog(question);
                        boolean isCorrect = connection.checkAnswer(maze.xLoc + 1, maze.yLoc, answer);
                        if (isCorrect) {
                            AudioPlayer.playCorrectSound();
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc + 1, maze.yLoc);
                        }
                        else {
                            AudioPlayer.playWrongSound();
                            maze.sealDoor(maze.xLoc + 1, maze.yLoc);
                            correctAnswer = false;
                        }
                    }
                    view.getMaze().moveRight(correctAnswer);
                }
                break;

            case LEFT :
                if (maze.canMoveLeft()) {
                    boolean correctAnswer = true;
                    if (!maze.leftDoorUnlocked()) {
                        String question = connection.getQuestion(maze.xLoc - 1, maze.yLoc);
                        String answer = maze.showWallDialog(question);
                        boolean isCorrect = connection.checkAnswer(maze.xLoc - 1, maze.yLoc, answer);
                        if (isCorrect) {
                            AudioPlayer.playCorrectSound();
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc - 1, maze.yLoc);
                        }
                        else {
                            AudioPlayer.playWrongSound();
                            maze.sealDoor(maze.xLoc - 1, maze.yLoc);
                            correctAnswer = false;
                        }
                    }
                    view.getMaze().moveLeft(correctAnswer);
                }
                break;
        }
    }

}
