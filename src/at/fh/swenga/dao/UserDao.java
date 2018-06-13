package at.fh.swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.User;


@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer>{
	
	
	public User findFirstByUsername(String username);
}
