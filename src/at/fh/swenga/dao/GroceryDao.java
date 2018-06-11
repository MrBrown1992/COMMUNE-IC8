package at.fh.swenga.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import at.fh.swenga.model.Grocery;


@Repository
@Transactional
public interface GroceryDao extends JpaRepository<Grocery, Integer> {
	
	
	
	
	
	public Grocery findFirstByid(int id);
	

	

}
