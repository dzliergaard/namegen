var inputSpan = angular.module('dzl.directives');
inputSpan.directive('inputSpan', function(){
    return {
        restrict: "EA",
        templateUrl: "templates/input-span.html",
        scope: {
            inputSpan: "=",
            save: "="
        },
        link: function(scope, elem, attrs){
            scope.state = {
                editing: false
            };
            scope.done = function(){
                scope.state.editing = false;
                scope.save();
            };
            scope.edit = function(){
                scope.state.editing = true;
                elem.find('input')[0].focus();
            };

            scope.$watch('state.editing', function (newValue) {
                newValue && elem.find('input')[0].focus();
            });
        }
    };
});