package DialogBoxes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorDialog extends Alert {

    public ErrorDialog(String errorMessage, ButtonType tnt, ButtonType fix) {
        super(AlertType.ERROR, errorMessage);
        getDialogPane().getButtonTypes().addAll(tnt, fix);
    }

    public ErrorDialog(String errorMessage, String title, ButtonType tnt, ButtonType fix) {
        super(AlertType.ERROR, errorMessage);
        setHeaderText(title);
        getDialogPane().getButtonTypes().addAll(tnt, fix);
    }
}
