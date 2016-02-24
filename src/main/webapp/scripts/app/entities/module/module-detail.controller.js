'use strict';

angular.module('psmar16App')
    .controller('ModuleDetailController', function ($scope, $rootScope, $stateParams, entity, Module, Company) {
        $scope.module = entity;
        $scope.load = function (id) {
            Module.get({id: id}, function(result) {
                $scope.module = result;
            });
        };
        var unsubscribe = $rootScope.$on('psmar16App:moduleUpdate', function(event, result) {
            $scope.module = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
