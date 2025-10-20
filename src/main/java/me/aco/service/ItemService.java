package me.aco.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.dto.ItemReq;
import me.aco.model.Item;
import me.aco.model.ItemType;
import me.aco.model.User;

@Stateless
public class ItemService {
	
	@Inject
	private EntityManager em;
	
	public Item getById(long id) {
		List<Item> list = em.createNamedQuery(Item.getById, Item.class).setParameter("id", id).getResultList();
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	public List<Item> getBySeller(long userId) {
		return em.createNamedQuery(Item.getBySellerId, Item.class).setParameter("userId", userId).getResultList();
	}
	
	public List<Item> getByType(ItemType type) {
		return em.createNamedQuery(Item.getByType, Item.class).setParameter("type", type).getResultList();
	}
	
	public List<ItemType> getTypes() {
		return em.createNamedQuery(ItemType.getAll, ItemType.class).getResultList();
	}
	
	public ItemType getTypeById(long id) {
		List<ItemType> list = em.createNamedQuery(ItemType.getById, ItemType.class).setParameter("id", id).getResultList();
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	public Item add(ItemReq req, ItemType type, User seller) {
		Item item = new Item();
		item.setName(req.getName());
		item.setDescription(req.getDescription());
		item.setPrice(req.getPrice());
		item.setType(type);
		item.setActive(true);
		item.setCreatedAt(LocalDateTime.now());
		item.setSeller(seller);
		em.persist(item);
		return item;
	}
	
	public Item update(Item item, ItemReq req, ItemType type, User seller) {
		item.setName(req.getName());
		item.setDescription(req.getDescription());
		item.setPrice(req.getPrice());
		item.setType(type);
		return em.merge(item);
	}
	
	public Item deactivate(Item item) {
		item.setActive(false);
		return em.merge(item);
	}
	
	public Item activate(Item item) {
		item.setActive(true);
		return em.merge(item);
	}
	
	public Item delete(Item item) {
		item.setDeleted(true);
		return em.merge(item);
	}

}
