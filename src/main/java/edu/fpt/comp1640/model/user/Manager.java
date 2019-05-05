package edu.fpt.comp1640.model.user;

import java.sql.SQLException;

public class Manager extends User {
    Manager(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        super(name, username, hashedPassword, email, roleId);
        this.role = User.ROLE_MANAGER;
    }

    @Override
    void getAdditionalInformation() { }

    @Override
    public int getRole() {
        return User.ROLE_MANAGER;
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
        return true;
    }
}
