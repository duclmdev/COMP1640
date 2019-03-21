package edu.fpt.comp1640.model.user;

import edu.fpt.comp1640.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest extends User {
    private int facultyId;

    Guest(String name, String username, String hashedPassword, String email, int roleId) {
        super(name, username, hashedPassword, email, roleId);

        String sql = "SELECT * FROM guests WHERE id = ?";
        try (Database db = new Database()) {
            ResultSet result = db.query(sql, new Integer[]{roleId});
            if (result.next()) {
                this.facultyId = result.getInt("faculty_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getFacultyId() {
        return facultyId;
    }

    @Override
    public int getRole() {
        return User.ROLE_GUEST;
    }

    @Override
    public boolean canSubmit() {
        return false;
    }

    @Override
    public boolean canEditSubmit() {
        return false;
    }

    @Override
    public boolean canViewStatistic() {
        return false;
    }
}
