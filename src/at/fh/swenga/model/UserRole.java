package at.fh.swenga.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import at.fh.swenga.model.User;


 
@Entity
@Table(name = "user_roles")
public class UserRole implements java.io.Serializable {
	private static final long serialVersionUID = 8098173157518993615L;
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	@ManyToMany(mappedBy = "userRoles", fetch = FetchType.EAGER)
	private List<User> users;
 
	@Column(name = "role", nullable = false, length = 45)
	private String role;
 
	public UserRole() {
		// TODO Auto-generated constructor stub
	}
 
	public UserRole(String role) {
		super();
		this.role = role;
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	
 
	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getRole() {
		return role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
 
}