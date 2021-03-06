package com.evonck.dropwizard.db;

import com.google.common.base.Optional;
import com.evonck.dropwizard.core.AccessToken;
import com.evonck.dropwizard.core.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.UUID;

public class AccessTokenDAO extends AbstractDAO<AccessToken> {
	private  SessionFactory factory;
	public AccessTokenDAO(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }
	protected Session currentSession() {
        return factory.getCurrentSession();
    }
	

	public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
		 	
		Optional<AccessToken> accessToken = Optional.fromNullable(get(accessTokenId));
		if (!accessToken.isPresent()) {
			return Optional.absent();
		}
		return accessToken;
	}

	public AccessToken generateNewAccessToken(final User user, final DateTime dateTime) {
		AccessToken accessToken = new AccessToken();
		accessToken.setLastAccessUTC(dateTime);
		accessToken.setUsername(user.getUsername());
		persist(accessToken);
		return accessToken;
	}

	public AccessToken update(AccessToken accessToken) {
			currentSession().merge(accessToken);
			return accessToken;
		
	}
}