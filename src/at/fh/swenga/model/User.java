package at.fh.swenga.model;

import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

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
	@Column(name = "username", length = 45)
	private String username;
	@Column(name = "password", length = 60)
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

	public User(String username, String password, boolean enabled, String firstname, String lastname, int mobilenumber,
			String email, Date birthdate, Set<UserRole> userRoles, Image userimage) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mobilenumber = mobilenumber;
		this.email = email;
		this.birthdate = birthdate;
		this.userRoles = userRoles;
		this.userimage = userimage;
	}
	
	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the mobilenumber
	 */
	public int getMobilenumber() {
		return mobilenumber;
	}

	/**
	 * @param mobilenumber
	 *            the mobilenumber to set
	 */
	public void setMobilenumber(int mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate
	 *            the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the userRoles
	 */
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles
	 *            the userRoles to set
	 */
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public void addUserRole(UserRole userRole) {
		if (userRoles==null) userRoles = new HashSet<UserRole>();
		userRoles.add(userRole);
	}
	

	/**
	 * @return the userimage
	 */
	public Image getUserimage() {
		return userimage;
	}
	
	

	/**
	 * @param userimage
	 *            the userimage to set
	 */
	public void setUserimage(Image userimage) {
		this.userimage = userimage;
	}

	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
	}

}
