'use strict';

/**
 * Book view controller.
 */
App.controller('BookView', function($scope, $q, $timeout, $state, $stateParams, Restangular) {
  /**
   * Delete the book.
   */
  $scope.deleteBook = function() {
    if(confirm('Do you really want to delete this book?')) {
      Restangular.one('book', $stateParams.id).remove().then(function() {
        $state.transitionTo('book');
      });
    }
  };

  /**
   * Edit the book.
   */
  $scope.editBook = function() {
    $state.transitionTo('bookedit', { id: $stateParams.id });
  }

  /**
   * Edit the book cover.
   */
  $scope.editCover = function() {
    var url = prompt('Book cover image URL (only JPEG supported)');
    if (url) {
      $scope.coverChanging = true;

      Restangular.one('book', $stateParams.id).post('cover', {url: url}).then(function() {
        $scope.coverChanging = false;
      }, function() {
        alert('Error downloading the book cover, please check the URL');
        $scope.coverChanging = false;
      });
    }
  };

  // True if the cover is in the process of being changed
  $scope.coverChanging = false;

  // Load tags
  var tagsPromise = Restangular.one('tag/list').get().then(function(data) {
    $scope.tags = data.tags;
  });
  
  // Load book
  var bookPromise = Restangular.one('book', $stateParams.id).get().then(function(data) {
    $scope.book = data;
  })

  // Wait for everything to load
  $q.all([bookPromise, tagsPromise]).then(function() {
    $timeout(function () {
      // Initialize active tags
      _.each($scope.tags, function(tag) {
        var found = _.find($scope.book.tags, function(bookTag) {
          return tag.id == bookTag.id;
        });
        tag.active = found !== undefined;
      });

      // Initialize read state
      $scope.book.read = $scope.book.read_date != null;

      // Watch tags activation
      $scope.$watch('tags', function(prev, next) {
        if (prev && next && !angular.equals(prev, next)) {
          Restangular.one('book', $stateParams.id).post('', {
            tags: _.pluck(_.where($scope.tags, { active: true }), 'id')
          })
        }
      }, true);

      // Watch read state change
      $scope.$watch('book.read', function(prev, next) {
        if (prev !== next) {
          Restangular.one('book', $stateParams.id).post('read', {
            read: $scope.book.read
          }).then(function() {
                if ($scope.book.read) {
                  $scope.book.read_date = new Date().getTime();
                } else {
                  $scope.book.read_date = null;
                }
              });
        }
      }, true);
    }, 1);
  });
});