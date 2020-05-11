package DialogBoxes;

import javafx.scene.control.Alert;

public class ErrorDialog extends Alert {
    public ErrorDialog(String errorMessage) {
        super(AlertType.ERROR, errorMessage);
    }
}
