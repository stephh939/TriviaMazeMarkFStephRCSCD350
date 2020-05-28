package DialogBoxes;

import javafx.scene.control.Alert;

public class WarningDialog extends Alert {

    public WarningDialog(String warningMessage) {
        super(AlertType.WARNING, warningMessage);
    }

}
