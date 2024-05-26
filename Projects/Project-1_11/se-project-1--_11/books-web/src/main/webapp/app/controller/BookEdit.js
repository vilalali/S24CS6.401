'use strict';

/**
 * Book edit controller.
 */
App.controller('BookEdit', function($scope, $state, $stateParams, Restangular) {
  $scope.isEdit = true;

  /**
   * Save the modifications.
   */
  $scope.edit = function() {
    $scope.book.publish_date = Date.parse($scope.book.publish_date_year + '-01-01');
    Restangular.one('book', $stateParams.id).post('', $scope.book).then(function() {
      $state.transitionTo('bookview', { id: $stateParams.id });
    })
  };

  /**
   * Cancel the edition and go back to the book.
   */
  $scope.cancel = function() {
    $state.transitionTo('bookview', { id: $stateParams.id });
  };

  // Load book
  Restangular.one('book', $stateParams.id).get().then(function(data) {
    $scope.book = data;
    $scope.book.publish_date_year = new Date($scope.book.publish_date).getFullYear();
    $scope.book.tags = _.pluck($scope.book.tags, 'id');
  });
});