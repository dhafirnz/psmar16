'use strict';

angular.module('psmar16App')
    .factory('ClientAddressSearch', function ($resource) {
        return $resource('api/_search/clientAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
