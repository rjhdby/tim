package tim.content;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Content {
    public ArrayList<Probe> probes = new ArrayList<>();

    public Content() {
        SimpleDateFormat dt = new SimpleDateFormat("dd.mm.yyyy");
        try {
            probes.add(new Probe("ха001", "г-5/700", dt.parse("05.05.2017")));
            probes.add(new Probe("ха002", "г-5/700", dt.parse("05.07.2017")));
            probes.add(new Probe("ха003", "г-5/31", dt.parse("05.10.2017")));
            probes.add(new Probe("ха004", "г-5/67", dt.parse("05.09.2017")));
            probes.add(new Probe("ха005", "г-5/67", dt.parse("05.05.2017")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Probe> getData() {
        return FXCollections.observableList(probes);
    }
}
