package me.aco.endpoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
	public Response getByItemId(@PathParam("itemId") long itemId) {
		Item item = itemService.getById(itemId);
		if (item == null)
			return Response.serverError().build();
		List<Image> images = imageService.getByItem(item);
		List<String> paths = new ArrayList<String>();
		images.forEach(x -> paths.add(x.getPath()));
		return Response.ok(paths).build();
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
		System.out.println(uploadedFileLocation);
		try {
			Files.createDirectories(java.nio.file.Path.of(imageDir));
			File objFile = new File(uploadedFileLocation);
			if (objFile.exists())
				objFile.delete();
			saveToFile(file.getContent(), uploadedFileLocation);
			Image image = imageService.add(file.getFileName().get(), item);
			return Response.ok(image).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

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
