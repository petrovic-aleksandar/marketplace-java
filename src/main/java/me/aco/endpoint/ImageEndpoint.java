package me.aco.endpoint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.model.Image;
import me.aco.model.Item;
import me.aco.service.ImageService;
import me.aco.service.ItemService;

@Path("Image")
public class ImageEndpoint {

	@Inject
	private ItemService itemService;
	@Inject
	private ImageService imageService;

	@GET
	@Path("/{itemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByItemId(@PathParam("itemId") long itemId) {
		Item item = itemService.getById(itemId);
		if (item == null)
			return Response.serverError().build();
		List<Image> images = imageService.getByItem(item);
		return Response.ok(images).build();
	}
	
	@POST
	@Path("/{itemId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response add(@PathParam("itemId") long itemId, @FormParam("name") String name, @FormParam("file") EntityPart file) {
		Item item = itemService.getById(itemId);
		if (item == null)
			return Response.serverError().build();
		
		@SuppressWarnings("unused")
		String homeDir = System.getProperty("jboss.home.dir");
		String imagesDir = System.getProperty("marketplace.imagesdirectory");
		String imageDir = imagesDir + item.getSeller().getId() + File.separatorChar + item.getId() + File.separatorChar;
		String uploadedFileLocation = imageDir + file.getFileName().get();
		try {
			Files.createDirectories(java.nio.file.Path.of(imageDir));
			File objFile = new File(uploadedFileLocation);
			if (objFile.exists())
				objFile.delete();
			imageService.saveToFile(file.getContent(), uploadedFileLocation);
			Image image = imageService.add(file.getFileName().get(), item);
			return Response.ok(image).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/front/{imageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeImageFront(@PathParam("imageId") long imageId) {
		Image image = imageService.getById(imageId);
		if (image == null)
			return Response.status(409).build();
		Image updatedImage = imageService.makeFrontImage(image, image.getItem());
		if (updatedImage != null)
			return Response.ok(updatedImage).build();
		return Response.serverError().build();
	}
	
	@DELETE
	@Path("/{imageId}")
	public Response delete( @PathParam("imageId") long imageId) {
		Image image = imageService.getById(imageId);
		if (image == null)
			return Response.status(409).build();
		boolean deleted = imageService.delete(image);
		if (deleted) 
			return Response.ok().build();
		else
			return Response.serverError().build();
	}
	
}
