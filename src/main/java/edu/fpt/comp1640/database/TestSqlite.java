package edu.fpt.comp1640.database;

import java.sql.*;

public class TestSqlite {
    private static String url = "jdbc:sqlite:./db/sqlite/tests.db";

    /**
     * Connect to a sample database
     */
    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());

                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (id integer PRIMARY KEY, name text NOT NULL, capacity real);";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAll() {
        String sql = "SELECT id, name, capacity FROM warehouses";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (getResultSize(rs) > 0) {
                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
                }
            } else {
                System.out.println("empty result!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getResultSize(ResultSet resultSet) {
        int size = 0;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (Exception ignored) {
        }
        return size;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewDatabase();
        createNewTable();
        selectAll();
    }
}

