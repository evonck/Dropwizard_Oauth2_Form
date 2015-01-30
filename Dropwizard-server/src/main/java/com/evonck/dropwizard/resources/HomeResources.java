package com.evonck.dropwizard.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.evonck.dropwizard.core.User;
import com.evonck.dropwizard.db.UserDAO;
import com.evonck.dropwizard.views.HomeView;
import com.evonck.dropwizard.views.UserView;
@Path("/")
@Produces(MediaType.TEXT_HTML)

public class HomeResources {
	private final UserDAO userDAO;
	
	public HomeResources(UserDAO userDAO){
		this.userDAO =userDAO;
	}
	
	@GET
    @Timed
    public HomeView showHome() {
        return new HomeView();
    }
	
	@Path("/check/{username}")
	@POST
    @Timed
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public boolean CheckUsername(@PathParam("username")  String username) {
		User user = userDAO.findUserByUsername(username);
		if(user != null){
			throw new WebApplicationException(Response.status(Response.Status.CONFLICT).build());
		}else{
			return true;
		}
    }
}
