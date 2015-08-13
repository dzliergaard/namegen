var nameList = angular.module("dzl.directives");
nameList.directive('nameList', function () {
    return {
        restrict: "AE",
        templateUrl: "templates/name-list.html",
        scope: {
            list: "=",
            btnAction: "=",
            editAction: "=",
            btnText: "@"
        }
    };
});