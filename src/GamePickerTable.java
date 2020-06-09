import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GamePickerTable extends TableView {

    public GamePickerTable() {
        TableColumn game = new TableColumn("Game");

        game.setCellValueFactory(new PropertyValueFactory<>("gameName"));

        getColumns().add(game);
    }

}
