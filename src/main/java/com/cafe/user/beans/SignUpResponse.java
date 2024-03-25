package com.cafe.user.beans;

import java.io.Serializable;

public class SignUpResponse implements Serializable {

    private static final long serialVersionUID = -8239823923L;


    private String userName;

    private String userRole;

    private String name;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
