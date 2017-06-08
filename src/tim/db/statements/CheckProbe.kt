package tim.db.statements

import tim.content.Probe
import tim.db.Db

class CheckProbe(probe: Probe) : Db() {
    override val sql = "UPDATE PROBES SET NEXTCHECK=? WHERE NAME=?"

    init {
        stmt.setString(1, probe.nextCheckString)
        stmt.setString(2, probe.name)
        stmt.execute()
    }
}