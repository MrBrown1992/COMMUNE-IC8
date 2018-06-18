package at.fh.swenga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name = "flat")
public class Flat  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9004782543943650955L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "flat", fetch = FetchType.EAGER)
	private Set<User> users;
	
	/*
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flatToClean")
	private CleanPlan cleanplan;
*/
	@Version
	long version;

	
	

	public Flat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Flat(String name) {
		super();
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUsers(User user) {
		if (users == null) {
			users = new HashSet<User>();
		}
		users.add(user);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}