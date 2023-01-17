package com.springrest.springrest.services;

import java.util.List;

import com.springrest.springrest.model.Course;
import com.springrest.springrest.model.Info;
import com.springrest.springrest.model.Page;

public interface CourseService {

//JPA REPOSITORY
	
	public Course getCourses(long courseId);

	public Course addCourse(Course course);

	public void deleteCourse(long parseLong);

	public Course updateCourse(Course course);
	
//CRITERIA QUERY 
	
	List<Course>ListAllCourses();

	Page pagination(Info info);

}
