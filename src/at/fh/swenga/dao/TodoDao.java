package at.fh.swenga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Todo;

@Repository
@Transactional
public interface TodoDao extends JpaRepository <Todo,Integer> {

	Todo findFirstByid(int id);

	

	
	
	
}
