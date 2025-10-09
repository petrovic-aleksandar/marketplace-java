package me.aco.jakarta.hello;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import me.aco.Service.UserService;
import me.aco.model.User;
import me.aco.model.UserRole;

@Path("hello")
public class HelloWorldResource {
	
	@Inject
	UserService userService;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }
    
    @GET
    @Path("test")
    public void test() {
    	System.out.println("TEST");
    	if (false) {
    		User u = new User();
    		u.setUsername("petar.petrovic");
        	u.setPassword("petar123");
        	u.setName("Petar Petrovic");
        	u.setEmail("pp@gmai.com");
        	u.setPhone("67 100 100");
        	u.setRole(UserRole.USER);
        	userService.saveUser(u);
    	}
    	
    	
    }
}
