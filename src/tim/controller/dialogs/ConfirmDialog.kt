package tim.controller.dialogs

import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType

class ConfirmDialog(callback: () -> Unit, title: String = "Подтверждение действия", header: String? = null, content: String = "Уверены?") : Alert(Alert.AlertType.CONFIRMATION) {
    init {
        this.title = title
        this.headerText = null
        this.contentText = "Уверены?"
        val ok = ButtonType("Да", ButtonBar.ButtonData.OK_DONE)
        buttonTypes.setAll(ok, ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE))
        val result = showAndWait()
        if (result.get() == ok) {
            callback()
        }
    }
}