'use strict';

angular.module('psmar16App')
    .controller('CompanyDetailController', function ($scope, $rootScope, $stateParams, entity, Company, CompanyAddress, Module, Client) {
        $scope.company = entity;
        $scope.load = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
            });
        };
        var unsubscribe = $rootScope.$on('psmar16App:companyUpdate', function(event, result) {
            $scope.company = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
