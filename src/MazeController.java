

// Takes the actions from the view and translates that to the Model to do business work
// Then the controller tells the view what to do based on the business logic result

public class MazeController {

    private MazeView screen;
    private MazeModel logic;

    public MazeController(MazeView screen, MazeModel logic) {
        this.screen = screen;
        this.logic = logic;
    }
}
