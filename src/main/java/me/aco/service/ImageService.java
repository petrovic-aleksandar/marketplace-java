package me.aco.service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.model.Image;
import me.aco.model.Item;

@Stateless
public class ImageService {
	
	@Inject
	private EntityManager em;
	
	public Image getFrontImageForItem(Item item) {
		List<Image> list = em.createNamedQuery(Image.getFrontForItem, Image.class).setParameter("item", item).getResultList();
		return list.size()>0 ? list.get(0) : null;
	}
	
	public List<Image> getByItem(Item item) {
		return em.createNamedQuery(Image.getByItem, Image.class).setParameter("item", item).getResultList();
	}
	
	public Image add(String path, Item item) {
		Image image = new Image();
		image.setPath(path);
		image.setItem(item);
		image.setFront(getFrontImageForItem(item)==null);
		em.persist(image);
		return image;
	}
	
	public Image makeFrontImage(Image image, Item item) {
		Image front = getFrontImageForItem(item);
		front.setFront(false);
		image.setFront(true);
		em.merge(front);
		image = em.merge(image);
		return image;
	}

}
