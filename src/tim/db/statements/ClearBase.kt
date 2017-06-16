package tim.db.statements

import tim.db.Db

class ClearBase : Db() {
    override val sql = "DELETE FROM PROBES"
    init {
        stmt.execute()
    }
}