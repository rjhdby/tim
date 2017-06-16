package tim.controller

import javafx.fxml.FXML
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.stage.FileChooser
import tim.content.Content
import tim.controller.dialogs.ConfirmDialog
import tim.controller.dialogs.ErrorDialog
import tim.controller.dialogs.InfoDialog
import tim.properties.Config
import java.io.File
import java.util.*


class DbTabController {
    @FXML private fun initialize() {
    }

    @FXML fun unload() {
        try {
            val link = Content.unload(Date().time.toString() + ".csv")
            val content = ClipboardContent()
            content.putString(link)
            Clipboard.getSystemClipboard().setContent(content)
            InfoDialog("Выгрузка завершена",
                    "Данные выгружены в файл",
                    "$link\n\nПуть скопирован в буфер обмена")
        } catch (e: Exception) {
            ErrorDialog("Ошибка", "Не удалось выгрузить данные в файл", exception = e)
        }
    }

    @FXML fun append() {
        val file = fileSelect() ?: return
        try {
            val count = Content.loadFromFile(file)
            InfoDialog("Данные загружены", content = "Добавлено $count записей")
        } catch (e: Exception) {
            ErrorDialog(title = "Ошибка загрузки", content = "При загрузке данных произошла ошибка", exception = e)
        }
    }

    @FXML fun reload() {
        val file = fileSelect() ?: return
        try {
            val link = Content.unload(Date().time.toString() + ".csv")
            Content.clear()
            val count = Content.loadFromFile(file)
            InfoDialog("Данные загружены", content = "Добавлено $count записей.\n\nСтарые данные выгружены в файл $link")
        } catch (e: Exception) {
            ErrorDialog(title = "Ошибка загрузки", content = "При загрузке данных произошла ошибка", exception = e)
        }
    }

    @FXML fun clear() {
        ConfirmDialog({
            val link = Content.unload(Date().time.toString() + ".csv")
            Content.clear()
            InfoDialog("Все записи удалены", content = "Данные выгружены в файл $link")
        }, "Удаление всех записей")
    }

    fun fileSelect(): File? {
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"))
        fileChooser.initialDirectory = File(Config.path)
        return fileChooser.showOpenDialog(null)
    }
}
