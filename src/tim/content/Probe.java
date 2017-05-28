package tim.content;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Probe {
    public String name;
    public String location;
    public Date nextCheck;
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

    public Probe(String name, String location, Date nextCheck) {
        this.name = name;
        this.location = location;
        this.nextCheck = nextCheck;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getNextCheck() {
        return dt.format(nextCheck);
    }
}
