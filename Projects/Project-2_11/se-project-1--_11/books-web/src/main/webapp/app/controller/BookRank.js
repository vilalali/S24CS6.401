'use strict';

App.controller('BookRank', function($scope, $state, Restangular) {
  $scope.rankOption = ''; // Holds the user's choice of ranking
  $scope.rankedBooks = []; // Array to hold the ranked books
  $scope.rankingInitiated = false; // Flag to indicate if ranking has been initiated

  $scope.rankBooks = function() {
    $scope.rankingInitiated = true; // Set flag to true when search begins
    // Sends a POST request to the backend with the ranking type
    console.log('options books:', $scope.rankOption);

    Restangular.one('bookranking/rank').post('',{
      rankingType: $scope.rankOption,
      }).then(function(response) {
        $scope.rankedBooks = response.rankedBooks;
        }, function(error) {
          $scope.rankingInitiated = false; // Reset flag on error
        });

    // Restangular.all('bookranking/rank').post('',{ rankingType: $scope.rankOption })
    //   .then(function(response) {
    //     console.log('Ranked books:', response);
    //     $scope.rankedBooks = response.rankedBooks; // Assuming the response wraps the ranked books array in an object
    //   }, function(error) {
    //     console.error('Error fetching ranked books:', error);
    //     $scope.rankingInitiated = false; // Reset flag on error
    //   });
  };
});
