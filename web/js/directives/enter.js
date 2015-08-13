var enter = angular.module("dzl.directives");
enter.directive('enter', function () {
    return {
        restrict: "A",
        link: function (scope, elem, attrs) {
            elem.on('keydown', function (evt) {
                if (evt.keyCode == 13) {
                    scope.$eval(attrs.enter);
                    evt.preventDefault();
                    scope.$apply();
                }
            });
        }
    };
});