package com.springrest.springrest.services;

import java.util.List;

import com.springrest.springrest.model.Course;

public interface CourseService {

//JPA REPOSITORY
	
	public Course getCourses(long courseId);

	public Course addCourse(Course course);

	public void deleteCourse(long parseLong);

	public Course updateCourse(Course course);
	
//CRITERIA QUERY 
	
	List<Course> findByCourseName(String title);
	
	List<Course>findByCourseId(Long sno);
	
	List<Course>ListAllCourses();

	List<Course> sortByName(String title);

	
}
