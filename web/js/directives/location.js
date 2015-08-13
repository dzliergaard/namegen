/**
 * Created by DZL on 6/9/14.
 */
angular.module('dzl.directives')
    .directive('location', function(){
        return {
            restrict: "E",
            replace: true,
            scope: {
                top: "=",
                left: "=",
                width: "=",
                height: "="
            },
            link: function(scope, elem, attrs){
                var ctx = elem[0].getContext("2d");
                scope.colors = colors;

                ctx.beginPath();
                ctx.strokeRect(0, 0, scope.width, scope.height);
            }
        };
    });