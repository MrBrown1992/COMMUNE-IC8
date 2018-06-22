package at.fh.swenga.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "todo")
public class Todo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1334712559936353221L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", length = 512)
	private String name;

	@ManyToOne( fetch= FetchType.EAGER)
	private Category category;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar date;

	public Todo(String name, Category category, Calendar date) {
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

	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Todo [date=" + date + "]";
	}
	
}
