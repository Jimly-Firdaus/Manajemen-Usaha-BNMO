package logic.store;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import logic.store.interfaces.*;

@NoArgsConstructor
@Value
public class Database implements Parser, Storable {
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup connection to db
     * 
     * @return connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:derby:bnmoDatabase;create=true";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }
}