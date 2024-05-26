'use strict';

App.controller('Signup', function($scope, $http, $state, $stateParams, Restangular) {
    $scope.user = {}; // Initialize user object

    /**
     * Signup function.
     */
    $scope.signup = function() {
        Restangular.one('user/signup').put({
            username: $scope.user.username,
            password:  $scope.user.password,
            // locale:"en",
            email:$scope.user.email
        }).then(function(data) {
              $state.transitionTo('login');
            }, function(response) {
              alert("The email provided is already registered. Please use a different one!!");
            });
      };


    
    $scope.checkEmailUniqueness = function() {
        if ($scope.user.email) {
            $http.get('checkEmailUniqueness', { params: { email: $scope.user.email } }).then(function(response) {
                if (response.data.isUnique) {
                    // Email is unique
                    $scope.editUserForm.email.$setValidity('unique', true);
                } else {
                    // Email is not unique
                    // Show an error message
                    $scope.editUserForm.email.$setValidity('unique', false);
                }
            });
        }
    };

});