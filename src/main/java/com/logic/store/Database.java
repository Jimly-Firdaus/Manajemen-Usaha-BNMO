package com.logic.store;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import com.logic.store.interfaces.*;

@Value
@NoArgsConstructor
public class Database {
    private String dbUrl = "jdbc:sqlite:../BNMO.db";
    static {
        try {
            Class.forName("org.sqlite.JDBC");
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
        try (Connection conn = DriverManager.getConnection(this.dbUrl)) {
            System.out.println("Connected to SQLite database");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void createtable(String tableName, String colConstraint) {
        PreparedStatement queryStmt = null;
        try (Connection conn = DriverManager.getConnection(this.dbUrl)) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + colConstraint + ")";
            queryStmt = conn.prepareStatement(sql);
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
        }
    }

    public void insertData(String tableName, String tableCol, String values) {
        PreparedStatement queryStmt = null;
        try (Connection conn = DriverManager.getConnection(this.dbUrl)) {
            String sql = "INSERT INTO " + tableName + " (" + tableCol + ") VALUES (" + values + ")";
            queryStmt = conn.prepareStatement(sql);
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
        }
    }

    public void updateData(String tableName, String newValues, String condition) {
        Statement query = null;
        try (Connection conn = DriverManager.getConnection(this.dbUrl)) {
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
        }
    }

    public void deleteData(String tableName, String condition) {
        Statement query = null;
        try (Connection conn = DriverManager.getConnection(this.dbUrl)) {
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
        }
    }
}