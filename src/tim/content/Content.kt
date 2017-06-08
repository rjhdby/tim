package tim.content

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tim.db.statements.AddProbe
import tim.db.statements.CheckProbe
import tim.db.statements.DeleteProbe
import tim.db.statements.GetProbes

import java.sql.ResultSet
import java.sql.SQLException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap
import java.util.stream.Collectors

object Content
 {
    private val probes = HashMap<String, Probe>()
    private val dt = SimpleDateFormat("yyyy-MM-dd")
    private val cal = Calendar.getInstance()

    init {
        cal.add(Calendar.MONTH, 1)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
    }

    init {
        try {
            val rs = GetProbes().result
            while (rs.next()) {
                probes.put(rs.getString("NAME"), Probe(rs.getString("NAME"), rs.getString("LOCATION"), dt.parse(rs.getString("NEXTCHECK"))))
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            println("Database read error")
            System.exit(3)
        } catch (e: ParseException) {
            e.printStackTrace()
            println("Database read error")
            System.exit(3)
        }

    }

    val all: FilteredList<Probe>
        get() {
            val list = FXCollections.observableList(ArrayList(probes.values))
            return FilteredList(list) { p -> true }
        }

    val unChecked: ObservableList<Probe>
        get() = FXCollections.observableList(probes.values.stream().filter({ probe:Probe -> probe.nextCheck.before(cal.time) }).sorted({ a, b-> if (a.nextCheck.after(b.nextCheck)) 1 else -1 }).collect(Collectors.toList()))

    fun delete(name: String) {
        DeleteProbe(probes[name]!!)
        probes.remove(name)
    }

    fun add(probe: Probe) {
        AddProbe(probe)
        probes.put(probe.name, probe)
    }

    fun check(probe: Probe) {
        CheckProbe(probe)
        probes.put(probe.name, probe)
    }

    fun find(name: String): Probe {
        return probes[name]!!
    }
}
