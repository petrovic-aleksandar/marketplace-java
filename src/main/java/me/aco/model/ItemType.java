package me.aco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = ItemType.getAll, query = "select it from ItemType it"),
	@NamedQuery(name = ItemType.getById, query = "select it from ItemType it where it.id = :id")
})
public class ItemType {

	public static final String getAll = "GetAllItemTypes";
	public static final String getById = "GetItemTypeById";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_type_seq_gen")
	@SequenceGenerator(name = "item_type_seq_gen", sequenceName = "item_type_seq")
	private long id;
	private String name;
	private String description;
	private String imagePath;

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}