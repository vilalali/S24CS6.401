'use strict';

/**
 * Book add manually controller.
 */
App.controller('BookAddManual', function($scope, $state, $stateParams, Restangular) {
  $scope.isEdit = false;
  $scope.book = {
    tags: []
  };

  /**
   * Create the new book.
   */
  $scope.edit = function() {
    $scope.book.publish_date = Date.parse($scope.book.publish_date_year + '-01-01');
    Restangular.one('book/manual', $stateParams.id).put($scope.book).then(function(data) {
      $state.transitionTo('bookview', { id: data.id });
    }, function(data) {
      alert(data.data.message);
    });
  };

  /**
   * Cancel the manual add and go back to the book add main page.
   */
  $scope.cancel = function() {
    $state.transitionTo('bookadd');
  };
});