package at.fh.swenga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class flat {
	
	@Id
	@Column(name = "id")
	private String id; 
}
