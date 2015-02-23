var app = angular.module('dzl.controllers');

app.controller('ContinentCtrl', function($scope, GridCalls){
    var result = GridCalls.get({}, function(val, headers){
        $scope.grid = result.data;
    });

    $scope.advance = function(years){
        var result = GridCalls.advance(
            {grid: $scope.grid, years: years},
            function(val, headers){
                $scope.grid = result.data;
            });
    }
});