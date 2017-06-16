package tim.controller.dialogs

import javafx.scene.control.Alert

class InfoDialog(title: String, header: String?=null, content: String) : Alert(AlertType.INFORMATION) {
    init {
        this.title = title
        headerText = header
        contentText = content
        showAndWait()
    }
}
