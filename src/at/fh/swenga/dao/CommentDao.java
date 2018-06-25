package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Comment;
import at.fh.swenga.model.Flat;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Integer> {
	
	public List<Comment> findAllByUser_id(@Param("flat_id") int flat_id);


	public Comment findFirstByid(int id);

	public List<Comment> findAllByFlat(Flat flat);

	//public List<Comment> findAllByFlat_id();

	//@Query("select c from comment c, flat f, user u where f.flatid == u.flatid ")
	//public List<Comment> findAllByFlatid();
	
	
					

}
