import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuestionsTable extends TableView {

    private TableColumn answer;
    private TableColumn question;

    public QuestionsTable() {
        TableColumn id = new TableColumn("ID");
        question = new TableColumn("Question");
        answer = new TableColumn("Answer");

        id.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        question.setCellValueFactory(new PropertyValueFactory<>("question"));
        answer.setCellValueFactory(new PropertyValueFactory<>("answer"));

        getColumns().add(id);
        getColumns().add(question);
        getColumns().add(answer);
    }

    protected void enableEdit() {
        question.setEditable(true);
        answer.setEditable(true);
    }

    protected void save() {
        question.setEditable(false);
        answer.setEditable(false);
    }
}
