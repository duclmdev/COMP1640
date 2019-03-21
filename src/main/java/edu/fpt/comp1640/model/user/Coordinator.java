package edu.fpt.comp1640.model.user;

public class Coordinator extends User {
    Coordinator(String name, String username, String hashedPassword, String email, int roleId) {
        super(name, username, hashedPassword, email, roleId);
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
    public boolean canEditSubmit() {
        return false;
    }

    @Override
    public boolean canViewStatistic() {
        return false;
    }
}
