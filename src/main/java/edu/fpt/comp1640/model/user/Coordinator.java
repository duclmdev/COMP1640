package edu.fpt.comp1640.model.user;

import edu.fpt.comp1640.utils.DatabaseUtils;

import java.sql.SQLException;

public class Coordinator extends User {
    private int facultyId;
    private String facultyName;
    private String facultyDescription;

    Coordinator(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
    }

    @Override
    void getAdditionalInformation() throws SQLException {
        //language=SQLite
        String sql = "SELECT faculty_id, name, description FROM Coordinators C JOIN Faculties F ON C.faculty_id = F.id WHERE C.id = ?";
        try {
            DatabaseUtils.getResult(sql, new Object[]{getRoleId()}, rs -> {
                if (rs.next()) {
                    facultyId = rs.getInt(1);
                    facultyName = rs.getString(2);
                    facultyDescription = rs.getString(3);
                    System.out.println(facultyId);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getFacultyDescription() {
        return facultyDescription;
    }

    @Override
    public int getRole() {
        return User.ROLE_COORDINATOR;
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
