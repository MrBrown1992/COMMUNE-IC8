package at.fh.swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Grocery;

@Repository
@Transactional
public class GroceryDao {

	@PersistenceContext
	protected EntityManager entityManager;

	public List<Grocery> getGroceries() {
		TypedQuery<Grocery> typedQuery = entityManager.createQuery("select g from Grocery g", Grocery.class);
		List<Grocery> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}

	public Grocery getGrocery(int i) throws DataAccessException {
		return entityManager.find(Grocery.class, i);
	}

	
	
	public void persist(Grocery grocery) {
		entityManager.persist(grocery);
	}

	public Grocery merge(Grocery grocery) {
		return entityManager.merge(grocery);
	}

	public void delete(Grocery grocery) {
		entityManager.remove(grocery);
	}

	public void delete(int id) {
		Grocery grocery = getGrocery(id);
		if (grocery != null)
			delete(grocery);
	}

	public static Grocery getGroceryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void addGrocery(@Valid Grocery newGrocery) {
		// TODO Auto-generated method stub
		
	}

}
