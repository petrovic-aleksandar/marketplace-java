package me.aco.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import me.aco.enums.TransferType;

@Entity
@NamedQueries({
		@NamedQuery(name = Transfer.getByUser, query = "select t from Transfer t where t.seller = :user or t.buyer = :user") })
public class Transfer {

	public static final String getByUser = "getTransfersByUser";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_seq_gen")
	@SequenceGenerator(name = "transfer_seq_gen", sequenceName = "transfer_seq")
	private long id;
	private double amount;
	private LocalDateTime createdAt;
	private TransferType type;
	@ManyToOne
	private User buyer;
	@ManyToOne
	private User seller;
	@ManyToOne
	private Item item;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public TransferType getType() {
		return type;
	}

	public void setType(TransferType type) {
		this.type = type;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
