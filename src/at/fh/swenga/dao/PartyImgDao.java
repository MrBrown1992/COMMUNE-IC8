/**
 * 
 */
package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.model.Grocery;
import at.fh.swenga.model.PartyImg;

/**
 * @author nikolaus Spieß
 *
 */

@Repository
@Transactional
public interface PartyImgDao extends JpaRepository<PartyImg, Integer> {
	
	public List<PartyImg> findAllByFlat_id(@Param("flat_id") int flat_id);


}
