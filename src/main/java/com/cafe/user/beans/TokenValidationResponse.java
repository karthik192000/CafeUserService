package com.cafe.user.beans;

import java.io.Serializable;

public class TokenValidationResponse implements Serializable {

    private static final long serialVersionUID = -83598585984583L;

    private String status;

    private String userName;


    private String role;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
