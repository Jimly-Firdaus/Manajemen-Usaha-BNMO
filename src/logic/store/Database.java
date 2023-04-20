package logic.store;

import lombok.*;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        String url = "jdbc:mariadb://localhost:3306/myDatabase";
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    @NonNull
    @Nullable
    public void createtable(String tableName, String colConstraint) {
        Connection conn = null;
        PreparedStatement queryStmt = null;
        try {
            conn = this.getConnection();
            String sql = "CREATE TABLE ? (?)";
            queryStmt = conn.prepareStatement(sql);
            queryStmt.setString(1, tableName);
            queryStmt.setString(2, colConstraint);
            queryStmt.execute();
        } catch (SQLException e) {
            System.err.println("An error occured when creating table: " + e.getMessage());
        } finally {
            if (queryStmt != null) {
                try {
                    queryStmt.close();
                } catch (SQLException e) {
                    System.err.println("An error occured when closing statement: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("An error occured when closing connection: " + e.getMessage());
                }
            }
        }
    }

    public void insertData(String tableName, String tableCol, String values) {
        Connection conn = null;
        PreparedStatement queryStmt = null;
        try {
            conn = this.getConnection();
            String sql = "INSERT INTO " + tableName + " (" + tableCol + ") VALUES (?)";
            queryStmt = conn.prepareStatement(sql);
            queryStmt.setString(1, values);
            queryStmt.execute();
        } catch (SQLException e) {
            System.err.println("An error occured when inserting value: " + e.getMessage());
        } finally {
            if (queryStmt != null) {
                try {
                    queryStmt.close();
                } catch (SQLException e) {
                    System.err.println("An error occured when closing statement: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("An error occured when closing connection: " + e.getMessage());
                }
            }
        }
    }

    public void updateData(String tableName, String newValues, String condition) {
        Connection conn = null;
        Statement query = null;
        try {
            conn = this.getConnection();
            query = conn.createStatement();
            String sql = "UPDATE " + tableName + " SET " + newValues + " WHERE " + condition;
            query.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("An error occurred when updating data: " + e.getMessage());
        } finally {
            if (query != null) {
                try {
                    query.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred when closing statement: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred when closing connection: " + e.getMessage());
                }
            }
        }
    }

    public void deleteData(String tableName, String condition) {
        Connection conn = null;
        Statement query = null;
        try {
            conn = this.getConnection();
            query = conn.createStatement();
            String sql = "DELETE FROM " + tableName + " WHERE " + condition;
            query.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("An error occurred when deleting data: " + e.getMessage());
        } finally {
            if (query != null) {
                try {
                    query.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred when closing statement: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("An error occurred when closing connection: " + e.getMessage());
                }
            }
        }
    }
}