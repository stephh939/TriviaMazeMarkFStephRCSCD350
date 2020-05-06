import javafx.stage.Stage;

public class Main {

    public static void main(String[] args) {

        MazeView view = new MazeView();
        MazeModel logic = new MazeModel();

        MazeController controller = new MazeController(view, logic);

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


