package com.springrest.springrest.services;

import com.springrest.springrest.model.User;

import java.util.List;

public interface UserService {

    public User addUserRole(User user);

    List<User> ListAllUsers();
}
