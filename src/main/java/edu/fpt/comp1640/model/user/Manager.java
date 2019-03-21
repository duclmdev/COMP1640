package edu.fpt.comp1640.model.user;

public class Manager extends User {
    Manager(String name, String username, String hashedPassword, String email, int roleId) {
        super(name, username, hashedPassword, email, roleId);
        this.role = User.ROLE_MANAGER;
    }

    @Override
    public int getRole() {
        return User.ROLE_MANAGER;
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
