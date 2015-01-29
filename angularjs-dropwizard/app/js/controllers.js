'use strict';

/* Controllers */

var angularjsDropwizardControllers = angular.module('angularjsDropwizardControllers', []);

angularjsDropwizardControllers.controller('HomeCtrl', ['$scope','UserResource','$location',
	function($scope, UserResource,$location) {
		$scope.master = {};
		 $scope.user = {};
  		$scope.submitFormAddUser = function() {
    		var user = new UserResource($scope.user);
    		var key = "pass2";
			delete user[key]; 
    		user.$save($scope.user,function(successResult) {
        		localStorage["tokens-Kalc-ID"] = successResult.accessTokenId;
		 		localStorage["tokens-Kalc-UserID"] = successResult.userId;
				localStorage["last_access_utc"] = successResult.lastAccessUTC;
				$location.path('/users/'+$scope.user.username);
    		})
  		}
		
	}
]);

angularjsDropwizardControllers.controller('UserCtrl', ['$scope',
	function($scope) {
		
	}
]);