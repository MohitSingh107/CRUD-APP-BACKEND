package com.springrest.springrest.dao;

import com.springrest.springrest.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Roles,Integer> {

}
