'use strict';

angular.module('psmar16App')
    .controller('ClientAddressDetailController', function ($scope, $rootScope, $stateParams, entity, ClientAddress, Client) {
        $scope.clientAddress = entity;
        $scope.load = function (id) {
            ClientAddress.get({id: id}, function(result) {
                $scope.clientAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('psmar16App:clientAddressUpdate', function(event, result) {
            $scope.clientAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
