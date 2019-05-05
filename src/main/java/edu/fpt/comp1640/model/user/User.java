package edu.fpt.comp1640.model.user;

import java.sql.SQLException;

public abstract class User {
    public static final int ROLE_ADMINISTRATOR = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_COORDINATOR = 2;
    public static final int ROLE_STUDENT = 3;
    public static final int ROLE_GUEST = 4;

    private String name;
    private String username;
    private String hashedPassword;
    private String email;
    protected int role;
    private int roleId;

    private User() {}

    User(String name, String username, String hashedPassword, String email, int roleId) throws SQLException {
        this.name = name;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.roleId = roleId;
        getAdditionalInformation();
    }

    public static User getUser(
        String name,
        String username,
        String hashedPassword,
        String email,
        int role,
        int roleId
    ) throws Exception {
        System.out.println("getuser");
        switch (role) {
            case ROLE_ADMINISTRATOR:
                return new Administrator(name, username, hashedPassword, email, roleId);
            case ROLE_MANAGER:
                return new Manager(name, username, hashedPassword, email, roleId);
            case ROLE_COORDINATOR:
                return new Coordinator(name, username, hashedPassword, email, roleId);
            case ROLE_STUDENT:
                return new Student(name, username, hashedPassword, email, roleId);
            case ROLE_GUEST:
                return new Guest(name, username, hashedPassword, email, roleId);
            default:
                throw new Exception("Role ID of this user is invalid!");
        }
    }

    abstract void getAdditionalInformation() throws SQLException;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public abstract int getRole();

    public int getRoleId() {
        return roleId;
    }

    public abstract boolean canSubmit();
    public abstract boolean canViewSubmission();
    public abstract boolean canViewStatistic();
}
