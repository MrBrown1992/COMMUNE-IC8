package at.fh.swenga.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3534057603171743878L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Column(name = "name", length = 512)
	private String name;
	
	
	
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Set<Todo> todos;



	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Category(String name) {
		super();
		this.name = name;
		
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Set<Todo> getTodos() {
		return todos;
	}



	public void setTodos(Set<Todo> todos) {
		this.todos = todos;
	}



	@Override
	public String toString() {
		return name ;
	}



	
	
	
	
}

