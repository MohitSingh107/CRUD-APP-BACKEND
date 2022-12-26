package com.springrest.springrest.services;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.springrest.dao.CourseDao;
import com.springrest.springrest.model.Course;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao coursedao;
	
	@Override
	public Course getCourses(long courseId) {
			
				
	return coursedao.findById(courseId).get();
	}
	
	@Override
	public Course addCourse(Course course) {
		return coursedao.save(course);
		 
	}
	
	@Override
	public void deleteCourse(long parseLong) {
	
		coursedao.deleteById(parseLong);
	}

	@Override
	public Course updateCourse(Course course) {
			return coursedao.save(course);
			
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@PersistenceContext
	 private EntityManager entityManager;

	
	@Override
	public List<Course> findByCourseName(String title) {
		
		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    
		    Root<Course> course = cq.from(Course.class);
		    Predicate CourseNamePredicate=cb.equal(course.get("title"),title);
		    
		    cq.where(CourseNamePredicate);
		    TypedQuery<Course> query=entityManager.createQuery(cq);
		
		
		
		return query.getResultList();
	}


	@Override
	public List<Course> findByCourseId(Long sno) {

		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    
		    Root<Course> course = cq.from(Course.class);
		    Predicate CoursesnoPredicate=cb.equal(course.get("sno"),sno);
		    
		    cq.where(CoursesnoPredicate);
		TypedQuery<Course> query=entityManager.createQuery(cq);
		
		return query.getResultList();
	
	}


	@Override
	public List<Course> ListAllCourses() {

		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    
		    cq.from(Course.class);
		   
			TypedQuery<Course> query=entityManager.createQuery(cq);
			
			return query.getResultList();
	}		
	
}
