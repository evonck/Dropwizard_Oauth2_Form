package com.evonck.dropwizard;

import java.util.EnumSet;

import org.joda.time.DateTimeZone;

import com.evonck.dropwizard.auth.SimpleAuthenticator;
import com.evonck.dropwizard.core.AccessToken;
import com.evonck.dropwizard.core.User;
import com.evonck.dropwizard.db.AccessTokenDAO;
import com.evonck.dropwizard.db.UserDAO;
import com.evonck.dropwizard.resources.HomeResources;
import com.evonck.dropwizard.resources.OAuth2Resource;
import com.evonck.dropwizard.resources.UsersResources;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

public class dropwizardServerApplication extends Application<dropwizardServerConfiguration> {
	public static void main(String[] args) throws Exception {
        new dropwizardServerApplication().run(args);
    }

	private final HibernateBundle<dropwizardServerConfiguration> hibernate = new HibernateBundle<dropwizardServerConfiguration>(User.class,AccessToken.class) {
	    @Override
	    public DataSourceFactory getDataSourceFactory(dropwizardServerConfiguration configuration) {
	        return configuration.getDataSourceFactory();
	    }
	};

    @Override
    public void initialize(Bootstrap<dropwizardServerConfiguration> bootstrap) {
    	bootstrap.addBundle(new ViewBundle());
    	bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
    	bootstrap.addBundle(new AssetsBundle("/assets/img", "/img", null, "img"));
    	bootstrap.addBundle(new AssetsBundle("/assets/font", "/font", null, "font"));
    	bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
    	bootstrap.addBundle(hibernate);
    	DateTimeZone.setDefault(DateTimeZone.UTC);
    }

	    @Override
	    /**
	     * Initialize the application with the configuration data
	     */
    public void run(dropwizardServerConfiguration configuration,Environment environment) {
    	Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_EXPOSE_HEADERS_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_REQUEST_HEADERS_HEADER, " X-Total-Pages, X-Page");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    	final AccessTokenDAO accessTokenDAO = new AccessTokenDAO(hibernate.getSessionFactory());
    	final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
    	environment.jersey().register(new HomeResources(userDAO));
   	 	environment.jersey().register(new UsersResources(userDAO,accessTokenDAO));
		environment.jersey().register(new OAuth2Resource(configuration.getAllowedGrantTypes(), accessTokenDAO, userDAO));
   	 	environment.jersey().register(new OAuthProvider<String>(new SimpleAuthenticator(accessTokenDAO), configuration.getBearerRealm()));
    }

}
