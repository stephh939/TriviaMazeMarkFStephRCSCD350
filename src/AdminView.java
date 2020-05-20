import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminView extends BorderPane {

    private Button close, editSave;

    public AdminView() throws SQLException {
        setTop(createTitle());

        QuestionsTable table = new QuestionsTable();
        MazeConnection conn = connectToDatabase();
        conn.fillTable(table);
        setCenter(table);

        setBottom(buildToolBar());
    }

    private HBox buildToolBar() {
        editSave = new Button("Edit");
        editSave.setPadding(new Insets(8, 16, 8, 16));

        close = new Button("Close");
        close.setPadding(new Insets(8, 16, 8, 16));

        HBox toolbar = new HBox(editSave, close);
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

    private MazeConnection connectToDatabase() {
        SQLiteDataSource ds;
        MazeConnection myQuery = null;
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:questionsDatabase1.db");
            Connection conn = ds.getConnection();
            myQuery = new MazeConnection(conn);
        }catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return myQuery;
    }

    public Button getEditSave() {
        return editSave;
    }

    public void setEditSave(Button editSave) {
        this.editSave = editSave;
    }

    public Button getClose() {
        return close;
    }

    public void setClose(Button close) {
        this.close = close;
    }

}
