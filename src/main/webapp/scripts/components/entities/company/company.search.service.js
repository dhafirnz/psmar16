'use strict';

angular.module('psmar16App')
    .factory('CompanySearch', function ($resource) {
        return $resource('api/_search/companys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
