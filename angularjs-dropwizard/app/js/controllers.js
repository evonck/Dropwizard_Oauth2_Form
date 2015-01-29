'use strict';

/* Controllers */

var angularjsDropwizardControllers = angular.module('angularjsDropwizardControllers', ['ngCookies']);

angularjsDropwizardControllers.controller('HomeCtrl', ['$scope','UsersResource','$location','$cookies',
	function($scope, UsersResource,$location,$cookies) {
		$scope.master = {};
		 $scope.user = {};
  		$scope.submitFormAddUser = function() {
    		var user = new UsersResource($scope.user);
    		var key = "pass2";
			delete user[key]; 
    		user.$save('',function(successResult) {
        		$cookies.tokensKalID = successResult.accessTokenId;
		 		$cookies.tokensKalcUserName = successResult.username;
				$cookies.lastaccessutc = successResult.lastAccessUTC;
				$location.path('/users/'+$scope.user.username);
    		})
  		}
  		$scope.login = function(){

  		}		
	}
]);

angularjsDropwizardControllers.controller('UserCtrl', ['$scope','UserResource','$cookies',
	function($scope,UserResource,$cookies) {
		var test = $cookies;
		var user = UserResource.get({ username: $cookies.tokensKalcUserName} , function() {
    console.log(entry);
  		});
	}
]);