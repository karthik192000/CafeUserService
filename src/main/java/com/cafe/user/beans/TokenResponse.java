package com.cafe.user.beans;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    private static final long serialVersionUID = -9238925895L;


    private String userName;

    private String userRole;

    private String token;


    public TokenResponse(String userName, String userRole, String token) {
        this.userName = userName;
        this.userRole = userRole;
        this.token = token;
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
}
