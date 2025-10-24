package me.aco.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
	
	public Image getById(long id) {
		List<Image> list = em.createNamedQuery(Image.getById, Image.class).setParameter("id", id).getResultList();
		return list.size()>0 ? list.get(0) : null;
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
	
	public boolean delete(Image image) {
		String imagesDir = System.getProperty("marketplace.imagesdirectory");
		String imageDir = imagesDir + image.getItem().getSeller().getId() + File.separatorChar + image.getItem().getId() + File.separatorChar;
		String fileLocation = imageDir + image.getPath();
		try {
			Files.deleteIfExists(Path.of(fileLocation));
			em.remove(image);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
