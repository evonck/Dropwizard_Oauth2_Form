(function(angular) {
	'use strict';
var angularjsDropwizardDirectives = angular.module('angularjsDropwizardDirectives', []);

angularjsDropwizardDirectives.directive('username', function($q, $timeout) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
		var usernames = ['Jim', 'John', 'Jill', 'Jackie'];

			ctrl.$asyncValidators.username = function(modelValue, viewValue) {

				if (ctrl.$isEmpty(modelValue)) {
					// consider empty model valid
					return $q.when();
				}

				var def = $q.defer();

				$timeout(function() {
					// Mock a delayed response
					if (usernames.indexOf(modelValue) === -1) {
						// The username is available
						def.resolve();
					} else {
						def.reject();
					}

				}, 2000);

				return def.promise;
			};
		}
	};
});

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