package edu.fpt.comp1640.database;

import edu.fpt.comp1640.model.user.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.ResultSet.*;

public class Database implements AutoCloseable {

    private static final String DB_URL = "jdbc:sqlite:./db/sqlite/comp1640.sqlite";
    private final Connection connection;
    private static final Security security = new Security();

    public Database() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }

    //FIXME
    public User authenticate(String account, String password) {
        String query = "SELECT id, name, username, hashed_password, email, role, role_id FROM Users WHERE username = ? OR email = ? LIMIT 1";
        try (
            Connection connect = this.connection;
            PreparedStatement pstm = connect.prepareStatement(query)
        ) {
            pstm.setString(1, account);
            pstm.setString(2, account);
            ResultSet result = pstm.executeQuery();
            System.out.println(result);
            if (result.next()) {
                String name = result.getString("name");
                String username = result.getString("username");
                String hashedPassword = result.getString("hashed_password");
                String email = result.getString("email");
                int role = result.getInt("role");
                int role_id = result.getInt("role_id");

                if (validatePassword(password, hashedPassword)) {
                    return User.getUser(name, username, hashedPassword, email, role, role_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean validatePassword(String password, String code) {
        try {
            return security.validatePassword(password, code);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String createHashed(String pass) {
        try {
            return security.createPassword(pass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet query(String sql) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY);
        return pstm.executeQuery();
    }

    public ResultSet query(String sql, Object[] param) throws SQLException {
        try (PreparedStatement pstm = connection.prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
            prepareStatement(pstm, param);
            return pstm.executeQuery();
        }
    }

    public void query(String sql, Object[] param, ResultSetHandler handler) throws Exception {
        try (PreparedStatement pstm = connection.prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
            prepareStatement(pstm, param);
            ResultSet resultSet = pstm.executeQuery();
            handler.handle(resultSet);
        }
    }

    public ResultSet insert(String sql, Object[] param) throws SQLException {
        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement(pstm, param);
            pstm.executeUpdate();
            return pstm.getGeneratedKeys();
        }
    }

    public int update(String sql, Object[] param) throws SQLException {
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            prepareStatement(pstm, param);
            return pstm.executeUpdate();
        }
    }

    private void prepareStatement(PreparedStatement pstm, Object[] param) throws SQLException {
        int length = param == null ? 0 : param.length;
        for (int i = 0; i < length; i++) {
            pstm.setObject(i + 1, param[i]);
        }
    }

    @Override
    public void finalize() {
        close();
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
