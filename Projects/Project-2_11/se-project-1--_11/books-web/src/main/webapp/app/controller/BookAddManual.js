'use strict';

/**
 * Book add manually controller.
 */
App.controller('BookAddManual', function($scope, $state, $stateParams, Restangular) {
  $scope.isEdit = false;
  $scope.genres = []; // Initialize the genres list as empty
  $scope.book = {
    tags: [],
    selectedGenres: [] // This will store the selected genre IDs
  };

  // Function to fetch genres from the backend
  function fetchGenres() {
    // Make a GET request to the `/genre/list` endpoint
    Restangular.one('genre/list').get().then(function(response) {
      // Assuming the response is in the format { genres: [{id: ..., name: ...}, ...]}
      $scope.genres = response.genres; // Update the genres list with the response data
    }, function(error) {
      console.error('Error fetching genres:', error);
    });
  }
  fetchGenres(); // Call the function to fetch genres when the controller initializes


  /**
   * Create the new book.
   */
  $scope.edit = function() {
    $scope.book.publish_date = Date.parse($scope.book.publish_date_year + '-01-01');
    // Prepare the book data for submission
    var bookData = {
      ...$scope.book,
      genres: $scope.book.selectedGenres // Ensure that selected genres are included in the submission
    };
    delete bookData.selectedGenres; // Remove temporary selectedGenres property not expected by backend
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