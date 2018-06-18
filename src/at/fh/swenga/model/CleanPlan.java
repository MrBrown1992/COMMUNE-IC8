package at.fh.swenga.model;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "clean_pan")
public class CleanPlan {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "room", nullable = false)
	private String room;
	@Column(name = "user", nullable = false)
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DayofCleaning",nullable = false)
	private Date doc;
	/*
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cleanplan")
	private Flat flat;
*/
	public CleanPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * @param room
	 * @param user
	 * @param doc
	 */
	public CleanPlan(String room, User user, Date doc) {
		super();
		this.room = room;
		this.user = user;
		this.doc = doc;
	}

	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the doc
	 */
	public Date getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Date doc) {
		this.doc = doc;
	}
	/**
	 * @return the flat
	 
	
	public Flat getFlat() {
		return flat;
	}

	/**
	 * @param flat the flat to set
	
	public void setFlat(Flat flat) {
		this.flat = flat;
	}
	*/
}
