package me.aco.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.aco.dto.LoginReq;
import me.aco.dto.TokenResp;
import me.aco.dto.UserReq;
import me.aco.model.User;
import me.aco.service.AuthService;
import me.aco.service.UserService;
import me.aco.util.JWTUtil;

@Path("Auth")
public class AuthEndpoint {
	
	@Inject
	private UserService userService;
	@Inject
	private AuthService authService;
	
	@Path("/login")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public TokenResp login(LoginReq req) {
		User loadedUser = userService.getByUsername(req.getUsername());
		if (loadedUser == null)
			return null;
		else if (!authService.checkPassword(req, loadedUser))
			return null;
		return new TokenResp(JWTUtil.createToken(loadedUser), authService.createAndSaveRefreshToken(req));
	}
	
	@Path("/register")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response register(UserReq req) {
		if (userService.getByUsername(req.getUsername()) != null)
			return Response.status(409, "Username already taken!").build();
		try {
			userService.saveUser(req.toUser());
			return Response.ok("User created!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500, e.getMessage()).build();
		}
	}

}
