'use strict';

/**
 * Sismics Books application.
 */
var App = angular.module('books',
    // Dependencies
    ['ui.router', 'restangular', 'ngSanitize',
      'colorpicker.module', 'ui.keypress', 'ui.validate',]
  )

/**
 * Configuring modules.
 */
.config(function($stateProvider, $httpProvider, RestangularProvider) {
  // Configuring UI Router
  $stateProvider
  .state('main', {
    url: '',
    views: {
      'page': {
        templateUrl: 'partial/main.html',
        controller: 'Main'
      }
    }
  })
  .state('bookaddmanual', {
    url: '/book/addmanual',
    views: {
      'page': {
        templateUrl: 'partial/book.edit.html',
        controller: 'BookAddManual'
      }
    }
  })
  .state('book', {
    url: '/book/:tag',
    views: {
      'page': {
        templateUrl: 'partial/book.html',
        controller: 'Book'
      }
    }
  })
  .state('bookview', {
    url: '/book/view/:id',
    views: {
      'page': {
        templateUrl: 'partial/book.view.html',
        controller: 'BookView'
      }
    }
  })
  .state('bookedit', {
    url: '/book/edit/:id',
    views: {
      'page': {
        templateUrl: 'partial/book.edit.html',
        controller: 'BookEdit'
      }
    }
  })
  .state('bookadd', {
    url: '/book/add/:isbn',
    views: {
     'page': {
       templateUrl: 'partial/book.add.html',
       controller: 'BookAdd'
     }
    }
  })
  .state('login', {
    url: '/login',
    views: {
      'page': {
        templateUrl: 'partial/login.html',
        controller: 'Login'
      }
    }
  })
  .state('tag', {
    url: '/tag',
    views: {
      'page': {
        templateUrl: 'partial/tag.html',
        controller: 'Tag'
      }
    }
  })
  .state('settings', {
    url: '/settings',
    abstract: true,
    views: {
      'page': {
        templateUrl: 'partial/settings.html',
        controller: 'Settings'
      }
    }
  })
    .state('settings.default', {
      url: '',
      views: {
        'settings': {
          templateUrl: 'partial/settings.default.html',
          controller: 'SettingsDefault'
        }
      }
    })
    .state('settings.account', {
      url: '/account',
      views: {
        'settings': {
          templateUrl: 'partial/settings.account.html',
          controller: 'SettingsAccount'
        }
      }
    })
    .state('settings.session', {
      url: '/session',
      views: {
        'settings': {
          templateUrl: 'partial/settings.session.html',
          controller: 'SettingsSession'
        }
      }
    })
    .state('settings.log', {
      url: '/log',
      views: {
        'settings': {
          templateUrl: 'partial/settings.log.html',
          controller: 'SettingsLog'
        }
      }
    })
    .state('settings.user', {
      url: '/user',
      views: {
        'settings': {
          templateUrl: 'partial/settings.user.html',
          controller: 'SettingsUser'
        }
      }
    })
      .state('settings.user.edit', {
        url: '/edit/:username',
        views: {
          'user': {
            templateUrl: 'partial/settings.user.edit.html',
            controller: 'SettingsUserEdit'
          }
        }
      })
      .state('settings.user.add', {
        url: '/add',
        views: {
          'user': {
            templateUrl: 'partial/settings.user.edit.html',
            controller: 'SettingsUserEdit'
          }
        }
      });
  
  // Configuring Restangular
  RestangularProvider.setBaseUrl('api');
  
  // Configuring $http to act like jQuery.ajax
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
  $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
  $httpProvider.defaults.transformRequest = [function(data) {
    var param = function(obj) {
      var query = '';
      var name, value, fullSubName, subName, subValue, innerObj, i;
      
      for(name in obj) {
        value = obj[name];
        
        if(value instanceof Array) {
          for(i=0; i<value.length; ++i) {
            subValue = value[i];
            fullSubName = name;
            innerObj = {};
            innerObj[fullSubName] = subValue;
            query += param(innerObj) + '&';
          }
        } else if(value instanceof Object) {
          for(subName in value) {
            subValue = value[subName];
            fullSubName = name + '[' + subName + ']';
            innerObj = {};
            innerObj[fullSubName] = subValue;
            query += param(innerObj) + '&';
          }
        }
        else if(value !== undefined && value !== null) {
          query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
      }
      
      return query.length ? query.substr(0, query.length - 1) : query;
    };
    
    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
})

/**
 * Application initialization.
 */
.run(function($rootScope, $state, $stateParams) {
  $rootScope.$state = $state;
  $rootScope.$stateParams = $stateParams;
});