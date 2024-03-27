package com.cafe.user.controller;


import com.cafe.user.beans.CreateUserRequest;
import com.cafe.user.beans.LoginRequest;
import com.cafe.user.service.CafeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CafeUserController {


    @Autowired
    CafeUserService cafeUserService;

    @CrossOrigin
    @PostMapping(path = "/signup",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(cafeUserService.signUp(createUserRequest), HttpStatus.CREATED);
    }


    @CrossOrigin
    @PostMapping(path = "/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(cafeUserService.login(loginRequest), HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @PostMapping(path = "/validate",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateToken(@RequestHeader HttpHeaders httpHeaders){
        return new ResponseEntity<>(cafeUserService.validateToken(httpHeaders),HttpStatus.OK);
    }


}
