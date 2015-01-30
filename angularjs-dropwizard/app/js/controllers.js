'use strict';

/* Controllers */

var angularjsDropwizardControllers = angular.module('angularjsDropwizardControllers', ['ngStorage']);

angularjsDropwizardControllers.controller('HomeCtrl', ['$scope','UsersResource','$location','$localStorage','LoginResource','$window',
	function($scope, UsersResource,$location,$localStorage,LoginResource,$window) {
		$scope.master = {};
		 $scope.user = {};
  		$scope.submitFormAddUser = function() {
    		var user = new UsersResource($scope.user);
    		var key = "pass2";
			delete user[key]; 
    		user.$save('',function(successResult) {
        		$localStorage.tokensKalID = successResult.accessTokenId;
		 		$localStorage.tokensKalcUserName = successResult.username;
				$localStorage.lastaccessutc = successResult.lastAccessUTC;
				//$window.location='/app/#/users/'+$scope.user.username
				$location.path('/users/'+$scope.user.username).replace();
    		})
  		}
  		$scope.login = function(userlogin) {
  			LoginResource.save({grantType: "password",email : userlogin.email, password : userlogin.pass},
  			function(successResult){
				$localStorage.tokensKalID = successResult.accessTokenId;
		 		$localStorage.tokensKalcUserName = successResult.username;
				$localStorage.lastaccessutc = successResult.lastAccessUTC;
				//$window.location='/app/#/users/'+successResult.username
				$location.path('/users/'+successResult.username).replace();
			}
  			)

  		}		
	}
]);

angularjsDropwizardControllers.controller('UserCtrl', ['$scope','UserResource','$localStorage','$routeParams',
	function($scope,UserResource,$localStorage,$routeParams) {
		var user = UserResource.get({ username: $routeParams.username} , function() {
    		$scope.error="succes"
  		},function(){
  			$scope.error="unauthorized"
  		});
	}
]);