package com.evonck.dropwizard.core;	

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "accessToken")
public class AccessToken {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)",name = "accessToken_id", unique = true)
	private UUID accessTokenId;

	@Column(name="username")
	@NotNull
	private String username;

	@Column(name="lastAccess_utc")
	@NotNull
	private DateTime lastAccessUTC;
	
	
	public UUID getAccessTokenId() {
		return accessTokenId;
	}

	public void setAccessTokenId(UUID accessTokenId) {
		this.accessTokenId = accessTokenId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public DateTime getLastAccessUTC() {
		return lastAccessUTC;
	}

	public void setLastAccessUTC(DateTime lastAccessUTC) {
		this.lastAccessUTC = lastAccessUTC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessTokenId == null) ? 0 : accessTokenId.hashCode());
		result = prime * result
				+ ((lastAccessUTC == null) ? 0 : lastAccessUTC.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessToken other = (AccessToken) obj;
		if (accessTokenId == null) {
			if (other.accessTokenId != null)
				return false;
		} else if (!accessTokenId.equals(other.accessTokenId))
			return false;
		if (lastAccessUTC == null) {
			if (other.lastAccessUTC != null)
				return false;
		} else if (!lastAccessUTC.equals(other.lastAccessUTC))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}