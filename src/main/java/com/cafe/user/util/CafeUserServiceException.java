package com.cafe.user.util;

import org.springframework.http.HttpStatus;

public class CafeUserServiceException extends RuntimeException{

    private String errorMessage;

    private HttpStatus status;

    public CafeUserServiceException(){
        super();
        //Empty Constructor
    }

    public CafeUserServiceException(String errorMessage,HttpStatus status){
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
