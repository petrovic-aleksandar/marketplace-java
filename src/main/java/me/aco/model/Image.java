package me.aco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;

@Entity
@NamedQueries({@NamedQuery(name = Image.getBySellerId, query = "select im from Image im left join Item it on im.item = it where it.seller.id = :sellerId"),
		@NamedQuery(name = Image.getByTypeId, query = "select im from Image im left join Item it on im.item = it where it.type.id = :typeId"),
		@NamedQuery(name = Image.getFrontForItem, query = "select im from Image im where im.item = :item and im.front = true"),
		@NamedQuery(name = Image.getByItem, query = "select im from Image im where im.item = :item")})
public class Image {
	
	public static final String getBySellerId = "getImagesBySellerId";
	public static final String getByTypeId = "getImagesByTypeId";
	public static final String getByItem = "getImagesByItem";
	public static final String getFrontForItem = "getImageFrontForItem";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_gen")
	@SequenceGenerator(name = "image_seq_gen", sequenceName = "image_seq")
	private long id;
	private String path;
	@JsonIgnore
	@ManyToOne
	private Item item;
	private boolean front;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isFront() {
		return front;
	}

	public void setFront(boolean front) {
		this.front = front;
	}

}
