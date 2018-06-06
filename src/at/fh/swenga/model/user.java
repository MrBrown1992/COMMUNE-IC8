package at.fh.swenga.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "user")

public class user implements java.io.Serializable {

	@Id
	@Column(name = "username", length = 35)
	private String username;
	@Column(name="password",length = 32)
	private String password;
	@Column(name = "firstname", nullable = false, length = 35)
	private String firstname;
	@Column(name = "lastname", nullable = false, length = 35)
	private String lastname;

	
	public user() {
		/**
		 * @param username
		 * @param password
		 * @param firstname
		 * @param lastname 
		 */
	}
}
