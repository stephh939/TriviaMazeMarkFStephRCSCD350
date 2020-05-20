import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

// Takes the actions from the view and translates that to the Model to do business work
// Then the controller tells the view what to do based on the business logic result

public class MazeController {

    private MazeModel model;
    private MazeView view;
    private AdminView adminView;
    private Scene mainScene, adminScene;
    private Stage stage;

    public MazeController() {
    	ArrayList<String> Q = model.getQuestion();
    	ArrayList<String> A = model.getAnswer();
    	ArrayList<String> I = model.getID();
        model = new MazeModel(Q,A,I);
    }

    public void buildView(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.getIcons().add(new Image("Images/ApplicationImage.PNG"));
        primaryStage.setTitle("Trivia Maze");

        // Main scene
        view = new MazeView(primaryStage);
        mainScene = new Scene(view, 800, 800);
        mainScene.setOnKeyPressed(keyEvent -> onKeyPressed(keyEvent));
        view.admin.setOnAction(event -> onAdmin());

        // Admin scene
        try {
            adminView = new AdminView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adminScene = new Scene(adminView, 800, 800);

        adminView.getClose().setOnAction(click -> onClose());

        primaryStage.setResizable(false);
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    private void onAdmin() {
        stage.setScene(adminScene);
    }

    private void onClose() {
        stage.setScene(mainScene);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Maze maze = view.getMaze();
        switch (keyEvent.getCode()){
            case UP :
                // check if there is another room in that direction
                if (maze.canMoveUp()) {
                    boolean correctAnswer = true;
                    // check to see if door to next room is unlocked if not prompt with question.
                    if (!maze.upDoorUnlocked()) {
                        // Prompt user for a response to question
                        String answer = maze.showWallDialog();
                        System.out.print(answer);
                        if (answer.equals("correct")) {
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc, maze.yLoc - 1);
                        }
                        //else if (answer.compareTo())
                        // if user gets it wrong seal door
                        else {
                            maze.sealDoor(maze.xLoc, maze.yLoc - 1);
                            correctAnswer = false;
                        }
                    }
                    // if unlocked or user got correct answer then move character
                    view.getMaze().moveUp(correctAnswer);
                }
                break;
            case DOWN :
                // check if there is another room in that direction
                if (maze.canMoveDown()) {
                    boolean correctAnswer = true;
                    // check to see if door to next room is unlocked if not prompt with question.
                    if (!maze.downDoorUnlocked()) {
                        // Prompt user for a response to question
                        String answer = maze.showWallDialog();
                        if (answer.equals("correct")) {
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc, maze.yLoc + 1);
                        }
                        // if user gets it wrong seal door
                        else {
                            maze.sealDoor(maze.xLoc, maze.yLoc + 1);
                            correctAnswer = false;
                        }
                    }
                    // if unlocked or user got correct answer then move character
                    view.getMaze().moveDown(correctAnswer);
                }
                break;
            case RIGHT :
                // check if there is another room in that direction
                if (maze.canMoveRight()) {
                    boolean correctAnswer = true;
                    // check to see if door to next room is unlocked if not prompt with question.
                    if (!maze.rightDoorUnlocked()) {
                        // Prompt user for a response to question
                        String answer = maze.showWallDialog();
                        if (answer.equals("correct")) {
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc + 1, maze.yLoc);
                        }
                        // if user gets it wrong seal door
                        else {
                            maze.sealDoor(maze.xLoc + 1, maze.yLoc);
                            correctAnswer = false;
                        }
                    }
                    // if unlocked or user got correct answer then move character
                    view.getMaze().moveRight(correctAnswer);
                }
                break;
            case LEFT :
                // check if there is another room in that direction
                if (maze.canMoveLeft()) {
                    boolean correctAnswer = true;
                    // check to see if door to next room is unlocked if not prompt with question.
                    if (!maze.leftDoorUnlocked()) {
                        // Prompt user for a response to question
                        String answer = maze.showWallDialog();
                        if (answer.equals("correct")) {
                            correctAnswer = true;
                            maze.setWallToUnlocked(maze.xLoc - 1, maze.yLoc);
                        }
                        // if user gets it wrong seal door
                        else {
                            maze.sealDoor(maze.xLoc - 1, maze.yLoc);
                            correctAnswer = false;
                        }
                    }
                    // if unlocked or user got correct answer then move character
                    view.getMaze().moveLeft(correctAnswer);
                }
                break;
        }
    }

}
