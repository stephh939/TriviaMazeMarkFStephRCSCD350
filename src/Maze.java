import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Maze extends GridPane {

    private ImageView character;

    public Maze(int width, int height) {
        createBoard(width, height);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        character = new ImageView(new Image("Images/character.png"));
    }

    protected void createBoard(int width, int height) {
        int columnsAmount = width * 2;
        int rowsAmount = height * 2 - 2;
        // add blank spaces
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView blank = new ImageView(new Image("Images/blank.png"));
                blank.setUserData("BLANK");
                this.add(blank, column, rows, 1,1);
            }
        }

        // add dividers horizontally
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 1; rows < rowsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/horiz.png"));
                this.add(spacer, column, rows, 1,1);
            }
        }
        // add dividers vertically
        for (int column = 1; column < rowsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/vert.png"));
                this.add(spacer, column, rows, 1,1);
            }
        }
    }

}
