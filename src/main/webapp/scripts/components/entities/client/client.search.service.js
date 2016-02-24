'use strict';

angular.module('psmar16App')
    .factory('ClientSearch', function ($resource) {
        return $resource('api/_search/clients/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
