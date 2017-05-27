package tim.content;

import java.util.Date;

public class Probe {
    public String name;
    public String location;
    public Date nextCheck;

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

    public Date getNextCheck() {
        return nextCheck;
    }
}
