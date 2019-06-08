package com.supernovaic.spring.rest.service;

import com.supernovaic.spring.rest.client.UsersFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersFeignClient usersFeignClient;

    @Autowired
    public UsersServiceImpl(UsersFeignClient usersFeignClient) {
        this.usersFeignClient = usersFeignClient;
    }

    public boolean userExists(String username) {
        return (usersFeignClient.getUser(username) == null) ? true : false;
    }
}
