package tim.controller

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import tim.content.Content
import tim.content.Probe
import tim.controller.dialogs.CheckDialog

class CheckTabController {
    @FXML lateinit private var checkList: TableView<Probe>

    @FXML lateinit private var clName: TableColumn<Probe, String>
    @FXML lateinit private var clLoc: TableColumn<Probe, String>
    @FXML lateinit private var clDate: TableColumn<Probe, String>

    @FXML lateinit private var checkIn: Button

    @FXML private fun initialize() {
        clName.cellValueFactory = PropertyValueFactory<Probe, String>("name")
        clLoc.cellValueFactory = PropertyValueFactory<Probe, String>("location")
        clDate.cellValueFactory = PropertyValueFactory<Probe, String>("nextCheckString")
        checkList.selectionModel.selectedItemProperty().addListener { _, _, s -> checkIn.isDisable = s == null }
    }

    @FXML fun checkIn() {
        CheckDialog(Content.find(checkList.selectionModel.selectedItem.name)) { probe ->
            Content.check(probe)
            setUp()
            checkIn.isDisable = false
        }
    }

    fun setUp() {
        checkList.items = Content.unChecked
    }
}
