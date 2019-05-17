package edu.fpt.comp1640.model.user;

import edu.fpt.comp1640.utils.DatabaseUtils;

import java.sql.SQLException;

public class Guest extends User {
    private int facultyId;

    Guest(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
    }

    @Override
    void getAdditionalInformation() throws SQLException {
        //language=SQL
        String sql = "SELECT faculty_id, name, description FROM Guests G JOIN Faculties F ON G.faculty_id = F.id WHERE G.id = ?";
        try {
            DatabaseUtils.getResult(sql, new Object[]{getRoleId()}, rs -> {
                if (rs.next()) {
                    facultyId = rs.getInt(1);
                }
            });
        } catch (Exception e) {
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
    public boolean canViewSubmission() {
        return true;
    }

    @Override
    public boolean canViewStatistic() {
        return false;
    }
}
