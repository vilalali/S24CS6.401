'use strict';

/**
 * Navigation controller.
 */
App.controller('Navigation', function($scope, $http, $state, $rootScope, User) {
  $rootScope.userInfo = User.userInfo();

  /**
   * User logout.
   */
  $scope.logout = function($event) {
    User.logout().then(function() {
      $rootScope.userInfo = User.userInfo(true);
      $state.transitionTo('main');
    });
    $event.preventDefault();
  };

  /**
   * Returns true if at least an asynchronous request is in progress.
   */
  $scope.isLoading = function() {
    return $http.pendingRequests.length > 0;
  };

  // Collapse the menu on state change
  $scope.$on('$stateChangeStart', function() {
    $scope.collapsed = true;
  });
});