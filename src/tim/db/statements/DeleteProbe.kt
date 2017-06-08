package tim.db.statements

import tim.content.Probe
import tim.db.Db

class DeleteProbe(probe: Probe) : Db() {
    override val sql = "DELETE FROM PROBES WHERE NAME=?"

    init {
        stmt.setString(1, probe.name)
        stmt.execute()
    }
}