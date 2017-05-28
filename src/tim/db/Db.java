package tim.db;

import tim.properties.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Connection connection;

    public static Connection connection() {
        if (connection == null) {
            String dbURL = "jdbc:h2:" + Config.get("db");
            try {
                Class.forName("org.h2.Driver");
//                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
                connection = DriverManager.getConnection(dbURL, Config.get("user"), Config.get("pass"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Database not found, or other DB error");
                System.exit(2);
            }
        }
        return connection;
    }
}
