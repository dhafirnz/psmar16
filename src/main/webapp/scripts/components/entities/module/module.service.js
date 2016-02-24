'use strict';

angular.module('psmar16App')
    .factory('Module', function ($resource, DateUtils) {
        return $resource('api/modules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
