package at.fh.swenga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Flat {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String name;

	@OneToMany(mappedBy = "flat", fetch = FetchType.EAGER)
	private Set<User> users;

	@Version
	long version;

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

}