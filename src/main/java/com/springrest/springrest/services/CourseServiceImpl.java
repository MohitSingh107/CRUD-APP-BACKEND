package com.springrest.springrest.services;
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
import com.springrest.springrest.dao.CourseDao;
import com.springrest.springrest.model.Course;
import com.springrest.springrest.model.Info;
import com.springrest.springrest.model.Page;

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

	@Override
	public List<Course> sortByName(String title) {

		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    
		    Root<Course> course = cq.from(Course.class);
		   cq.orderBy(cb.asc(course.get("title")));
		
		   CriteriaQuery<Course> select = cq.select(course);  
	          TypedQuery<Course> q = entityManager.createQuery(select);  
	          return q.getResultList();
	}



	@Override
	public Page pagination(Info info) {

		   CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		    Root<Course> course = cq.from(Course.class);
		    CriteriaQuery<Course> select = cq.select(course);
		    
		    CriteriaQuery<Long> cq1 = cb.createQuery(Long.class);
		    cq1.select(cb.count(cq1.from(Course.class)));
		    entityManager.createQuery(cq1);
		    Long count = entityManager.createQuery(cq1).getSingleResult();
		    
		    if(info.getSearchitem()!=null&&!info.getSearchitem().isEmpty()) {
		    	Predicate predicate1=cb.like(course.get("title"),"%"+ info.getSearchitem()+"%"); 			
		    	cq.where(predicate1);
		    }
		    
		    Query queryTotal = entityManager.createQuery
		    	    ("Select count(f.id) from Course f");
		    	long countResult = (long)queryTotal.getSingleResult();
		    	int pageSize = info.getLimit();
		    	int pageNumber = (int) ((countResult / pageSize) + 1);
		    
		    
		    
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
		        
		        
		        
		        
//  List<Course> result = entityManager.createQuery(cq).setFirstResult((info.getPageSize()-1)*info.getLimit()).setMaxResults(info.getLimit()).getResultList();

		        return new Page(result,count.intValue(),pageNumber,info.getPageSize(),info.getLimit());
		    }

	}
 
//CriteriaBuilder builder = em.getCriteriaBuilder();
//CriteriaQuery<Brand> cQuery = builder.createQuery(Brand.class);
//Root<Brand> from = cQuery.from(Brand.class);
//CriteriaQuery<Brand> select = cQuery.select(from);
//.
//.
//Created many predicates and added to **Predicate[] pArray**
//.
//.
//CriteriaQuery<Long> cq = builder.createQuery(Long.class);
//cq.select(builder.count(cq.from(Brand.class)));
// Following line if commented causes [org.hibernate.hql.ast.QuerySyntaxException: Invalid path: 'generatedAlias1.enabled' [select count(generatedAlias0) from xxx.yyy.zzz.Brand as generatedAlias0 where ( generatedAlias1.enabled=:param0 ) and ( lower(generatedAlias1.description) like :param1 )]]
//em.createQuery(cq);
//cq.where(pArray);
//Long count = em.createQuery(cq).getSingleResult();
//.
//.
//select.where(pArray);
//.
//.
// Added orderBy clause
//TypedQuery typedQuery = em.createQuery(select);
//typedQuery.setFirstResult(startIndex);
//typedQuery.setMaxResults(pageSize);
//List resultList = typedQuery.getResultList()
//
//



