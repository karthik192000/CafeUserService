package com.cafe.user.controller;

import com.cafe.user.beans.ResponseBean;
import com.cafe.user.service.CafeUserService;
import com.cafe.user.util.CafeUserServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CafeUserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CafeUserServiceException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request){
        CafeUserServiceException cafeServiceException = (CafeUserServiceException) ex;
        return handleExceptionInternal(ex,new ResponseBean(cafeServiceException.getErrorMessage()),new HttpHeaders(),cafeServiceException.getStatus(),request);
    }
}
