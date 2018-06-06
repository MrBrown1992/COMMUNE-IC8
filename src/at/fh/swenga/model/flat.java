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
public class flat {

@Id
@Column(name = "id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private String id;

private String name;

@OneToMany(mappedBy = "flat", fetch = FetchType.EAGER)
private Set<user> users;

@Version
long version;

public flat(String name) {
super();
this.name = name;

}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Set<user> getUsers() {
return users;
}

public void setUsers(Set<user> users) {
this.users = users;
}


public void addUsers(user user) {
if(users == null){
users = new HashSet<user>();
}
users.add(user);
}


}