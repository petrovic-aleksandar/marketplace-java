package me.aco.endpoint;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.dto.ItemReq;
import me.aco.dto.ItemResp;
import me.aco.interfaces.JwtSecured;
import me.aco.model.Item;
import me.aco.model.ItemType;
import me.aco.model.User;
import me.aco.service.ItemService;
import me.aco.service.UserService;

@Path("Item")
public class ItemsEndpoint {
	
	@Inject
	private ItemService itemService;
	@Inject
	private UserService userService;
	
	@JwtSecured
	@Path("/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Item getById(@PathParam("id") long id) {
		return itemService.getById(id);
	}
	
	@JwtSecured
	@Path("/bySellerId/{userId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<ItemResp> getBySeller(@PathParam("userId") long userId) {
		List<Item> items = itemService.getBySeller(userId);
		List<ItemResp> result = new ArrayList<ItemResp>();
		items.forEach(item -> result.add(new ItemResp(item)));
		return result;
	}
	
	@JwtSecured
	@Path("/byTypeId/{typeId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<ItemResp> getByType(@PathParam("typeId") long typeId) {
		ItemType type = itemService.getTypeById(typeId);
		List<Item> items = itemService.getByType(type);
		List<ItemResp> result = new ArrayList<ItemResp>();
		items.forEach(item -> result.add(new ItemResp(item)));
		return result;
	}
	
	@JwtSecured
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createItem(ItemReq req) {
		ItemType type = itemService.getTypeById(req.getTypeId());
		if (type == null)
			return Response.status(400, "Type with given id not found!").build();
		User seller = userService.getById(req.getSellerId());
		if (seller == null)
			return Response.status(400, "Seller with given id not found!").build();
		Item createdItem = itemService.add(req, type, seller);
		if (createdItem != null)
			return Response.ok(createdItem).build();
		else
			return Response.serverError().build();
	}
	
	@JwtSecured
	@PUT
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response update(@PathParam("id") long id ,ItemReq req) {
		Item loadedItem = itemService.getById(id);
		if (loadedItem == null)
			return Response.status(400, "Item with given id not found").build();
		User seller = userService.getById(req.getSellerId());
		if (seller == null)
			return Response.status(400, "Seller with given id not found!").build();
		ItemType type = itemService.getTypeById(req.getTypeId());
		if (type == null)
			return Response.status(400, "Type with given id not found!").build();
		Item updatedItem = itemService.update(loadedItem, req, type, seller);
		if (updatedItem != null)
			return Response.ok(updatedItem).build();
		else
			return Response.serverError().build();
	}
	
	@Path("/Types")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<ItemType> getAll() {
		return itemService.getTypes();
	}
	
	@Path("/Deactivate/{id}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public Response deactivate(@PathParam("id") long id) {
		Item loadedItem = itemService.getById(id);
		if (loadedItem == null)
			return Response.status(400, "Item with given id not found").build();
		itemService.deactivate(loadedItem);
		return Response.ok("item deactivated").build();
	}
	
	@Path("/Activate/{id}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public Response activate(@PathParam("id") long id) {
		Item loadedItem = itemService.getById(id);
		if (loadedItem == null)
			return Response.status(400, "Item with given id not found").build();
		itemService.activate(loadedItem);
		return Response.ok("item activated").build();
	}
	
	@Path("/Delete/{id}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	public Response delete(@PathParam("id") long id) {
		Item loadedItem = itemService.getById(id);
		if (loadedItem == null)
			return Response.status(400, "Item with given id not found").build();
		itemService.delete(loadedItem);
		return Response.ok("item deleted").build();
	}
	
}
