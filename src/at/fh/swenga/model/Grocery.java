package at.fh.swenga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grocery")
public class Grocery {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "name", nullable = false, length = 60)
	private String name;
	@Column(name = "bought", nullable = false)
	private boolean bought;
	
	public Grocery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grocery(String name, boolean bought) {
		super();
		this.name = name;
		this.bought = bought;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bought
	 */
	public boolean isBought() {
		return bought;
	}

	/**
	 * @param bought the bought to set
	 */
	public void setBought(boolean bought) {
		this.bought = bought;
	}

	
}
