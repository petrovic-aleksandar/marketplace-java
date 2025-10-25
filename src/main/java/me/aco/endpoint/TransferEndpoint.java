package me.aco.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.dto.TransferReq;
import me.aco.model.Item;
import me.aco.model.Transfer;
import me.aco.model.User;
import me.aco.service.ItemService;
import me.aco.service.TransferService;
import me.aco.service.UserService;

@Path("Transfer")
public class TransferEndpoint {
	
	@Inject
	private TransferService transferService;
	@Inject
	private UserService userService;
	@Inject
	private ItemService itemService;
	
	@GET
	@Path("/byUserId/{userId}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getByUserId(@PathParam("userId") long userId) {
		User user = userService.getById(userId);
		if (user == null)
			return Response.status(404).build();
		return Response.ok(transferService.getByUser(user)).build();
	}
	
	@POST
	@Path("/payment")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addPayment(TransferReq req) {
		User seller = userService.getById(req.getSellerId());
		if (seller == null)
			return Response.status(404).build();
		Transfer transfer = transferService.addPayment(req, seller);
		return Response.ok(transfer).build();
	}
	
	@POST
	@Path("/withdrawal")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addWithdrawal(TransferReq req) {
		User buyer = userService.getById(req.getBuyerId());
		if (buyer == null)
			return Response.status(404).build();
		Transfer transfer = transferService.addWithdrawal(req, buyer);
		return Response.ok(transfer).build();
	}
	
	@POST
	@Path("/purchase")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addPurchase(TransferReq req) {
		User buyer = userService.getById(req.getBuyerId());
		User seller = userService.getById(req.getSellerId());
		Item item = itemService.getById(req.getItemId());
		if (buyer == null || seller == null || item == null)
			return Response.status(404).build();
		Transfer transfer = transferService.addPurchase(req, buyer, seller, item);
		return Response.ok(transfer).build();
	}

}
