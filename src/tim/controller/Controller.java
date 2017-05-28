package tim.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import tim.content.Content;
import tim.content.Probe;

import java.sql.SQLException;
import java.util.Optional;

public class Controller {
    private Content content = new Content();
//    private FilteredList<Probe> filteredData = new FilteredList<>(content.getAll(), p -> true);

    @FXML
    private TableView<Probe> checkList, fullList;

    @FXML
    private TableColumn<Probe, String> clName, flName, clLoc, flLoc, clDate, flDate;

    @FXML
    private Button checkIn, delete, add;

    @FXML
    private TextField searchName, searchLocation;

    private String fullSelected, uncheckedSelected;

    public Controller() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {


        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        clDate.setCellValueFactory(new PropertyValueFactory<>("nextCheck"));

        flName.setCellValueFactory(new PropertyValueFactory<>("name"));
        flLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        flDate.setCellValueFactory(new PropertyValueFactory<>("nextCheck"));

        // заполняем таблицу данными
        setUp();

        searchName.textProperty().addListener((observable, oldValue, newValue) -> content.getAll().setPredicate(probe -> newValue == null || newValue.isEmpty() || probe.getName().toLowerCase().contains(newValue.toLowerCase())));
        searchLocation.textProperty().addListener((observable, oldValue, newValue) -> content.getAll().setPredicate(probe -> newValue == null || newValue.isEmpty() || probe.getLocation().toLowerCase().contains(newValue.toLowerCase())));

        checkList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                checkIn.setDisable(false);
                uncheckedSelected = newSelection.getName();
            }
        });
        fullList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                delete.setDisable(false);
                add.setDisable(false);
                fullSelected = newSelection.getName();
            }
        });
    }

    @FXML
    public void checkIn() {
        content.check(uncheckedSelected);
        setUp();
    }

    @FXML
    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление датчика");
        alert.setHeaderText("Подтвердите удаление датчика");
        alert.setContentText("Удаляем?");

        ButtonType ok = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(ok, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ok) {
            content.delete(fullSelected);
            delete.setDisable(true);
            setUp();
        }
    }

    @FXML
    public void add() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Добавление датчика");
        dialog.setHeaderText("Введите наименование и месторасположение");

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Наименование");
        TextField location = new TextField();
        location.setPromptText("Расположение");

        grid.add(new Label("Наименование:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Расположение:"), 0, 1);
        grid.add(location, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        ButtonType add = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(add, cancel);
        Node addButton = dialog.getDialogPane().lookupButton(add);
        addButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == add) {
                return new Pair<>(name.getText(), location.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            content.add(name.getText(), location.getText());
            setUp();
        });
    }

    private void setUp() {
        fullList.getItems().clear();
        checkList.getItems().clear();
        fullList.getItems().addAll(content.getAll());
        checkList.getItems().addAll(content.getUnChecked());
    }
}
