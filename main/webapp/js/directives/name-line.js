var nameLine = angular.module("dzl.directives");
nameLine.directive('nameLine', function (userAuth) {
    return {
        replace: true,
        restrict: "E",
        scope: {
            btnAction: "=",
            editAction: "=",
            btnText: "=",
            name: "="
        },
        templateUrl: "templates/name-line.html",
        link: function (scope, elem, attrs) {
            scope.state = {
                edit: false
            };
            scope.done = function () {
                scope.state.edit = false;
                if (scope.editAction) scope.editAction(scope.name);
            };
            scope.userAuth = userAuth;
            scope.signinToSave = "sign in with Google to persist names between sessions";

        }
    };
});