'use strict';

// Declare app level module which depends on views, and components
var angularjsDropwizard = angular.module('angularjsDropwizard', [
	'ngRoute',
	'angularjsDropwizardControllers',
	'angularjsDropwizardDirectives',
	'angularjsDropwizardServices'
]);


angularjsDropwizard.config(['$routeProvider', '$httpProvider','$locationProvider',
	function ($routeProvider, $httpProvider,$locationProvider) {
  		$httpProvider.defaults.useXDomain = true;
  		$routeProvider
			.when('/',{templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
			.when('/users/:username',{templateUrl: 'partials/users.html', controller: 'UserCtrl'});
		$httpProvider.defaults.useXDomain = true;
		$httpProvider.interceptors.push('tokenInjector');
		$httpProvider.interceptors.push('requestRecoverer');
	//	$locationProvider.html5Mode(true);
	}]);	