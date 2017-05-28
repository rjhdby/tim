package tim.content;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tim.db.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Content {
    private HashMap<String, Probe> probes = new HashMap<>();
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar cal = Calendar.getInstance();
    private PreparedStatement deleteStmt = Db.connection().prepareStatement("DELETE FROM PROBES WHERE NAME=?");
    private PreparedStatement selectStmt = Db.connection().prepareStatement("SELECT NAME, LOCATION, NEXTCHECK FROM PROBES ORDER BY NEXTCHECK");
    private PreparedStatement addStmt = Db.connection().prepareStatement("INSERT INTO PROBES VALUES(?,?,CURRENT_TIMESTAMP)");
    private PreparedStatement checkStmt = Db.connection().prepareStatement("UPDATE PROBES SET NEXTCHECK=DATEADD('MONTH',1, CURRENT_DATE) WHERE NAME=?");

    {
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    }

    public Content() throws SQLException {
        try {
            selectStmt.execute();
            ResultSet rs = selectStmt.getResultSet();
            while (rs.next()) {
                probes.put(rs.getString("NAME"), new Probe(rs.getString("NAME"), rs.getString("LOCATION"), dt.parse(rs.getString("NEXTCHECK"))));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            System.out.println("Database read error");
            System.exit(3);
        }
    }

    public FilteredList<Probe> getAll() {
        ObservableList<Probe> list = FXCollections.observableList(new ArrayList<>(probes.values()));
        return new FilteredList<>(list, p -> true);
    }

    public ObservableList<Probe> getUnChecked() {
        return FXCollections.observableList(probes.values().stream().filter(probe -> probe.nextCheck.before(cal.getTime())).sorted((a, b) -> a.nextCheck.before(b.nextCheck) ? 1 : -1).collect(Collectors.toList()));
    }

    public void delete(String name) {
        try {
            deleteStmt.setString(1, name);
            deleteStmt.execute();
            probes.remove(name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database write error");
            System.exit(3);
        }
    }

    public void add(String name, String location) {
        try {
            addStmt.setString(1, name);
            addStmt.setString(2, location);
            addStmt.execute();
            probes.put(name, new Probe(name, location, new Date()));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database write error");
            System.exit(3);
        }
    }

    public void check(String name) {
        try {
            checkStmt.setString(1, name);
            checkStmt.execute();
            Probe probe = probes.get(name);
            Calendar cal = Calendar.getInstance();
            cal.setTime(probe.nextCheck);
            cal.add(Calendar.MONTH, 1);
            probe.nextCheck = cal.getTime();
            probes.put(name, probe);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database write error");
            System.exit(3);
        }
    }
}
