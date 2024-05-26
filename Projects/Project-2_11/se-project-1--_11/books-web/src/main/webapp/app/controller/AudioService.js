'use strict';
/**
 * Book add controller.
 */
App.controller('AudioService', function($scope, $http, $state, $stateParams, Restangular) {
  $scope.selectedService = ''; // Initialize selectedService
  $scope.itunesOption = ''; // Initialize itunesOption
  $scope.spotifyOption = ''; // Initialize spotifyOption
  $scope.podCastName = ''; // Initialize podCastName
  $scope.audiobookId = ''; // Initialize audiobookId

  $scope.submit = function () {
    if($scope.spotifyOption == 'audiobook' ||  $scope.itunesOption == 'audiobook') {
      $scope.audiobook();
    } else {
      $scope.addPodcast();
    }
  }

  $scope.addPodcast = function() {
    Restangular.one('audio/podcast').post('',{
      podcastName: $scope.podCastName,
      service: $scope.selectedService
      }).then(function(data) {
          data = JSON.parse(data.data)
          let finalData = itunesManager(data.results)
          $scope.audio = finalData
          $state.transitionTo('audio', { data: data });
        });
  };

  $scope.audiobook = function() {
    Restangular.one('audio/podcast').put({
      audioBookId: $scope.audiobookId,
      service: $scope.selectedService
      }).then(function(data) {
          data = JSON.parse(data.data)
          let finalData = itunesManager(data.results)
          $scope.audio = data

          $state.transitionTo('audio', { data: data });
        });
  };

  function itunesManager(data) {
    let tableData = ''
    data.forEach(element => {
      tableData += `
       <td>${element.artistName}</td>
       <td>${element.trackName}</td>
       <td>${element.collectionViewUrl}</td>
       `
    });

    return `
        <table>
            <tr>
                <th>Artist Name</th>
                <th>Track Name</th>
                <th>Collection View URL</th>
            </tr>
            <tr id="dataRow">
              ${tableData}
            </tr>
        </table>
    `
  }

});