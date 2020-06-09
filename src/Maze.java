import DialogBoxes.ErrorDialog;
import DialogBoxes.InputDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Optional;

public class Maze extends GridPane {

    private ImageView [][] images;
    protected int xLoc, yLoc;
    protected String character;
    private ButtonType tnt, fix;
    private boolean tntIsUsed, fixIsUsed;

    protected Maze(int width, int height) {
        tnt = new ButtonType("TNT");
        fix = new ButtonType("Fix");

        character = "Android";
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
                blank.setUserData("room");
                add(blank, column, rows, 1,1);
                images[column][rows] = blank;
            }
        }

        // add dividers horizontally
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 1; rows < rowsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/horiz.png"));
                spacer.setUserData("horizLocked");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
            }
        }

        // add dividers vertically
        for (int column = 1; column < rowsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/vert.png"));
                spacer.setUserData("vertLocked");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
            }
        }

        ImageView character = images[0][0];
        character.setImage(new Image("Images/" + this.character + ".png"));
        xLoc = 0; yLoc = 0;

        ImageView exit = images[columnsAmount-2][rowsAmount];
        exit.setImage(new Image("Images/exit.png"));
    }

    protected String showWallDialog(String question){
        InputDialog questionDialog = new InputDialog(question);
        return questionDialog.getAnswer().toLowerCase();
    }

    protected void sealDoor(int x, int y) {
        ImageView lockedDoor = images[x][y];
        if (lockedDoor.getUserData().equals("vertLocked")) {
            lockedDoor.setImage(new Image("Images/sealedVert.jpg"));
            lockedDoor.setUserData("sealed");
        }
        else if (lockedDoor.getUserData().equals("horizLocked")) {
            lockedDoor.setImage(new Image("Images/sealedHoriz.jpg"));
            lockedDoor.setUserData("sealed");
        }
        else {
            System.out.println("Wrong node index passed in sealDoor method");
        }
    }

    protected void setWallToUnlocked(int x, int y) {
        ImageView wall = images[x][y];
        if (wall.getUserData().equals("horizLocked")) {
            wall.setImage(new Image("Images/unlockedHoriz.png"));
        }
        else if (wall.getUserData().equals("vertLocked")){
            wall.setImage(new Image("Images/unlockedVert.png"));
        }
        wall.setUserData("unlocked");
    }

    protected void changeGameCharacter(String character) {
        this.character = character;
        images[xLoc][yLoc].setImage(new Image("Images/" + character + ".png"));
    }

    protected void moveRight(boolean correctAnswer) {
        if (correctAnswer) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc + 2][yLoc].setImage(new Image("Images/" + character + ".png"));
            xLoc += 2;
        }
    }

    protected void moveLeft(boolean correctAnswer) {
        if (correctAnswer) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc - 2][yLoc].setImage(new Image("Images/" + character + ".png"));
            xLoc -= 2;
        }
    }

    protected void moveDown(boolean correctAnswer) {
        if (correctAnswer) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc][yLoc + 2].setImage(new Image("Images/" + character + ".png"));
            yLoc += 2;
        }
    }

    protected void moveUp(boolean correctAnswer) {
        if (correctAnswer) {
            images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
            images[xLoc][yLoc - 2].setImage(new Image("Images/" + character + ".png"));
            yLoc -= 2;
        }
    }

    protected boolean canMoveUp() {
        if (yLoc - 2 > -1) {
            ImageView wall = images[xLoc][yLoc - 1];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed", tnt, fix);
                handleButtonClick(message.showAndWait());
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveDown() {
        if (yLoc + 2 < images.length) {
            ImageView wall = images[xLoc][yLoc + 1];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed", tnt, fix);
                handleButtonClick(message.showAndWait());
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveRight() {
        if (xLoc + 2 < images.length) {
            ImageView wall = images[xLoc + 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed", tnt, fix);
                handleButtonClick(message.showAndWait());
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveLeft() {
        if (xLoc - 2 > -1) {
            ImageView wall = images[xLoc - 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed", tnt, fix);
                handleButtonClick(message.showAndWait());
                return false;
            }
            return true;
        }
        return false;
    }

    private void handleButtonClick(Optional<ButtonType> result) {
        if (result.get() == tnt){
            //TODO: Set door to unlocked
        } else if (result.get() == fix) {
            // TODO: Give the user a second chance
        }
    }

    protected boolean upDoorUnlocked() {
        if (yLoc - 2 > -1) {
            ImageView wall = images[xLoc][yLoc - 1];
            return wall.getUserData().equals("unlocked");
        }
        return false;
    }

    protected boolean downDoorUnlocked() {
        if (yLoc + 2 < images.length) {
            ImageView wall = images[xLoc][yLoc + 1];
            return wall.getUserData().equals("unlocked");
        }
        return false;
    }

    protected boolean rightDoorUnlocked() {
        if (xLoc + 2 < images.length) {
            ImageView wall = images[xLoc + 1][yLoc];
            return wall.getUserData().equals("unlocked");
        }
        return false;
    }

    protected boolean leftDoorUnlocked() {
        if (xLoc - 2 > -1) {
            ImageView wall = images[xLoc - 1][yLoc];
            return wall.getUserData().equals("unlocked");
        }
        return false;
    }

    protected void checkIfStuck() {
        // TODO: Check if all the rooms around them are locked
    }

    protected void endGame() {
        // TODO: end the game
    }

    public void refreshCharacterLocation() {
        images[xLoc][yLoc].setImage(new Image("Images/" + character + ".png"));
    }

    public void clearCharacter() {
        images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
    }
}
