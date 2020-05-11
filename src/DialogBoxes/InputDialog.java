package DialogBoxes;

import javafx.scene.control.TextInputDialog;

public class InputDialog extends TextInputDialog {
    public InputDialog(String header) {
        setHeaderText(header);
    }
}
