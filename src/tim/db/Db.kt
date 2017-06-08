package tim.db

import tim.properties.Config
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class Db {
    abstract val sql: String
    val connection: Connection by lazy {
        Class.forName("org.h2.Driver")
        return@lazy DriverManager.getConnection("jdbc:h2:" + Config.get("db"), Config.get("user"), Config.get("pass"))
    }
    protected val stmt: PreparedStatement by lazy {
        return@lazy connection.prepareStatement(sql)
    }
    val result: ResultSet
        get() = stmt.resultSet
}
