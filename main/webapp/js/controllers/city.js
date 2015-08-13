angular.module('dzl.controllers')
    .controller("CityCtrl", function ($scope, CityStore) {
        $scope.generate = CityStore.generate;
        $scope.getNew = CityStore.getNew;
        $scope.city = CityStore.city;
        $scope.cityForm = {};
        $scope.state = CityStore.state;
    });