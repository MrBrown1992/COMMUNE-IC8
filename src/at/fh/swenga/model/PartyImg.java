/**
 * 
 */
package at.fh.swenga.model;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author nik
 *
 */

@Entity
@Table(name = "party_img")
public class PartyImg implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "img")
	private byte[] img;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="Date")
	@Temporal(TemporalType.DATE)
	private Calendar uploadDate;
	@Version
	long version;

	
	
	/**
	 * @param img
	 * @param filename
	 * @param uploadDate
	 */
	public PartyImg( byte[] img, String filename, Calendar uploadDate) {
		super();
	
		this.img = img;
		this.filename = filename;
		this.uploadDate = uploadDate;
	}



	/**
	 * 
	 */
	public PartyImg() {
		// TODO Auto-generated constructor stub
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
	 * @return the img
	 */
	public byte[] getImg() {
		return img;
	}



	/**
	 * @param img the img to set
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}



	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}



	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}



	/**
	 * @return the uploadDate
	 */
	public Calendar getUploadDate() {
		return uploadDate;
	}



	/**
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(Calendar uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	

}
