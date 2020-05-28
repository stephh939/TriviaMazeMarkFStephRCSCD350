import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TriviaMaze extends Application {

    public static void main(String[] args) throws FileNotFoundException {
    	PrintStream ans = new PrintStream(new File("Answers.txt"));
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        MazeController controller = new MazeController();
        controller.buildView(primaryStage);
    }
  
}


