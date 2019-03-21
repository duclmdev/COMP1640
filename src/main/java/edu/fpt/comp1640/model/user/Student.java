package edu.fpt.comp1640.model.user;

public class Student extends User {
    Student(String name, String username, String hashedPassword, String email, int roleId) {
        super(name, username, hashedPassword, email, roleId);
        this.role = User.ROLE_STUDENT;
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
    public boolean canEditSubmit() {
        return true;
    }

    @Override
    public boolean canViewStatistic() {
        return false;
    }
}
