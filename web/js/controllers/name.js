var nameCtrl = angular.module('dzl.controllers');

nameCtrl.controller('NameCtrl', function ($scope, NameStore) {
    $scope.state = NameStore.state;
    $scope.names = NameStore.names;
    $scope.saveName = NameStore.save;
    $scope.generate = NameStore.generate;
    $scope.remove = NameStore.remove;
});