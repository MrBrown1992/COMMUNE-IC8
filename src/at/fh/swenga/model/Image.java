package at.fh.swenga.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "image")
public class Image implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
	private User attacheduser;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "img")
	private byte[] img;
	
	@Version
	long version;

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Image(int id, User attacheduser, byte[] img) {
		super();
		this.id = id;
		this.attacheduser = attacheduser;
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getAttacheduser() {
		return attacheduser;
	}

	public void setAttacheduser(User attacheduser) {
		this.attacheduser = attacheduser;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}
	
	

	
	
}
