package tim.controller

import javafx.fxml.FXML
import javafx.scene.control.TabPane


class MainController {
    @FXML lateinit private var checkTabController: CheckTabController
    @FXML lateinit private var fullListTabController: FullListTabController
    @FXML lateinit private var tabPane: TabPane

    @FXML private fun initialize() {
        tabPane.selectionModel.selectedItemProperty().addListener { _, _, _ -> setUp() }
        setUp()
    }

    private fun setUp() {
        checkTabController.setUp()
        fullListTabController.setUp()
    }
}
