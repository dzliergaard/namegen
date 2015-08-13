var cityTable = angular.module("dzl.directives");
cityTable.directive('cityTable', function () {
    return {
        restrict: 'E',
        scope: {
            city: "=",
            getNew: "="
        },
        templateUrl: 'templates/city-table.html'
    };
});