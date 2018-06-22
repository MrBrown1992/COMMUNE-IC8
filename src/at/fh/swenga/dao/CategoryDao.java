package at.fh.swenga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Category;
import at.fh.swenga.model.Flat;

@Repository
@Transactional
public interface CategoryDao extends JpaRepository<Category, Object>{

	public Category findFirstByid(int id);
	
	
}
