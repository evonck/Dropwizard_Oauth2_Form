'use strict';

/* Services */
var angularjsDropwizardServices = angular.module('angularjsDropwizardServices', ['ngResource']);

/**
Post les infos de creation à l'api
**/
angularjsDropwizardServices.factory('UsersResource', function($resource){
	return $resource('http://localhost:9090/users/'); 
});
angularjsDropwizardServices.factory('UserResource', function($resource){
	return $resource('http://localhost:9090/users/:username'); 
});
angularjsDropwizardServices.factory('LoginResource', function($resource){
	return $resource('http://localhost:9090/oauth2/token'); 
});

// register the interceptor as a service
angularjsDropwizardServices.factory('tokenInjector', ['$localStorage', function($localStorage) {  
    var tokenInjector = {
        request: function(config) {
            if ($localStorage.tokensKalID !='') {
            	config.headers['Authorization'] = "Bearer "+$localStorage.tokensKalID;
            }
            return config;
        }
    };
    return tokenInjector;
}]);

angularjsDropwizardServices.factory('requestRecoverer', ['$q', '$location',function($q,$location) {  
    var requestRecoverer = {
        response: function(response) {
            if (response.status == 401) {
            	$location.path('/');
            }
            return response;
        },
        responseError: function(response){
        if (response.status == 401) {
            	$location.path('/');
            }
            return response;	
        }
    };
    return requestRecoverer;
}]);