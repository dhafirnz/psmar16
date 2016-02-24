'use strict';

angular.module('psmar16App')
    .controller('CompanyAddressDetailController', function ($scope, $rootScope, $stateParams, entity, CompanyAddress, Company) {
        $scope.companyAddress = entity;
        $scope.load = function (id) {
            CompanyAddress.get({id: id}, function(result) {
                $scope.companyAddress = result;
            });
        };
        var unsubscribe = $rootScope.$on('psmar16App:companyAddressUpdate', function(event, result) {
            $scope.companyAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
