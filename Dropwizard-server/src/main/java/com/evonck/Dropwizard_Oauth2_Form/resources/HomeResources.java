package com.evonck.Dropwizard_Oauth2_Form.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.evonck.Dropwizard_Oauth2_Form.views.HomeView;
@Path("/")
@Produces(MediaType.TEXT_HTML)

public class HomeResources {
		
		public HomeResources(){
		}
		
		@GET
	    @Timed
	    public HomeView showHome() {
	        return new HomeView();
	    }
}
