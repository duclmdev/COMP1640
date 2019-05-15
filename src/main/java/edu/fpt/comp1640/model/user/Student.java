package edu.fpt.comp1640.model.user;

import edu.fpt.comp1640.database.Database;

import java.sql.SQLException;

public class Student extends User {
    Student(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
        this.role = User.ROLE_STUDENT;

    }

    @Override
    void getAdditionalInformation() throws SQLException {
        //language=SQLite
        String sql = "SELECT faculty_id, rollnumber FROM Students S JOIN Faculties F ON S.faculty_id = F.id WHERE S.id = ?";
        try (Database db = new Database()) {
            db.query(sql, new Object[]{getRoleId()});

        }
    }

    @Override
    public int getRole() {
        return User.ROLE_STUDENT;
    }

    @Override
    public boolean canSubmit() {
        return true;
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
