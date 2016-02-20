'use strict';

angular.module('psmar16App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


