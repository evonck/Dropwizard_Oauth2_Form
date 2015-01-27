package com.evonck.dw.server;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class DropwizardOauth2FormConfiguration extends Configuration {
	@Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();


	public DropwizardOauth2FormConfiguration(){	
	}
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    @Valid
	@JsonProperty
	private ImmutableList<String> allowedGrantTypes;

	@Valid	
	@JsonProperty
	@NotEmpty
	private String bearerRealm;


	public ImmutableList<String> getAllowedGrantTypes() {
		return allowedGrantTypes;
	}
	public void setAllowedGrantTypes(ImmutableList<String> allowedGrantTypes) {
		this.allowedGrantTypes = allowedGrantTypes;
	}
	public String getBearerRealm() {
		return bearerRealm;
	}
	public void setBearerRealm(String bearerRealm) {
		this.bearerRealm = bearerRealm;
	}


}
