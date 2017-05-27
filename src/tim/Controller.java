package tim;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tim.content.Content;
import tim.content.Probe;

import java.util.Date;

public class Controller {
    @FXML
    private TableView<Probe> checkList;

    @FXML
    private TableColumn<Probe, String> name;

    @FXML
    private TableColumn<Probe, String> loc;

    @FXML
    private TableColumn<Probe, Date> date;

    @FXML
    private Button checkIn;

    @FXML
    private void initialize() {
        Content content = new Content();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        date.setCellValueFactory(new PropertyValueFactory<>("nextCheck"));

        // заполняем таблицу данными
        checkList.setItems(content.getData());
    }

    @FXML
    public void checkIn() {
        checkIn.setText("Thanks!");
    }
}
