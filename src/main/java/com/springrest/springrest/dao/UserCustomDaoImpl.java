package com.springrest.springrest.dao;

import com.springrest.springrest.model.Roles;
import com.springrest.springrest.model.User;
import com.springrest.springrest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCustomDaoImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;


    @Override
    public User addUserRole(User user) {
       User user2=modelMapper.map(user,User.class);
       user2.setPassword(passwordEncoder.encode(user2.getPassword()));

           Roles roles= this.roleDao.findById(402).get();
           user2.getRole().add(roles);

        User newUser = userDao.save(user2);
        return modelMapper.map(newUser,User.class);
    }

    @Override
    public List<User> ListAllUsers() {
        return userDao.findAll();
    }
}
