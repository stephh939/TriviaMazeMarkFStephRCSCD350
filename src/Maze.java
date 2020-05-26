import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import DialogBoxes.ErrorDialog;
import DialogBoxes.InputDialog;
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
    private String character;

    public Maze(int width, int height) {
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

    public String showWallDialog(){
    	ArrayList<String> q = MazeModel.getQuestion();
    	ArrayList<String> i = MazeModel.getID();
    	ArrayList<String> a = MazeModel.getAnswer();
    	Random r = new Random();
    	int random = r.nextInt(q.size());
    	
    	InputDialog question = new InputDialog("ID: "+ i.get(random) + " " + q.get(random));
    	String que = question.getAnswer().toLowerCase();
    	
    	if(que.compareTo(a.get(random)) == 0) {
    		q.remove(random);
    		a.remove(random);
    		i.remove(random);
    		return "correct";
    	}
       
    	else {
    			
    		return "incorrect";
    	}
    
   
    }

    public boolean canMoveUp() {
        if (yLoc - 2 > -1) {
            ImageView wall = images[xLoc][yLoc - 1];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed");
                message.showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean canMoveDown() {
        if (yLoc + 2 < images.length) {
            ImageView wall = images[xLoc][yLoc + 1];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed");
                message.showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean canMoveRight() {
        if (xLoc + 2 < images.length) {
            ImageView wall = images[xLoc + 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed");
                message.showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean canMoveLeft() {
        if (xLoc - 2 > -1) {
            ImageView wall = images[xLoc - 1][yLoc];
            if (wall.getUserData().equals("sealed")) {
                ErrorDialog message = new ErrorDialog("This door is sealed");
                message.showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean upDoorUnlocked() {
        if (yLoc - 2 > -1) {
            ImageView wall = images[xLoc][yLoc - 1];
            if (wall.getUserData().equals("unlocked")) {
                return true;
            }
        }
        return false;
    }

    public boolean downDoorUnlocked() {
        if (yLoc + 2 < images.length) {
            ImageView wall = images[xLoc][yLoc + 1];
            if (wall.getUserData().equals("unlocked")) {
                return true;
            }
        }
        return false;
    }

    public boolean rightDoorUnlocked() {
        if (xLoc + 2 < images.length) {
            ImageView wall = images[xLoc + 1][yLoc];
            if (wall.getUserData().equals("unlocked")) {
                return true;
            }
        }
        return false;
    }

    public boolean leftDoorUnlocked() {
        if (xLoc - 2 > -1) {
            ImageView wall = images[xLoc - 1][yLoc];
            if (wall.getUserData().equals("unlocked")) {
                return true;
            }
        }
        return false;
    }

    public void setWallToUnlocked(int x, int y) {
        ImageView wall = images[x][y];
        if (wall.getUserData().equals("horizLocked")) {
            wall.setImage(new Image("Images/unlockedHoriz.png"));
        }
        else if (wall.getUserData().equals("vertLocked")){
            wall.setImage(new Image("Images/unlockedVert.png"));
        }
        wall.setUserData("unlocked");
    }

    public void changeGameCharacter(String character) {
        this.character = character;
        images[xLoc][yLoc].setImage(new Image("Images/" + character + ".png"));
    }
    
}
