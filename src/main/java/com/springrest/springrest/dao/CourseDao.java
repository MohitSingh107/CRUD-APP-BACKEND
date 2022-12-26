package com.springrest.springrest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springrest.springrest.model.Course;


public interface CourseDao extends JpaRepository<Course, Long>{

}
