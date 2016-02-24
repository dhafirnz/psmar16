'use strict';

angular.module('psmar16App')
    .factory('ModuleSearch', function ($resource) {
        return $resource('api/_search/modules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
