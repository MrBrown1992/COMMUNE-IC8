package at.fh.swenga.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "todo")
public class Todo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1334712559936353221L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Column(name = "name", length = 512)
	private String name;
	
	@Column(name = "category", length = 512)
	private String category;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	public Todo(String name, String category, Date date) {
		super();
		this.name = name;
		this.category = category;
		this.date = date;
	}

	public Todo() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
