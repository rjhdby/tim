package tim.controller.dialogs

import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType

class DeleteDialog(callback: () -> Unit) : Alert(Alert.AlertType.CONFIRMATION) {
    init {
        title = "Удаление датчика"
        headerText = "Подтвердите удаление датчика"
        contentText = "Удаляем?"
        val ok = ButtonType("Да", ButtonBar.ButtonData.OK_DONE)
        buttonTypes.setAll(ok, ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE))
        val result = showAndWait()
        if (result.get() == ok) {
            callback()
        }
    }
}