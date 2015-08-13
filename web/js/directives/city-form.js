var cityForm = angular.module("dzl.directives");
cityForm.directive('cityForm', function () {
    return {
        restrict: 'E',
        scope: {
            cityForm: "=",
            state: "=",
            generate: "="
        },
        templateUrl: 'templates/city-form.html',
        link: function (scope, elem, attrs) {
            scope.cityVals = {
                size: [
                    'Village', 'Town', 'City', 'Capital'
                ],
                species: [
                    'Human', 'Elf', 'Dwarf', 'Gnome', 'Orc', 'Goliath', 'Dragonborn', 'Halfling'
                ],
                diversity: [
                    'Low', 'Medium', 'High', 'Equal'
                ]
            };
            scope.cityForm = {
                size: "", spec: "", div: ""
            };
        }
    };
});