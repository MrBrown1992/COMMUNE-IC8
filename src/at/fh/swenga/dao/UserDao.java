package at.fh.swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.User;


@Repository
@Transactional
public class UserDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	
	public void persist(User user) {
		entityManager.persist(user);
	}

}
