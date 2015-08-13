angular.module('dzl.resources')
    .factory('GridCalls', function ($resource) {
        function toArray(data, headers) {
            data = data ? angular.fromJson(data) : [];
            return {data: data};
        };
        function toString(data, headers) {
            data = data ? angular.fromJson(data) : "";
            return {data: data};
        };
        function toOb(data, headers) {
            return {data: angular.fromJson(data)};
        };
        return $resource('/grid/:action', null, {
            get: {method: 'GET', params: {action: 'get'}, transformResponse: toArray},
            advance: {url: 'grid/advance', method: 'POST', data: {grid: '@grid', years: '@years'}, transformResponse: toOb}
            /*,
            generate: {method: 'GET', params: {action: 'generate', size: '@size', race: '@race', diversity: '@diversity'}, transformResponse: toOb},
            remove: {method: 'POST', params: {action: 'delete', name: '@name'}},
            save: {method: 'POST', params: {action: 'save', name: '@name'}, transformResponse: toString}*/
        });
    });