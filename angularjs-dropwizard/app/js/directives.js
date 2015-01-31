(function(angular) {
	'use strict';
var angularjsDropwizardDirectives = angular.module('angularjsDropwizardDirectives', []);

angularjsDropwizardDirectives.directive('username',['$http', function($http) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			scope.$watch(attrs.ngModel, function() {
	        	$http({
	          		method: 'POST',
	          		url: 'http://localhost:9090/check/'+ attrs.username,
	        	}).success(function(data, status, headers, cfg) {
	        		if(status == 409){
	          			ctrl.$setValidity('unique', false);
	        		}
	        		if(status == 200){
	          			ctrl.$setValidity('unique',true);
	 				}
	        	}).error(function(data, status, headers, cfg) {
	          		ctrl.$setValidity('unique', false);
	        	});
			});
		}
	}
}]);

angularjsDropwizardDirectives.directive('email',['$http', function($http) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			scope.$watch(attrs.ngModel, function() {
	        	$http({
	          		method: 'POST',
	          		url: 'http://localhost:9090/checkEmail/'+ attrs.email,
	        	}).success(function(data, status, headers, cfg) {
	        		if(status == 409){
	          			ctrl.$setValidity('unique', false);
	        		}
	        		if(status == 200){
	          			ctrl.$setValidity('unique',true);
	 				}
	        	}).error(function(data, status, headers, cfg) {
	          		ctrl.$setValidity('unique', false);
	        	});
			});
		}
	}
}]);

angularjsDropwizardDirectives.directive('pwCheck', function() {
	return {
		require: 'ngModel',
		link: function(scope, elem, attrs,ctrl) {
			var firstPassword = '#' + attrs.pwCheck;
			elem.add(firstPassword).on('keyup', function () {
				scope.$apply(function () {
					ctrl.$setValidity('pwmatch', elem.val() === $(firstPassword).val());
				});
			});
		}
	}
});
})(window.angular);