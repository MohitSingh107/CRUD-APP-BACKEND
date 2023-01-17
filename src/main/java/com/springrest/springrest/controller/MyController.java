package com.springrest.springrest.controller;

import java.util.List;

import javax.validation.Valid;

import com.springrest.springrest.model.User;
import com.springrest.springrest.payload.JwtAuthRequest;
import com.springrest.springrest.payload.JwtAuthResponse;
import com.springrest.springrest.security.JwtTokenHelper;
import com.springrest.springrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import com.springrest.springrest.dao.CourseDao;
import com.springrest.springrest.model.Course;
import com.springrest.springrest.model.Info;
import com.springrest.springrest.model.Page;
import com.springrest.springrest.services.CourseService;
import com.springrest.springrest.validator.CourseValidator;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MyController {
	
	
	@Autowired
	  private CourseValidator validator; //DEPENDENCY INJECTION FOR VALIDATOR
	
	@Autowired
	private CourseService courseService; //DEPENDENCY INJECTION FOR SERVICE

	@Autowired
	private UserService userService;  //DEPENDENCY INJECTION FOR SERVICE
	
	@Autowired
	private CourseDao cd;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

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

//Native Query List All
@GetMapping("/showall")
public List<Course> showall(){
return cd.getAllCourses();
}	

//Native Query Search
@GetMapping("/search/{s}")
public List<Course> searchAll(@PathVariable String s){
return cd.searchAll(s);
}
//Native Query Search
@GetMapping("/find/{Id}")
public List<Course> searchAll(@PathVariable Long Id) {
		return cd.findByPublished(Id);
	}

//JWT Security//*******************************************************************
@PostMapping("/courses/login")
public  ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)
{
this.authenticate(request.getUsername(),request.getPassword());
	UserDetails userDetail= this.userDetailsService.loadUserByUsername(request.getUsername());
	String token=this.jwtTokenHelper.generateToken(userDetail);
	JwtAuthResponse response=new JwtAuthResponse();
	response.setToken(token);
	return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);

	}
//JWT Security//*******************************************************************	

	//HOME PAGE (LIST ALL COURSES)
@GetMapping("/courses")
public List<Course> getCourses(){
return this.courseService.ListAllCourses(); //USING CRITERIA QUERY ListAllCourses()
}

@GetMapping("/showuser")
public List<User>getUsers(){
		return this.userService.ListAllUsers();
}



//VIEW BUTTON (READ A SPECIFIC COURSE FROM COURSE ID)  
@GetMapping("/courses/{courseId}")
public Course getCourse(@PathVariable String courseId) {
return this.courseService.getCourses(Long.parseLong(courseId));
}

//Creating and Registering User/Role into DataBase
@PostMapping("/Signup")
public ResponseEntity<?> addUserandRole(@RequestBody User user) {
	try {
		this.userService.addUserRole(user);
		return new ResponseEntity<>("Created and Saved", HttpStatus.CREATED);
	} catch (Exception e) {
		return new ResponseEntity<>("Try Again",HttpStatus.BAD_REQUEST);
	}
	}

//SORTING,SEARCHING,PAGINATION
@PostMapping("/courses/pagination")
public Page pagination(@RequestBody Info info ){
	return this.courseService.pagination(info);
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
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/courses/{courseId}")
public ResponseEntity<?> deleteCourse(@PathVariable String courseId){
	try {
	this.courseService.deleteCourse(Long.parseLong(courseId));
	return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
  } catch (Exception e) {
	return new ResponseEntity<>("Caught with some Error",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
 }


 


}
