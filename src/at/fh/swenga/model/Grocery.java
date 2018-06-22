package at.fh.swenga.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grocery")
public class Grocery implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4105617388524643455L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "groceryName", nullable = false, length = 60)
	public String groceryName;
	



	@Column(name = "bought")
	private boolean bought;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name ="flat_id")
	private Flat flat; 
		
	public Grocery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grocery(String groceryName, boolean bought) {
		
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
	
	
	
	




	/**
	 * @return the flat
	 */
	public Flat getFlat() {
		return flat;
	}

	/**
	 * @param flat the flat to set
	 */
	public void setFlat(Flat flat) {
		this.flat = flat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grocery other = (Grocery) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
