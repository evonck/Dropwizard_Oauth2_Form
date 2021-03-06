package com.evonck.dropwizard.resources;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import com.codahale.metrics.annotation.Timed;
import com.evonck.dropwizard.core.AccessToken;
import com.evonck.dropwizard.core.User;
import com.evonck.dropwizard.db.AccessTokenDAO;
import com.evonck.dropwizard.db.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)

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
    public Response  GetInfo(@Auth String username) throws JsonProcessingException {
		User user = userDAO.findUserByUsername(username);
		if(user != null){
			 ObjectMapper objectMapper = new ObjectMapper();
	         ObjectWriter writer = objectMapper.writer();
			 String entity = writer.writeValueAsString(user);
		      return Response.ok(entity).build();
		}else{
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
		}
    }
	
	@POST
    @UnitOfWork
    public AccessToken AddUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
		User userAuth=userDAO.create(user);
		DateTime dateTime = new DateTime();	
		AccessToken accessToken = this.accessTokenDAO.generateNewAccessToken(userAuth, dateTime);
		return accessToken;
    }
	
	@Path("/{username}")
	@PUT
    @UnitOfWork
    public User UpdateUser(User user,@QueryParam("oldpass") String oldpassword,@QueryParam("newpass") String newpassword) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
		user = userDAO.findUserByEmailAndPassword(user.getEmail(), oldpassword);
		if(user !=null ){
			user.setPass(newpassword);
			return userDAO.create(user);
		}else{
			return null;
		}
    }
}
