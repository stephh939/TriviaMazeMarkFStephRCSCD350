import DialogBoxes.InputDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.SQLException;

public class AdminView extends BorderPane {

    private Button close;
    private QuestionsTable table;
    private MazeConnection databaseConnection;

    public AdminView() throws SQLException {
        table = new QuestionsTable();
        databaseConnection = new MazeConnection();
        databaseConnection.fillTable(table);
        setTop(createTitle());
        setCenter(table);
        setBottom(buildToolBar());
    }

    private void onAdd() throws SQLException {
        InputDialog questionDialog = new InputDialog("Please enter the question:");
        String question = questionDialog.getAnswer();
        InputDialog answerDialog = new InputDialog("Please enter the answer:");
        String answer = answerDialog.getAnswer();
        
        databaseConnection.addQuestionToDatabase(question, answer, table);
        table.sort();
    }

    private HBox buildToolBar() {
        Button add = new Button("Add");
        add.setPadding(new Insets(8, 16, 8, 16));
        add.setOnAction(click -> {
			try {
				onAdd();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

        close = new Button("Close");
        close.setPadding(new Insets(8, 16, 8, 16));

        HBox toolbar = new HBox(add, close);
        toolbar.setAlignment(Pos.CENTER_RIGHT);
        toolbar.setPadding(new Insets(8));
        toolbar.setSpacing(16);
        return  toolbar;
    }

    private Label createTitle() {
        Label title = new Label();
        title.setText("Trivia Maze Admin");
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font ("Verdana", 40));
        title.setPadding(new Insets(16));

        return title;
    }

    public Button getClose() {
        return close;
    }

}
