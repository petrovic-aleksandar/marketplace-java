package me.aco.dto;

import java.time.format.DateTimeFormatter;

import me.aco.model.Transfer;

public class TransferResp {
	
	private long id;
	private double amount;
	private String time;
	private String type;
	private UserResp buyer;
	private UserResp seller;
	private ItemResp item;
	
	public TransferResp(Transfer transfer) {
		id = transfer.getId();
		amount = transfer.getAmount();
		time = transfer.getCreatedAt() != null ? transfer.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")) : "";
		type = transfer.getType().toString();
		buyer = new UserResp(transfer.getBuyer());
		seller = new UserResp(transfer.getSeller());
		item = new ItemResp(transfer.getItem());
	}
		
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserResp getBuyer() {
		return buyer;
	}
	public void setBuyer(UserResp buyer) {
		this.buyer = buyer;
	}
	public UserResp getSeller() {
		return seller;
	}
	public void setSeller(UserResp seller) {
		this.seller = seller;
	}
	public ItemResp getItem() {
		return item;
	}
	public void setItem(ItemResp item) {
		this.item = item;
	}

}
