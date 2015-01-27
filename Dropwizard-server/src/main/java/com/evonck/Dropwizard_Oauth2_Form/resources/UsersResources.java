package com.evonck.Dropwizard_Oauth2_Form.resources;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.codahale.metrics.annotation.Timed;
import com.evonck.Dropwizard_Oauth2_Form.core.AccessToken;
import com.evonck.Dropwizard_Oauth2_Form.core.User;
import com.evonck.Dropwizard_Oauth2_Form.db.AccessTokenDAO;
import com.evonck.Dropwizard_Oauth2_Form.db.UserDAO;
import com.evonck.Dropwizard_Oauth2_Form.views.UserView;

@Path("/users")
@Produces(MediaType.TEXT_HTML)

public class UsersResources {
	private final UserDAO userDAO;
	private final AccessTokenDAO  accessTokenDAO;
	
	public UsersResources(UserDAO userDAO,AccessTokenDAO accessTokenDAO){
		this.userDAO = userDAO;
		this. accessTokenDAO =  accessTokenDAO;
	}
	@Path("/user_Id")
	@GET
    @Timed
    @UnitOfWork
    public UserView showHome(@Auth Long userId	) {
        return new UserView();
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
