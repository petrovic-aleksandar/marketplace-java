package me.aco.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import me.aco.dto.LoginReq;
import me.aco.dto.TokenResp;
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

}
