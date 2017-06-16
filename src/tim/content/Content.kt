package tim.content

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tim.db.statements.*
import tim.properties.Config
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.SQLException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

object Content {
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
        get() = FXCollections.observableList(probes.values.stream().filter({ probe: Probe -> probe.nextCheck.before(cal.time) }).sorted({ a, b -> if (a.nextCheck.after(b.nextCheck)) 1 else -1 }).collect(Collectors.toList()))

    fun delete(probe: Probe) {
        DeleteProbe(probes[probe.name]!!)
        probes.remove(probe.name)
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

    fun unload(name: String): String {
        Files.write(Paths.get(Config.path + name),
                probes.values
                        .map { (name1, location, nextCheck) -> "$name1;$location;${dt.format(nextCheck.time)}" }
                        .toList())
        return Config.path + name
    }

    fun clear(){
        ClearBase()
        probes.clear()
    }

    fun loadFromFile(file: File):Int{
        var i = 0
        BufferedReader(FileReader(file)).use { br ->
            while (true) {
                val line: String? = br.readLine() ?: break
                val (name, location, nextCheck) = line!!.split(';')
                Content.add(Probe(name, location, SimpleDateFormat("yyyy-MM-dd").parse(nextCheck)))
                i++
            }
        }
        return i
    }
}
