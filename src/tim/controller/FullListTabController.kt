package tim.controller

import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import tim.content.Content
import tim.content.Probe
import tim.controller.dialogs.AddDialog
import tim.controller.dialogs.ConfirmDialog


class FullListTabController {
    lateinit private var filteredData: FilteredList<Probe>

    @FXML lateinit private var fullList: TableView<Probe>

    @FXML lateinit private var flName: TableColumn<Probe, String>
    @FXML lateinit private var flLoc: TableColumn<Probe, String>
    @FXML lateinit private var flDate: TableColumn<Probe, String>

    @FXML lateinit private var delete: Button
    @FXML lateinit private var restore: Button

    @FXML lateinit private var searchName: TextField
    @FXML lateinit private var searchLocation: TextField

    private var lastDeleted: Probe? = null

    @FXML private fun initialize() {
        flName.cellValueFactory = PropertyValueFactory<Probe, String>("name")
        flLoc.cellValueFactory = PropertyValueFactory<Probe, String>("location")
        flDate.cellValueFactory = PropertyValueFactory<Probe, String>("nextCheckString")

        searchName.textProperty().addListener { _, _, newValue -> filteredData.setPredicate { (name) -> newValue == null || newValue.isEmpty() || name.toLowerCase().contains(newValue.toLowerCase()) } }
        searchLocation.textProperty().addListener { _, _, newValue -> filteredData.setPredicate { (_, location) -> newValue == null || newValue.isEmpty() || location.toLowerCase().contains(newValue.toLowerCase()) } }

        fullList.selectionModel.selectedItemProperty().addListener { _, _, s -> delete.isDisable = s == null }
    }

    @FXML fun delete() {
        ConfirmDialog({
            lastDeleted = Content.find(fullList.selectionModel.selectedItem.name)
            Content.delete(lastDeleted!!)
            delete.isDisable = true
            restore.isDisable = false
            setUp()
        }, "Удаление датчика")
    }

    @FXML fun add() {
        AddDialog { probe: Probe ->
            Content.add(probe)
            setUp()
        }
    }

    @FXML fun restore() {
        if (lastDeleted == null) return
        Content.add(lastDeleted!!)
        lastDeleted = null
        restore.isDisable = true
        setUp()
    }

    fun setUp() {
        filteredData = FilteredList(Content.all, { true })
        fullList.items = filteredData
    }
}
