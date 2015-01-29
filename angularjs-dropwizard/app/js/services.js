'use strict';

/* Services */
var angularjsDropwizardServices = angular.module('angularjsDropwizardServices', ['ngResource']);

/**
Post les infos de creation à l'api
**/
angularjsDropwizardServices.factory('UserResource', function($resource){
	return $resource('http://localhost:9090/users'); 

});

