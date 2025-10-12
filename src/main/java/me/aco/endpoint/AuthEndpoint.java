package me.aco.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import me.aco.dto.LoginReq;
import me.aco.dto.TokenResp;
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
		if (!userService.checkIfUsernameExists(req.getUsername()))
			return null;
		if (!authService.checkPassword(req))
			return null;
		return new TokenResp(JWTUtil.createToken(), authService.createAndSaveRefreshToken(req));
	}

}
