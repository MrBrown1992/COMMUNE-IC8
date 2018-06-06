package at.fh.swenga.model;

import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.fh.swenga.model.UserRole;

@Entity
@Table(name = "user")

public class User implements java.io.Serializable {

	@Id
	@Column(name = "username", length = 35)
	private String username;
	@Column(name = "password", length = 35)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "firstname", nullable = false, length = 35)
	private String firstname;
	@Column(name = "lastname", nullable = false, length = 35)
	private String lastname;

	@Column(name = "mobilenumber", nullable = true, length = 15)
	private int mobilenumber;

	@Column(name = "email", nullable = true, length = 65)
	private String email;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<UserRole> userRoles;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "attacheduser")
	private Image userimage;

	public User() {
		/**
		 * @param username
		 * @param password
		 * @param firstname
		 * @param lastname
		 * @param mobilenumber
		 * @param email
		 * @param birthdate
		 */
	}

}
