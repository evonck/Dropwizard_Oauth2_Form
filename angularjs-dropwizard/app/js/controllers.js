'use strict';

/* Controllers */

var angularjsDropwizardControllers = angular.module('angularjsDropwizardControllers', ['ngStorage']);

angularjsDropwizardControllers.controller('HomeCtrl', ['$scope','UsersResource','$location','$localStorage','LoginResource','$window',
	function($scope, UsersResource,$location,$localStorage,LoginResource,$window) {
		$scope.master = {};
		$scope.user = {};
		if($localStorage.tokensKalcUserName == ''){
			$scope.loginUser =false;
		}else{
			$scope.loginUser =true;
		}				
		$scope.submitFormAddUser = function() {
    		var user = new UsersResource($scope.user);
    		var key = "pass2";
			delete user[key]; 
    		user.$save('',function(successResult) {
        		$localStorage.tokensKalID = successResult.accessTokenId;
		 		$localStorage.tokensKalcUserName = successResult.username;
				$localStorage.lastaccessutc = successResult.lastAccessUTC;
				$location.path('/users/'+$scope.user.username).replace();
    		})
  		}
  		$scope.login = function(userlogin) {
  			LoginResource.save({grantType: "password",email : userlogin.email, password : userlogin.pass},
  			function(successResult){
				$localStorage.tokensKalID = successResult.accessTokenId;
		 		$localStorage.tokensKalcUserName = successResult.username;
				$localStorage.lastaccessutc = successResult.lastAccessUTC;
				$location.path('/users/'+successResult.username).replace();
			}
  			)

  		}
  		$scope.logOut = function(){
  			$localStorage.tokensKalID = '';
		 	$localStorage.tokensKalcUserName = '';
			$localStorage.lastaccessutc = '';
			$scope.loginUser =false;
  		}		
	}
]);

angularjsDropwizardControllers.controller('UserCtrl', ['$scope','UserResource','$localStorage','$routeParams','$http',
	function($scope,UserResource,$localStorage,$routeParams,$http) {
		$scope.loginUser=true;
		$scope.user = UserResource.get({username: $routeParams.username}); 
  		$scope.logOut = function(){
  			$localStorage.tokensKalID = '';
		 	$localStorage.tokensKalcUserName = '';
			$localStorage.lastaccessutc = '';

  		}
  		$scope.ChangePasswordUser = function(){
  			$http({
            	url: 'http://localhost:9090/users/'+$routeParams.username,
            	method: "PUT",
            	 params: { oldpass: $scope.userUpdate.oldpass, newpass: $scope.userUpdate.pass},
            	data: { username : $routeParams.username, email : $scope.user.email},
            	headers: {'Content-Type': 'application/json'}
        		}).success(function (data, status, headers, config) {
        			if(status == 200){
        				$('#ChangePassword').modal('hide');
        				$scope.error =false;
        			}else{
        				$scope.error = true;
        			}
        		}).error(function (data, status, headers, config) {
                	$scope.error = true;
            	});
  




			/*$scope.user.$update({ oldpass: $scope.userUpdate.oldpass, newpass: $scope.userUpdate.pass},
  				function(value, responseHeaders) {
  					if(value.pass == null){
						$scope.error = true;
  					}else{
	  					$('#ChangePassword').modal('hide');
						$scope.error = false;
  					}
  				},function(httpResponse) {
  					$scope.error = true;
    			}
  			);*/
  		}
	}
]);