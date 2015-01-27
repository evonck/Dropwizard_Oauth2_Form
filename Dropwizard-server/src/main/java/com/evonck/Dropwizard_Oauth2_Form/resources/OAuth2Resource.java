package com.evonck.Dropwizard_Oauth2_Form.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.google.common.collect.ImmutableList;
import com.evonck.Dropwizard_Oauth2_Form.core.AccessToken;
import com.evonck.Dropwizard_Oauth2_Form.core.User;
import com.evonck.Dropwizard_Oauth2_Form.db.AccessTokenDAO;
import com.evonck.Dropwizard_Oauth2_Form.db.UserDAO;
import com.sun.jersey.api.Responses;

import org.joda.time.DateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/oauth2/token")
@Produces(MediaType.APPLICATION_JSON)
public class OAuth2Resource {
	private ImmutableList<String> allowedGrantTypes;
	private AccessTokenDAO accessTokenDAO;
	private UserDAO userDAO;

	public OAuth2Resource(ImmutableList<String> allowedGrantTypes, AccessTokenDAO accessTokenDAO, UserDAO userDAO) {
		this.allowedGrantTypes = allowedGrantTypes;
		this.accessTokenDAO = accessTokenDAO;
		this.userDAO = userDAO;

	}

	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postForToken(
			@FormParam("grant_type") String grantType,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("client_id") String clientId
	) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
		// Check if the grant type is allowed
		if (!allowedGrantTypes.contains(grantType)) {
			Response response = Response.status(Responses.METHOD_NOT_ALLOWED).build();
			throw new WebApplicationException(response);
		}

		// Try to find a user with the supplied credentials.
		User user = userDAO.findUserByEmailAndPassword(email, password);
		if (user == null) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}

		// User was found, generate a token and return it.
		AccessToken accessToken = accessTokenDAO.generateNewAccessToken(user, new DateTime());
		return accessToken.getAccessTokenId().toString();
	}
}
