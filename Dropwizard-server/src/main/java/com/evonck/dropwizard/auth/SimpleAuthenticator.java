package com.evonck.dropwizard.auth;

import com.google.common.base.Optional;
import com.evonck.dropwizard.core.AccessToken;
import com.evonck.dropwizard.db.AccessTokenDAO;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

public class SimpleAuthenticator implements Authenticator<String, String> {
	public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;
	private AccessTokenDAO accessTokenDAO;

	
	public SimpleAuthenticator(AccessTokenDAO accessTokenDAO) {
		super();
		this.accessTokenDAO = accessTokenDAO;
	}


	@Override
	public Optional<String> authenticate(String accessTokenId) throws AuthenticationException {
		// Check input, must be a valid UUID
		UUID accessTokenUUID;
		try {
			accessTokenUUID = UUID.fromString(accessTokenId);
		} catch (IllegalArgumentException e) {
			return Optional.absent();
		}

		// Get the access token from the database
		Optional<AccessToken> accessToken = accessTokenDAO.findAccessTokenById(accessTokenUUID);
		if (accessToken == null || !accessToken.isPresent()) {
			return Optional.absent();
		}

		// Check if the last access time is not too far in the past (the access token is expired)
		Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
		if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
			return Optional.absent();
		}

		// Update the access time for the token
		accessToken.get().setLastAccessUTC(new DateTime());
		accessToken = Optional.of(accessTokenDAO.update(accessToken.get()));

		// Return the user's id for processing
		//Optional<Long> user_id =Optional.of(accessTokenUpdated.getUserId());
		 Optional<String> username = Optional.of(accessToken.get().getUsername());
		return username;
	}
}