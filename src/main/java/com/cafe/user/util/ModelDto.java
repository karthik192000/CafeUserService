package com.cafe.user.util;
import com.cafe.user.beans.CreateUserRequest;
import com.cafe.user.model.User;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ModelDto {

    public static User toModel(CreateUserRequest createUserRequest){
        User user = null;
        if(createUserRequest != null){
           user  = new User(createUserRequest.getUserName(),createUserRequest.getName(),
                    createUserRequest.getUserRole(),createUserRequest.getPassword());

        }
        return user;
    }
}

