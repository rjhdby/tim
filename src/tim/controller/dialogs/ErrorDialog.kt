package tim.controller.dialogs

import javafx.beans.value.ObservableValue
import javafx.scene.control.Alert
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import java.io.PrintWriter
import java.io.StringWriter


class ErrorDialog(title: String, content: String, header: String? = null, exception: Exception? = null) : Alert(AlertType.ERROR) {
    init {
        this.title = title
        this.contentText = content
        this.headerText = header
        if (exception != null) {
            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            val label = Label("Лог ошибки:")

            val textArea = TextArea(sw.toString())
            textArea.isEditable = false
            textArea.isWrapText = true

            textArea.maxWidth = java.lang.Double.MAX_VALUE
            textArea.maxHeight = java.lang.Double.MAX_VALUE
            GridPane.setVgrow(textArea, Priority.ALWAYS)
            GridPane.setHgrow(textArea, Priority.ALWAYS)

            val expContent = GridPane()
            expContent.maxWidth = java.lang.Double.MAX_VALUE
            expContent.add(label, 0, 0)
            expContent.add(textArea, 0, 1)

            dialogPane.expandedProperty().addListener(
                    { _: ObservableValue<out Boolean>, _: Boolean, newValue: Boolean -> (dialogPane.lookup(".details-button") as Hyperlink).text = if (newValue) "Скрыть" else "Раскрыть" })
            dialogPane.isExpanded = true
            dialogPane.expandableContent = expContent
        }
        showAndWait()
    }
}
