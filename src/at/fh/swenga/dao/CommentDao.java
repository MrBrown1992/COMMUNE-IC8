package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Comment;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Integer> {
	
	public List<Comment> findAllByUser_id(@Param("flat_id") int flat_id);
	
	
					
}
