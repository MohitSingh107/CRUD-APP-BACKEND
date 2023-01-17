package com.springrest.springrest.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.springrest.springrest.model.Course;

public interface CourseDao extends JpaRepository<Course, Long>{

	//SELECT NATIVE QUERY 1
	@Query(value = "SELECT * FROM course",nativeQuery = true)
	public List<Course> getAllCourses();

	//SEARCH NATIVE QUERY 2
	@Query(value = "SELECT * FROM course WHERE title LIKE %?1%",nativeQuery = true)
	public List<Course> searchAll(String s);

	//NATIVE QUERY 3
	@Query(value = "SELECT * FROM course  WHERE sno=?1", nativeQuery = true)
	List<Course> findByPublished (Long id);

}
