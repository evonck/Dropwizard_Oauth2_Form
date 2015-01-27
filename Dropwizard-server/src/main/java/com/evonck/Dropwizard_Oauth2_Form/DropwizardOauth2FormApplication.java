package com.evonck.Dropwizard_Oauth2_Form;

import org.joda.time.DateTimeZone;

import com.evonck.Dropwizard_Oauth2_Form.auth.SimpleAuthenticator;
import com.evonck.Dropwizard_Oauth2_Form.core.AccessToken;
import com.evonck.Dropwizard_Oauth2_Form.core.User;
import com.evonck.Dropwizard_Oauth2_Form.db.AccessTokenDAO;
import com.evonck.Dropwizard_Oauth2_Form.db.UserDAO;
import com.evonck.Dropwizard_Oauth2_Form.resources.HomeResources;
import com.evonck.Dropwizard_Oauth2_Form.resources.OAuth2Resource;
import com.evonck.Dropwizard_Oauth2_Form.resources.UsersResources;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class DropwizardOauth2FormApplication extends Application<DropwizardOauth2FormConfiguration> {
	public static void main(String[] args) throws Exception {
        new DropwizardOauth2FormApplication().run(args);
    }

	private final HibernateBundle<DropwizardOauth2FormConfiguration> hibernate = new HibernateBundle<DropwizardOauth2FormConfiguration>(User.class,AccessToken.class) {
	    @Override
	    public DataSourceFactory getDataSourceFactory(DropwizardOauth2FormConfiguration configuration) {
	        return configuration.getDataSourceFactory();
	    }
	};

    @Override
    public void initialize(Bootstrap<DropwizardOauth2FormConfiguration> bootstrap) {
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
    public void run(DropwizardOauth2FormConfiguration configuration,Environment environment) {
    	final AccessTokenDAO accessTokenDAO = new AccessTokenDAO(hibernate.getSessionFactory());
    	final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
    	environment.jersey().register(new HomeResources());
   	 	environment.jersey().register(new UsersResources(userDAO,accessTokenDAO));
		environment.jersey().register(new OAuth2Resource(configuration.getAllowedGrantTypes(), accessTokenDAO, userDAO));
   	 	environment.jersey().register(new OAuthProvider<Long>(new SimpleAuthenticator(accessTokenDAO), configuration.getBearerRealm()));
    }

}
