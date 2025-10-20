package me.aco.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = Item.getBySellerId, query = "select i from Item i where i.deleted = false and i.seller.id = :userId order by i.id desc"),
		@NamedQuery(name = Item.getById, query = "select i from Item i where i.deleted = false and i.id = :id"),
		@NamedQuery(name = Item.getByType, query = "select i from Item i where i.deleted = false and i.type = :type order by i.id desc")})
public class Item {

	public static final String getBySellerId = "getItemsBySellerId";
	public static final String getById = "getItemById";
	public static final String getByType = "getItemsByType";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
	@SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq")
	private long id;
	private String name;
	private String description;
	private double price;
	@ManyToOne
	private ItemType type;
	private boolean active;
	private boolean deleted;
	@JsonbDateFormat("dd-MM-yyyy")
	private LocalDateTime createdAt;
	@ManyToOne
	private User seller;

	@OneToMany(mappedBy = "item")
	private List<Image> images;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
