angular.module('dzl.resources')
    .factory('DungeonCalls', function ($resource) {
        function toArray(data, headers) {
            data = data ? angular.fromJson(data) : [];
            return {data: data};
        }
        function toString(data, headers) {
            data = data ? angular.fromJson(data) : "";
            return {data: data};
        }
        function toOb(data, headers) {
            return {data: angular.fromJson(data)};
        }
        return $resource('/dungeon/:action', null, {
            get: {method: 'GET', params: {action: 'get'}, transformResponse: toArray},
            generate: {method: 'GET', params: {action: 'generate', maxRooms: 'maxRooms'}, transformResponse: toOb}
        });
    });