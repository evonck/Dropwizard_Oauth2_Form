package com.evonck.Dropwizard_Oauth2_Form.core;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="users")
public class User implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="username",nullable=false)
	private String username;
	
	@Column(name="email",nullable=false)
	private String email;
	
	@Column(name="pass" , nullable=false)
	private String pass;
	
	@Column(name="salt")
	private String salt;
		public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
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
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}