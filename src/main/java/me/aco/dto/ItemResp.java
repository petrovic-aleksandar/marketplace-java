package me.aco.dto;

import java.time.format.DateTimeFormatter;

import me.aco.model.Item;
import me.aco.model.ItemType;

public class ItemResp {
	
	private long id;
	private String name;
	private String description;
	private double price;
	private ItemType type;
	private boolean active;
	private boolean deleted;
	private String createdAt;
	private UserResp seller;
	
	public ItemResp(Item item) {
		super();
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.type = item.getType();
		this.active = item.isActive();
		this.createdAt = item.getCreatedAt() != null ? item.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")) : "";
		this.seller = new UserResp(item.getSeller());	
	}
	
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public UserResp getSeller() {
		return seller;
	}
	public void setSeller(UserResp seller) {
		this.seller = seller;
	}

}
