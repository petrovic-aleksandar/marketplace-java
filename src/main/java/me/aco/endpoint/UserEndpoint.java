package me.aco.endpoint;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.dto.UserReq;
import me.aco.dto.UserResp;
import me.aco.interfaces.JwtSecured;
import me.aco.service.UserService;

@JwtSecured
@Path("User")
public class UserEndpoint {
	
	@Inject
	private UserService userService;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<UserResp> getAll() {
		List<UserResp> response = new ArrayList<UserResp>();
		userService.getAll().forEach(user -> response.add(new UserResp(user)));
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response create(UserReq request) {
		if (userService.getByUsername(request.getUsername()) != null)
			return Response.status(409, "Username already taken!").build();
		try {
			userService.saveUser(request.toUser());
			return Response.ok("User created!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
		
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response update(long id, UserReq request) {
		try {
			userService.updateUser(id, request);
			return Response.ok().build();
		} catch (NoResultException nre) {
			return Response.status(400, "User with given Id not found!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/delete/{id}")
	public Response delete(long id) {
		try {
			userService.deactivateUser(id);
			return Response.ok().build();
		} catch (NoResultException nre) {
			return Response.status(400, "User with given Id not found!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}

}
