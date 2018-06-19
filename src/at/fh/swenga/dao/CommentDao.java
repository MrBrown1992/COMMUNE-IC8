package at.fh.swenga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Comment;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Integer> {

}
