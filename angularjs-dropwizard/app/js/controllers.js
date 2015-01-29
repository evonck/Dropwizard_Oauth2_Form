'use strict';

/* Controllers */

var angularjsDropwizardControllers = angular.module('angularjsDropwizardControllers', []);

angularjsDropwizardControllers.controller('HomeCtrl', ['$scope','UserResource',
	function($scope, UserResource) {
		$scope.master = {};
		 $scope.user = {};
  		$scope.submitFormAddUser = function() {
    		var user = new UserResource($scope.user);
    		var key = "pass2";
			delete user[key]; 
    		user.$save();
  		}
		
	}
]);

angularjsDropwizardControllers.controller('UserCtrl', ['$scope',
	function($scope, User) {
		
	}
]);