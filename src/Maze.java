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

    private ImageView [][] images;
    protected int xLoc, yLoc;

    public Maze(int width, int height) {
        createBoard(width, height);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    protected void createBoard(int width, int height) {
        int columnsAmount = width * 2;
        int rowsAmount = height * 2 - 2;
        images = new ImageView[columnsAmount-1][rowsAmount+1];
        // add blank spaces
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView blank = new ImageView(new Image("Images/blank.png"));
                blank.setUserData("BLANK");
                add(blank, column, rows, 1,1);
                images[column][rows] = blank;
            }
        }

        // add dividers horizontally
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 1; rows < rowsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/horiz.png"));
                spacer.setUserData("horiz");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
            }
        }

        // add dividers vertically
        for (int column = 1; column < rowsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/vert.png"));
                spacer.setUserData("vert");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
            }
        }

        ImageView character = images[0][0];
        character.setImage(new Image("Images/character.png"));
        xLoc = 0; yLoc = 0;

        ImageView exit = images[columnsAmount-2][rowsAmount];
        exit.setImage(new Image("Images/exit.png"));
    }

    protected void sealDoor(int x, int y) {
        ImageView lockedDoor = images[x][y];
        if (lockedDoor.getUserData().equals("vert")) {
            lockedDoor.setImage(new Image("Images/sealedVert.jpg"));
            lockedDoor.setUserData("sealed");
        }
        else if (lockedDoor.getUserData().equals("horiz")) {
            lockedDoor.setImage(new Image("Images/sealedHoriz.jpg"));
            lockedDoor.setUserData("sealed");
        }
        else {
            System.out.println("Wrong node index passed in sealDoor method");
        }
    }

    protected void moveRight() {
        if (xLoc + 2 < (images[xLoc].length)) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc + 2][yLoc].setImage(new Image("Images/character.png"));
            xLoc += 2;
        }
    }

    protected void moveLeft() {
        if (xLoc - 2 > -1) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc - 2][yLoc].setImage(new Image("Images/character.png"));
            xLoc -= 2;
        }
    }

    protected void moveDown() {
        if (yLoc + 2 < (images[yLoc].length)) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc][yLoc + 2].setImage(new Image("Images/character.png"));
            yLoc += 2;
        }
    }

    protected void moveUp() {
        if (yLoc - 2 > -1) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc][yLoc - 2].setImage(new Image("Images/character.png"));
            yLoc -= 2;
        }
    }

}
