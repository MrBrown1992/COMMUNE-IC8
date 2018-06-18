package at.fh.swenga.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import at.fh.swenga.model.User;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {

	public User findFirstByUsername(String username);
	
	public User findFirstByid(int id);
}
