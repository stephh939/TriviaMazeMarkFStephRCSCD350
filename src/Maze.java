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
    private String [][] wallImages;
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
        wallImages = new String[columnsAmount-1][rowsAmount+1];
        // add blank spaces
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView blank = new ImageView(new Image("Images/blank.png"));
                blank.setUserData("room");
                add(blank, column, rows, 1,1);
                images[column][rows] = blank;
                wallImages[column][rows] = "Images/blank.png";
            }
        }

        // add dividers horizontally
        for (int column = 0; column < columnsAmount; column += 2) {
            for (int rows = 1; rows < rowsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/horiz.png"));
                spacer.setUserData("horizLocked");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
                wallImages[column][rows] = "Images/horiz.png";
            }
        }

        // add dividers vertically
        for (int column = 1; column < rowsAmount; column += 2) {
            for (int rows = 0; rows < columnsAmount; rows += 2) {
                ImageView spacer = new ImageView(new Image("Images/vert.png"));
                spacer.setUserData("vertLocked");
                add(spacer, column, rows, 1,1);
                images[column][rows] = spacer;
                wallImages[column][rows] = "Images/vert.png";
            }
        }

        ImageView character = images[0][0];
        character.setImage(new Image("Images/" + this.character + ".png"));
        xLoc = 0; yLoc = 0;

        ImageView exit = images[columnsAmount-2][rowsAmount];
        wallImages[columnsAmount-2][rowsAmount] = "Images/exit.png";
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
            wallImages[x][y] = "Images/sealedVert.jpg";
            lockedDoor.setUserData("sealed");
        }
        else if (lockedDoor.getUserData().equals("horizLocked")) {
            lockedDoor.setImage(new Image("Images/sealedHoriz.jpg"));
            wallImages[x][y] = "Images/sealedHoriz.jpg";
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
            wallImages[x][y] = "Images/unlockedHoriz.png";
        }
        else if (wall.getUserData().equals("vertLocked")){
            wall.setImage(new Image("Images/unlockedVert.png"));
            wallImages[x][y] = "Images/unlockedVert.png";
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
        if(!this.canMoveUpUpdate() && !this.canMoveDownUpdate() && !this.canMoveLeftUpdate() && !this.canMoveRightUpdate()) {
            ErrorDialog dialog = new ErrorDialog("You are trapped in the maze!", "You are trapped!", tnt, fix);
            dialog.showAndWait();
            System.exit(0);
        }
    }

    protected boolean canMoveLeftUpdate() {
        if (xLoc - 2 > -1) {
            ImageView wall = images[xLoc - 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveRightUpdate() {
        if (xLoc + 2 < images.length) {
            ImageView wall = images[xLoc + 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveDownUpdate() {
        if (yLoc + 2 < images.length) {
            ImageView wall = images[xLoc][yLoc + 1];
            if (wall.getUserData().equals("sealed")) {
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean canMoveUpUpdate() {
        if (yLoc - 2 > -1) {
            ImageView wall = images[xLoc][yLoc - 1];
            if (wall.getUserData().equals("sealed")) {
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean isGameOver(int x, int y) {
        if(x == 2 && y == 2) {
            return true;
        }
        else {
            return false;
        }
    }

    public void refreshCharacterLocation() {
        images[xLoc][yLoc].setImage(new Image("Images/" + character + ".png"));
    }

    public void clearCharacter() {
        images[xLoc][yLoc].setImage(new Image("Images/blank.png"));
    }

    public String[][] getUserData() {
        int columnsAmount = 2 * 2;
        int rowsAmount = 2 * 2 - 2;
        String[][] userData = new String[columnsAmount-1][rowsAmount+1];

        for (int x = 0; x < columnsAmount - 1; x++) {
            for (int y = 0; y < rowsAmount + 1; y++){
                if(images[x][y] != null) {
                    userData[x][y] = (String) images[x][y].getUserData();
                }
            }
        }

        return userData;
    }

    public void setUserData(String[][] userData) {
        int columnsAmount = 2 * 2;
        int rowsAmount = 2 * 2 - 2;

        for (int x = 0; x < columnsAmount - 1; x++) {
            for (int y = 0; y < rowsAmount + 1; y++){
                if (userData[x][y] != null) {
                    System.out.println(wallImages[x][y]);
                    images[x][y].setImage(new Image(wallImages[x][y]));
                    images[x][y].setUserData(userData[x][y]);
                }
            }
        }
    }

    public String[][] getWallImages() {
        return wallImages;
    }

    public void setWallImages(String[][] wallImages) {
        this.wallImages = wallImages;
    }

}
