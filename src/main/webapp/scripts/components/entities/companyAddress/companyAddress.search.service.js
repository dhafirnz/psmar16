'use strict';

angular.module('psmar16App')
    .factory('CompanyAddressSearch', function ($resource) {
        return $resource('api/_search/companyAddresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
