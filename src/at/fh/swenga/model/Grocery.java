package at.fh.swenga.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grocery")
public class Grocery implements Serializable{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "groceryName", nullable = false, length = 60)
	private String groceryName;
	@Column(name = "bought", nullable = false)
	private boolean bought;
	
	public Grocery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grocery(String groceryName, boolean bought) {
		super();
		this.groceryName = groceryName;
		this.bought = bought;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return groceryName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String groceryName) {
		this.groceryName = groceryName;
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
