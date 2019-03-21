package edu.fpt.comp1640.model.user;

public class Administrator extends User {
    public Administrator(String name, String username, String hashedPassword, String email, int roleId) {
        super(name, username, hashedPassword, email, roleId);
    }

    @Override
    public int getRole() {
        return User.ROLE_ADMINISTRATOR;
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
        return true;
    }
}
