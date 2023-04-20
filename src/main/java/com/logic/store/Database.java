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

// @Value
public class Database {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public Database() {
        // Load configuration from properties file
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
            this.dbUrl = props.getProperty("db.url");
            this.dbUsername = props.getProperty("db.username");
            this.dbPassword = props.getProperty("db.password");
            System.out.println(dbUrl);
            System.out.println(dbUsername);
            System.out.println(dbPassword);
        } catch (IOException e) {
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
        Connection conn = DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
        return conn;
    }

    public void createtable(String tableName, String colConstraint) {
        Connection conn = null;
        PreparedStatement queryStmt = null;
        try {
            conn = this.getConnection();
            String sql = "CREATE TABLE " + tableName + " (" + colConstraint + ")";
            queryStmt = conn.prepareStatement(sql);
            // queryStmt.setString(1, tableName);
            // queryStmt.setString(2, colConstraint);
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