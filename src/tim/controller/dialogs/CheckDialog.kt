package tim.controller.dialogs

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import tim.content.Probe
import java.time.Instant
import java.time.ZoneId
import java.util.*

class CheckDialog(probe: Probe, callback: (probe: Probe) -> Unit) : Dialog<Probe>() {
    init {
        title = "Проверка произведена"
        headerText = "Введите дату следующей проверки"
        val datePicker = DatePicker()
        val grid = GridPane()
        with(grid) {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(20.0, 150.0, 10.0, 10.0)
            add(Label("Следующая проверка:"), 0, 0)
            add(datePicker, 1, 0)
        }
        val ok = ButtonType("Ок", ButtonBar.ButtonData.OK_DONE)
        dialogPane.buttonTypes.addAll(ok, ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE))

        dialogPane.content = grid

        Platform.runLater { datePicker.requestFocus() }
        setResultConverter { dialogButton ->
            if (dialogButton == ok) {
                probe.nextCheck = Date.from(Instant.from(datePicker.value.atStartOfDay(ZoneId.systemDefault())))
                return@setResultConverter probe
            }
            null
        }

        val result = showAndWait()

        result.ifPresent { probe -> callback(probe) }
    }
}