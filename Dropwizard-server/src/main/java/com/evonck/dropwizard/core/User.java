package com.evonck.dropwizard.core;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="users")
public class User implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="username",nullable=false,unique=true)
	private String username;
	
	@Column(name="email",nullable=false,unique=true)
	private String email;
	
	@Column(name="pass" , nullable=false,unique=true)
	private String pass;
	
	@Column(name="salt")
	private String salt;
		
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonIgnore
    @JsonProperty(value = "pass")
	public String getPass() {
		return pass;
	}
	@JsonProperty(value = "pass")
	public void setPass(String pass) {
		this.pass = pass;
	}
	@JsonIgnore
    @JsonProperty(value = "salt")
	public String getSalt() {
		return salt;
	}
	@JsonProperty(value = "salt")
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}