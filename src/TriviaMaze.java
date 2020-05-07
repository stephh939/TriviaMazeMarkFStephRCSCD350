import javafx.application.Application;
import javafx.stage.Stage;

public class TriviaMaze extends Application {

    MazeController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new MazeController();
        controller.buildView(primaryStage);
    }
}


