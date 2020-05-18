package DialogBoxes;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class InputDialog extends TextInputDialog {

	
    public InputDialog(String header) {
        setHeaderText(header);
    }

    public String getAnswer() {
        Optional<String> answer = showAndWait();
        return answer.get();
    }

}
