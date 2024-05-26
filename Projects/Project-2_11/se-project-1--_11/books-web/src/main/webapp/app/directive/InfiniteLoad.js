'use strict';

/**
 * Infinite scrolling directive.
 * Thanks to http://jsfiddle.net/vojtajina/U7Bz9/
 */
App.directive('infiniteLoad', function() {
  return function(scope, elm, attr) {
    var margin = parseInt(attr.infiniteMargin);

    // Scroll handler
    var scrollHandler = function() {
      if ($(this).scrollTop() + $(window).height() + margin >= elm.offset().top +  + elm.height()) {
        scope.$apply(attr.infiniteLoad);
      }
    };

    // Attach handler
    $(document).bind('scroll', scrollHandler);

    // Cleanup handler on destroy
    scope.$on('$destroy', function() {
      $(document).unbind('scroll', scrollHandler);
    })
  };
});