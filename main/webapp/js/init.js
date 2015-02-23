angular.module('dzl.resources', ['ngResource']);
angular.module('dzl.services', ['dzl.resources']);
angular.module('dzl.controllers', ['dzl.services']);
angular.module('dzl.directives', ['dzl.controllers', 'dzl.services', 'ui.bootstrap.tooltip']);