import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuestionsTable extends TableView {

    public QuestionsTable() {
        TableColumn id = new TableColumn("ID");
        TableColumn question = new TableColumn("Question");
        TableColumn answer = new TableColumn("Answer");

        id.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        question.setCellValueFactory(new PropertyValueFactory<>("question"));
        answer.setCellValueFactory(new PropertyValueFactory<>("answer"));

        getColumns().add(id);
        getColumns().add(question);
        getColumns().add(answer);

        getSortOrder().add(id);
    }

}
