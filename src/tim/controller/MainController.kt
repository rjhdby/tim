package tim.controller

import javafx.fxml.FXML


class MainController {
    @FXML lateinit private var checkTabController: CheckTabController
    @FXML lateinit private var fullListTabController: FullListTabController

    @FXML private fun initialize() {
        setUp()
    }

    private fun setUp() {
        checkTabController.setUp()
        fullListTabController.setUp()
    }
}
