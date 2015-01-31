package com.evonck.dropwizard.db;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.evonck.dropwizard.core.User;

import io.dropwizard.hibernate.AbstractDAO;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<User> {
	final static Map<Long, User> userTable = new HashMap<Long,User>();
	private final static int ITERATION_NUMBER = 1000;
	
	
	private  SessionFactory factory;
	public UserDAO(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }
	public User findUserByUsername(String username){
		List<User> user = currentSession().createSQLQuery(
	     		   "select u.* from users u where u.username =:username ").addEntity("u", User.class).setParameter("username",username).list();
		if(user.size()!=0){
			return user.get(0);
		}else{
			return null;
		}
	}
	
	public User findUserByEmail(String email){
		List<User> user = currentSession().createSQLQuery(
	     		   "select u.* from users u where u.email =:email ").addEntity("u", User.class).setParameter("email",email).list();
		if(user.size()!=0){
			return user.get(0);
		}else{
			return null;
		}
	}
	
	
	public User findUserByEmailAndPassword(String email, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		boolean authenticated=false;
		boolean userExist = true;
		User userAuth =null;
		if (email==null||password==null){
			
            // TIME RESISTANT ATTACK
            // Computation time is equal to the time needed by a legitimate user
			userAuth = null;
            email="";
            password="";
		}	
	
		List<User> user = currentSession().createSQLQuery(
	     		   "select u.* from users u where u.email =:email ").addEntity("u", User.class).setParameter("email",email).list();
		String digest, salt;
		if(user.size()!=0){
			userAuth = user.get(0);
			digest = user.get(0).getPass();
			salt = user.get(0).getSalt();
			if (digest == null || salt == null) {
                throw new SQLException("Database inconsistant Salt or Digested Password altered");
            }
			if (user.size() >1) { 
                throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
            }
		}else{
			 digest = "000000000000000000000000000=";
             salt = "00000000000=";
             userAuth = null;
		}
		ObjectMapper mapper = new ObjectMapper();
		 byte[] bDigest = mapper.convertValue(digest, byte[].class);
         byte[] bSalt =  mapper.convertValue(salt, byte[].class);
         
      // Compute the new DIGEST
         byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);
         if(Arrays.equals(proposedDigest, bDigest)){
        	 return userAuth;
         }else{
        	 userAuth =null;
        	 return userAuth;
         }
         
	}
	
	 public User create(User user)
	           throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException
	   {
         if (user.getEmail()!=null&&user.getPass()!=null&&user.getEmail().length()<=100){
               // Uses a secure Random not a simple Random
               SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
               // Salt generation 64 bits long
               byte[] bSalt = new byte[8];
               random.nextBytes(bSalt);
               // Digest computation
               byte[] bDigest = getHash(ITERATION_NUMBER,user.getPass(),bSalt);
               ObjectMapper mapper = new ObjectMapper();
               user.setPass(mapper.convertValue(bDigest, String.class));
               user.setSalt(mapper.convertValue(bSalt, String.class));
               persist(user);
               
           return user;
           } else {
               return null;
           }
   }

	
	public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    MessageDigest digest = MessageDigest.getInstance("SHA-1");
	    digest.reset();
	    digest.update(salt);
	    byte[] input = digest.digest(password.getBytes("UTF-8"));
	    for (int i = 0; i < iterationNb; i++) {
	        digest.reset();
	        input = digest.digest(input);
	    }
	    return input;
	}

	 
}
