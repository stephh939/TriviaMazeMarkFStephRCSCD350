

// Takes the actions from the view and translates that to the Model to do business work
// Then the controller tells the view what to do based on the business logic result
import com.sun.javafx.scene.traversal.SceneTraversalEngine;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MazeController {

    private MazeModel model;
    private MazeView view;

    public MazeController() {
        model = new MazeModel();
    }

    public void buildView(Stage primaryStage) {
        primaryStage.getIcons().add(new Image("ApplicationImage.PNG"));
        primaryStage.setTitle("Trivia Maze");
        MazeView root = new MazeView();
        view = root;
        Scene scene = new Scene(root, 800, 800);
        root.getScene().setOnKeyPressed(keyEvent -> onKeyPressed(keyEvent));
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case UP :
                view.moveUp();
                break;
            case DOWN :
                view.moveDown();
                break;
            case RIGHT :
                view.moveRight();
                break;
            case LEFT :
                view.moveLeft();
                break;
        }
    }
}
