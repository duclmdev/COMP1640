package edu.fpt.comp1640.model.user;

import edu.fpt.comp1640.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest extends User {
    private int facultyId;

    Guest(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
    }

    @Override
    void getAdditionalInformation() throws SQLException {
        String sql = "SELECT * FROM Guests WHERE id = ?";
        try (Database db = new Database()) {
            ResultSet result = db.query(sql, new Integer[]{getRoleId()});
            if (result.next()) {
                this.facultyId = result.getInt("faculty_id");
            }
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
    public boolean canViewSubmission() {
        return true;
    }

    @Override
    public boolean canViewStatistic() {
        return false;
    }
}
