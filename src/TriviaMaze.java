import javafx.application.Application;
import javafx.stage.Stage;

public class TriviaMaze extends Application {

    public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        MazeController controller = new MazeController();
        controller.buildView(primaryStage);
    }
  
}


