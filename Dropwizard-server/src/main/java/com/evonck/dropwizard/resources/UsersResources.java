package com.evonck.dropwizard.resources;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

import org.joda.time.DateTime;

import com.codahale.metrics.annotation.Timed;
import com.evonck.dropwizard.core.AccessToken;
import com.evonck.dropwizard.core.User;
import com.evonck.dropwizard.db.AccessTokenDAO;
import com.evonck.dropwizard.db.UserDAO;
import com.evonck.dropwizard.views.UserView;

@Path("/users")
@Produces(MediaType.TEXT_HTML)

public class UsersResources {
	private final UserDAO userDAO;
	private final AccessTokenDAO  accessTokenDAO;
	
	public UsersResources(UserDAO userDAO,AccessTokenDAO accessTokenDAO){
		this.userDAO = userDAO;
		this. accessTokenDAO =  accessTokenDAO;
	}
	@Path("/{username}")
	@GET
    @Timed
    @UnitOfWork
    public UserView showHome( @PathParam("username")String username) {
		User user = userDAO.findUserByUsername(username);
		if(user != null){
			return new UserView();
		}else{
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
		}
    }
	
	@POST
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public AccessToken AddUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
		User userAuth=userDAO.create(user);
		DateTime dateTime = new DateTime();	
		AccessToken accessToken = this.accessTokenDAO.generateNewAccessToken(userAuth, dateTime);
		return accessToken;
    }
}
