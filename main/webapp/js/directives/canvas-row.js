/**
 * Created by DZL on 6/9/14.
 */
angular.module('dzl.directives')
    .directive('canvasRow', function(){
        return {
            restrict: "AE",
            scope: {
                row: "="
            },
            templateUrl: "templates/canvas-row.html",
            replace: true,
            link: function(scope, elem, attrs){

            }
        };
    });