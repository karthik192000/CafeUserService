package com.cafe.user.beans;

import java.io.Serializable;

public class ResponseBean implements Serializable {

    private static final long serialVersionUID = -92879238593458L;

    private String errorMessage;

    public ResponseBean(){
        // Empty Constructor
    }

    public ResponseBean(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
