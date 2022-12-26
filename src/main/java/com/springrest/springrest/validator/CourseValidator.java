package com.springrest.springrest.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.springrest.springrest.controller.MyController;
import com.springrest.springrest.dao.CourseDao;
import com.springrest.springrest.model.Course;
@ControllerAdvice(assignableTypes = MyController.class)
public class CourseValidator implements Validator {
	@Autowired
	private CourseDao dao;

	@Override
	public boolean supports(Class<?> clazz) {
		return Course.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		Course course=(Course) target;
		
//SERIAL NUMBER AMBIGUITY CHECK FOR ADD METHOD
		if (dao.existsById(course.getSno())==true) {
		errors.rejectValue("sno", "try again","Serial Number already exists in db");
		}
		
//NORMAL CHECK FOR ADD METHOD
		
		
		//SERIAL NUMBER
		if (course.getSno()<=0) {
		errors.rejectValue("sno", "serial.emp.neg","Serial Number --> zero/negative");
		}
		
		
		//TITLE	
		if (course.getTitle()==null) {
		errors.rejectValue("title", "name is empty","Title cannot be empty");
		 }
			
		
		//TECHNOLOGY
		if (course.getTechnology()== null ) {
		errors.rejectValue("technology", "tech.null","technology cannot be empty");
		 }
		
		
		//PROVIDER	
		if (course.getProvider().getCompId()<=0||course.getProvider().getCompName()==null) {
	    errors.rejectValue("provider", "check provider","name--> null " + " id -->zero/negative");
	     }		
		
		
		//PRICE
		if (course.getPrice()!= 0 && course.getPrice()< 0) {
	    errors.rejectValue("price", "invalid price","price cannot be 0/negative");
	      }				
	}
	
	
	//CUSTOM METHOD FOR VALIDATIONG SERIAL NUMBER 
	public void putvalidate(Object target,Errors errors) {
		Course cd=(Course) target;
		
		if (dao.existsById(cd.getSno())==false) {
			errors.rejectValue("sno", "try again","Serial Number dont exists in db");
		    }
		
	}
	
}