package edu.fpt.comp1640.model.user;

import java.sql.SQLException;

public class Administrator extends User {
    public Administrator(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
    }

    @Override
    void getAdditionalInformation() { }

    @Override
    public int getRole() {
        return User.ROLE_ADMINISTRATOR;
    }

    @Override
    public boolean canSubmit() {
        return false;
    }

    @Override
    public boolean canViewSubmission() {
        return false;
    }

    @Override
    public boolean canViewStatistic() {
        return true;
    }
}
