package tim.db.statements

import tim.db.Db

class GetProbes : Db() {
    override val sql = "SELECT NAME, LOCATION, NEXTCHECK FROM PROBES ORDER BY NEXTCHECK"
    init {
        stmt.execute()
    }
}