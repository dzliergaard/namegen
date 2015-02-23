var nameCalls = angular.module('dzl.resources');
nameCalls.factory('NameCalls', function ($resource) {
    function toArray(data, headers) {
        data = data ? angular.fromJson(data) : [];
        return {data: data};
    };
    function toString(data, headers) {
        data = data ? angular.fromJson(data) : "";
        return {data: data};
    };
    return $resource('/name/:action', null, {
        get: {
            method: 'GET',
            params: {
                action: 'get'
            },
            transformResponse: toArray
        },
        generate: {
            method: 'GET',
            params: {
                action: 'generate'
            },
            isArray: true
        },
        remove: {
            method: 'POST',
            params: {
                action: 'delete'
            }
        },
        save: {
            method: 'POST',
            params: {
                action: 'save'
            },
            transformResponse: toString
        }
    });
});