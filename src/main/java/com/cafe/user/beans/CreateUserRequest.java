package com.cafe.user.beans;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {

    private static final long serialVersionUID = -83478257285285L;


    private String userName;

    private String password;

    private String name;

    private String userRole;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
