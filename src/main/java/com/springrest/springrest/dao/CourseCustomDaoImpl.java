package com.springrest.springrest.dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springrest.springrest.model.Course;
import com.springrest.springrest.model.Info;
import com.springrest.springrest.model.Page;
import com.springrest.springrest.services.CourseService;

@Service
public class CourseCustomDaoImpl implements CourseService {
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
	public List<Course> ListAllCourses() {

		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    
		    cq.from(Course.class);
		   
			TypedQuery<Course> query=entityManager.createQuery(cq);
			
			return query.getResultList();
	}


	@Override
	public Page pagination(Info info) {
		Long count = null;
		int pageNumber = 0;
		boolean check=false;
		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    Root<Course> course = cq.from(Course.class);
		    CriteriaQuery<Course> select = cq.select(course);
   	
		    if(info.getSearchitem()!=null&&!info.getSearchitem().isEmpty()) {
		    	Predicate predicate1=cb.like(course.get("title"),"%"+ info.getSearchitem()+"%");
		    	//Predicate predicate2=cb.like(course.get("technology"),"%"+ info.getSearchitem()+"%");
		    	Predicate resfinal=cb.or(predicate1);
		    	cq.where(resfinal);
		    	
		    	   CriteriaBuilder cb1 = entityManager.getCriteriaBuilder();
		    	CriteriaQuery<Long> cq1 = cb1.createQuery(Long.class);
		    	cq1.select(cb1.count(cq1.from(Course.class)));
		    	entityManager.createQuery(cq1);
		    	cq1.where(predicate1);
		    	 count = entityManager.createQuery(cq1).getSingleResult();
		    	 int pageSize = info.getLimit();
			    
		    	 if(count!=pageSize) {	
		    	 pageNumber = (int) ((count / pageSize) + 1);
			    	 	System.out.println(count);
			    	 	 System.out.println(pageNumber);
			    }else {
			    	 pageNumber = (int) ((count / pageSize));
			    	 	System.out.println(count);
			    	 	 System.out.println(pageNumber);
			    	}
			    	 	 check=true;
		    
		  }
		    	Order order;
		        if (info.getShortField() != null && !info.getShortField().isEmpty()) {
		            if (info.getShortType().equals("desc") ) {
		                order = cb.desc(course.get(info.getShortField()));
		            } else {
		                order = cb.asc(course.get(info.getShortField()));
		            }
		        } else {
		            order = cb.asc(course.get("sno"));
		        }
		        cq.orderBy(order);
		        
		        TypedQuery<Course> typedQuery = entityManager.createQuery(select);
		        typedQuery.setFirstResult((info.getPageSize()-1)*info.getLimit());
		        typedQuery.setMaxResults(info.getLimit());
		        List<?>result = typedQuery.getResultList();
		       
		       if(check==false) {
		        Query queryTotal = entityManager.createQuery
			    	    ("Select count(f.id) from Course f");
			    	 count = (long)queryTotal.getSingleResult();
			    	int pageSize = info.getLimit();
			    	if(count==pageSize) {
			    		 pageNumber = (int) ((count / pageSize));
			    	}else {
			    	 pageNumber = (int) ((count / pageSize) + 1);
			    	 System.out.println(pageNumber);
			    	} 
			    	 
		       }
		       
		        
		        
//  List<Course> result = entityManager.createQuery(cq).setFirstResult((info.getPageSize()-1)*info.getLimit()).setMaxResults(info.getLimit()).getResultList();

		        return new Page(result,count.intValue(),pageNumber,info.getPageSize(),info.getLimit());
		    }

	}
 
