'use strict';

/**
 * Book edit controller.
 */
App.controller('BookEdit', function($scope, $state, $stateParams, Restangular) {
  $scope.isEdit = true;
  $scope.genres = []; // To hold the list of genres
  $scope.book = {
    selectedGenres: [] // Initialize here to avoid undefined errors
  };

  // Function to fetch genres from the backend
  function fetchGenres() {
    Restangular.all('genres').getList().then(function(genres) {
        $scope.genres = genres;
        // If editing an existing book, you would also need to initialize $scope.book.selectedGenres here,
        // depending on how the genres are associated with the book in your backend and how they're returned.
        // This is just a placeholder; you'll need to adjust it based on your actual data structure.
    });
  }
  fetchGenres(); // Fetch genres when controller initializes  
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

  // Function to load the book being edited
  function loadBook() {
    Restangular.one('book', $stateParams.id).get().then(function(data) {
        $scope.book = data;
        $scope.book.publish_date_year = new Date($scope.book.publish_date).getFullYear();
        // Assuming tags are being plucked by ID for another part of your form, similar handling might be needed for genres
        $scope.book.tags = _.pluck($scope.book.tags, 'id');

        // Initialize selectedGenres based on the genres associated with the book
        // This assumes the book's genres are provided as an array of genre objects with id properties
        $scope.book.selectedGenres = $scope.book.genres.map(function(genre) {
            return genre.id;
        });
    });
  }
});