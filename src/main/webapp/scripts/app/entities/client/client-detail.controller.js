'use strict';

angular.module('psmar16App')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, ClientAddress, Company) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('psmar16App:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
