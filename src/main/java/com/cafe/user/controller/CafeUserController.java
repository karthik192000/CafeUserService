package com.cafe.user.controller;


import com.cafe.user.beans.CreateUserRequest;
import com.cafe.user.beans.LoginRequest;
import com.cafe.user.beans.ResponseBean;
import com.cafe.user.beans.SignUpResponse;
import com.cafe.user.beans.TokenResponse;
import com.cafe.user.service.CafeUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Register New User", notes = "Register New User",httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 201,message = "User Registered Successfully",response = SignUpResponse.class),
            @ApiResponse(code = 406,message = "Username not available",response = ResponseBean.class)
    })
    @CrossOrigin
    @PostMapping(path = "/signup",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(cafeUserService.signUp(createUserRequest), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Log In", notes = "Login",httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 202,message = "User Logged In Successfully",response = TokenResponse.class),
            @ApiResponse(code = 401,message = "Authentication Failed",response = ResponseBean.class)
    })
    @CrossOrigin
    @PostMapping(path = "/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(cafeUserService.login(loginRequest), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Validate Token", notes = "Validate Token",httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "authtoken",dataType = "String",paramType = "header",required = true)})
    @ApiResponses({
            @ApiResponse(code = 200,message = "Token Validation Successfull",response = TokenResponse.class),
            @ApiResponse(code= 500,message = "Exception occurred during token validation",response = ResponseBean.class)
    })
    @CrossOrigin
    @PostMapping(path = "/validate",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateToken(@RequestHeader HttpHeaders httpHeaders){
        return new ResponseEntity<>(cafeUserService.validateToken(httpHeaders),HttpStatus.OK);
    }


}
