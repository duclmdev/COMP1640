package edu.fpt.comp1640.database;

import edu.fpt.comp1640.model.user.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
        String query = "SELECT * FROM Users WHERE username = ? OR email = ? LIMIT 1";
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
        PreparedStatement pstm = connection.prepareStatement(sql, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        return pstm.executeQuery();
    }

    public ResultSet query(String sql, Object[] param) throws SQLException {
        try (PreparedStatement pstm = connection.prepareStatement(sql, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE)) {
            prepareStatement(pstm, param);
            return pstm.executeQuery();
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

    private static class Security {

        boolean validatePassword(String originalPass, String storedPass) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String[] parts = storedPass.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);
            PBEKeySpec spec = new PBEKeySpec(originalPass.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();
            int different = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                different |= hash[i] ^ testHash[i];
            }
            return different == 0;
        }

        String createPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
            int iterations = 1000;
            char[] chars = password.toCharArray();
            byte[] salt = getSalt();
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        }

        private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
            byte[] bytes = new byte[hex.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
            }
            return bytes;
        }

        private String toHex(byte[] array) throws NoSuchAlgorithmException {
            BigInteger big = new BigInteger(1, array);
            String hex = big.toString(16);
            int paddingLength = (array.length * 2) - hex.length();
            return (paddingLength > 0) ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
        }

        private byte[] getSalt() throws NoSuchAlgorithmException {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }
    }
}
