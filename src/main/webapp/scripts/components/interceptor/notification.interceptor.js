 'use strict';

angular.module('psmar16App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-psmar16App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-psmar16App-params')});
                }
                return response;
            }
        };
    });
