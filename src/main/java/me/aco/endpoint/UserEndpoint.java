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
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.dto.UserReq;
import me.aco.dto.UserResp;
import me.aco.enums.UserRole;
import me.aco.interfaces.JwtSecured;
import me.aco.model.User;
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
			User createdUser = userService.saveUser(request.toUser());
			return Response.ok(createdUser).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response update(@PathParam("id") long id, UserReq request) {
		User sameUsername = userService.getByUsername(request.getUsername());
		if (sameUsername != null && sameUsername.getId() == id)
			return Response.status(409, "Username already taken!").build();
		try {
			User updatedUser = userService.updateUser(id, request);
			return Response.ok(updatedUser).build();
		} catch (NoResultException nre) {
			return Response.status(400, "User with given Id not found!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/deactivate/{id}")
	public Response deactivate(@PathParam("id") long id) {
		try {
			User user = userService.deactivateUser(id);
			return Response.ok(user).build();
		} catch (NoResultException nre) {
			return Response.status(400, "User with given Id not found!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/activate/{id}")
	public Response activate(@PathParam("id") long id) {
		try {
			User user = userService.activateUser(id);
			return Response.ok(user).build();
		} catch (NoResultException nre) {
			return Response.status(400, "User with given Id not found!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/roles")
	@Produces({MediaType.APPLICATION_JSON})
	public List<String> getRoles() 
	{
		List<String> result = new ArrayList<>();
		UserRole[] roles =UserRole.values();
		for (UserRole userRole : roles)
			result.add(userRole.toString());
		return result;
	}
}
