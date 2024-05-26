'use strict';

/**
 * Tag controller.
 */
App.controller('Tag', function($scope, $state, Restangular) {
  $scope.tag = { name: '', color: '#3a87ad', isPrivate: false };
  
  // Load tags
  Restangular.one('tag/list').get().then(function(data) {
    $scope.tags = data.tags;
  });
  
  /**
   * Validate a tag name for duplicate.
   */
  $scope.validateDuplicate = function(name) {
    return !_.find($scope.tags, function(tag) {
      return tag.name == name;
    });
  };
  
  /**
   * Add a tag.
   */
  $scope.addTag = function() {
    Restangular.one('tag').put($scope.tag).then(function(data) {
      $scope.tags.push({ id: data.id, name: $scope.tag.name, color: $scope.tag.color, isPrivate: $scope.tag.isPrivate });
      $scope.tag = { name: '', color: '#3a87ad', isPrivate: false };
    });
  };
  
  /**
   * Delete a tag.
   */
  $scope.deleteTag = function(tag) {
    if(confirm('Do you really want to delete this bookshelf?')) {
      Restangular.one('tag', tag.id).remove().then(function() {
        $scope.tags = _.reject($scope.tags, function(t) {
          return tag.id == t.id;
        });
      });
    }
  };
  
  /**
   * Update a tag.
   */
  $scope.updateTag = function(tag) {
    // Update the server
    return Restangular.one('tag', tag.id).post('', tag).then(function () {
      // No need to update the local tag object as it's already bound to the UI
    });
  };

  /**
   * Check if the current user can edit the tag.
   */
  $scope.canEditTag = function(tag) {
    return !tag.isPrivate || tag.userId === $scope.currentUser.id;
  };

  /**
   * Check if the current user can delete the tag.
   */
  $scope.canDeleteTag = function(tag) {
    return !tag.isPrivate || tag.userId === $scope.currentUser.id;
  };
});