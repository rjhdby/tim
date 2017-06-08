package tim.db.statements

import tim.content.Probe
import tim.db.Db

class AddProbe(probe: Probe) : Db() {
    override val sql = "INSERT INTO PROBES VALUES(?,?,?)"

    init {
        stmt.setString(1, probe.name)
        stmt.setString(2, probe.location)
        stmt.setString(3, probe.nextCheckString)
        stmt.execute()
    }
}