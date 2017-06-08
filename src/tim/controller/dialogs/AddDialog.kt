package tim.controller.dialogs

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import tim.content.Probe
import java.time.Instant
import java.time.ZoneId
import java.util.*

class AddDialog(callback: (probe: Probe) -> Unit) : Dialog<Probe>() {
    init {
        title = "Добавление датчика"
        headerText = "Введите наименование и месторасположение"
        val name = TextField()
        name.promptText = "Наименование"
        val location = TextField()
        location.promptText = "Расположение"
        val datePicker = DatePicker()

        val grid = GridPane()
        with(grid) {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(20.0, 150.0, 10.0, 10.0)
            add(Label("Наименование:"), 0, 0)
            add(name, 1, 0)
            add(Label("Расположение:"), 0, 1)
            add(location, 1, 1)
            add(Label("Следующая проверка:"), 0, 2)
            add(datePicker, 1, 2)
        }
        val add = ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE)
        dialogPane.buttonTypes.addAll(add, ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE))
        val addButton = dialogPane.lookupButton(add)
        addButton.isDisable = true

        name.textProperty().addListener { _, _, _ -> addButton.isDisable = canAdd(name, location, datePicker) }
        location.textProperty().addListener { _, _, _ -> addButton.isDisable = canAdd(name, location, datePicker) }
        datePicker.valueProperty().addListener { _, _, _ -> addButton.isDisable = canAdd(name, location, datePicker) }
        dialogPane.content = grid

        Platform.runLater { name.requestFocus() }

        setResultConverter { dialogButton ->
            if (dialogButton == add) {
                return@setResultConverter Probe(name.text, location.text, Date.from(Instant.from(datePicker.value.atStartOfDay(ZoneId.systemDefault()))))
            }
            null
        }

        val result = showAndWait()

        result.ifPresent { result -> callback(result) }
    }

    private fun canAdd(name: TextField, location: TextField, datePicker: DatePicker): Boolean {
        return location.text.isEmpty() || name.text.isEmpty() || datePicker.value == null
    }

}