package com.springrest.springrest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springrest.springrest.model.Course;
import com.springrest.springrest.services.CourseService;
import com.springrest.springrest.validator.CourseValidator;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MyController {
	
	
	@Autowired
	  private CourseValidator validator; //DEPENDENCY INJECTION FOR VALIDATOR
	
	@Autowired
	private CourseService courseService; //DEPENDENCY INJECTION FOR SERVICE

//AUTO-DATA BINDING	
	@InitBinder("Course")
	    public void initBinder(WebDataBinder binder) {
	    binder.setValidator(validator);
	    }

	
// "__"	
@GetMapping("/home")
public String home(){
return "this is home page";
}	


//HOME PAGE (LIST ALL COURSES)
@GetMapping("/courses")
public List<Course> getCourses(){
return this.courseService.ListAllCourses(); //USING CRITERIA QUERY ListAllCourses()
}


//VIEW BUTTON (READ A SPECIFIC COURSE FROM COURSE ID)  
@GetMapping("/courses/{courseId}")
public Course getCourse(@PathVariable String courseId) {
return this.courseService.getCourses(Long.parseLong(courseId));
}


//SORTING USING TITLE
@GetMapping("/courses/sort/{title}")
public List<Course> sortByName(@PathVariable("title") String title) {
return this.courseService.sortByName(title); 
}


//SEARCH-BUTTON-1 (READ A SPECIFIC COURSE FROM INPUTED TITLE)
@GetMapping("/courses/search-title/{title}")
public List<Course> findByCourseName(@PathVariable("title") String title) {
return this.courseService.findByCourseName(title); 
}


//SEARCH-BUTTON-2 (READ A SPECIFIC COURSE FROM INPUTED ID)
@GetMapping("/courses/search-id/{sno}")
public List<Course> findByCourseId(@PathVariable Long sno){	
return this.courseService.findByCourseId(sno); 
}


//ADD COURSE BUTTON (ADD NEW COURSE)
@PostMapping(path="/courses", produces = "application/json")
public ResponseEntity<?> addCourse(@Valid @RequestBody Course course,Errors errors) {
	validator.validate(course, errors); //USING CUSTOM VALIDATION
	if (errors.hasErrors()){
    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
  }
    return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
   }	


//UPDATE BUTTON (UPDATE SPECIFIC COURSE)
@PutMapping("/courses")
public ResponseEntity<?> updateCourse(@RequestBody Course course,Errors errors) {
	validator.putvalidate(course, errors); //USING CUSTOM VALIDATION
	if (errors.hasErrors()) {
    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
   }
    return new ResponseEntity<>(courseService.updateCourse(course), HttpStatus.CREATED); 
	}
	

//DELETE BUTTON (DELETE SPECIFIC COURSE)
@DeleteMapping("/courses/{courseId}")
public ResponseEntity<HttpStatus> deleteCourse(@PathVariable String courseId){
	try {
	this.courseService.deleteCourse(Long.parseLong(courseId));
	return new ResponseEntity<>(HttpStatus.OK);
  } catch (Exception e) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
 }


 


}
