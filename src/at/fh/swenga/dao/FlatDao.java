package at.fh.swenga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Flat;


@Repository
@Transactional
public interface FlatDao extends JpaRepository <Flat,Integer> {

	
	@Query("select f from Flat f where f.name= :name")
	public Flat findFirstByFlatName(@Param("name") String name);

	public Flat findFirstByid(int id);
	
}
