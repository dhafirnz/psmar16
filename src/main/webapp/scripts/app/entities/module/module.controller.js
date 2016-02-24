'use strict';

angular.module('psmar16App')
    .controller('ModuleController', function ($scope, $state, Module, ModuleSearch) {

        $scope.modules = [];
        $scope.loadAll = function() {
            Module.query(function(result) {
               $scope.modules = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ModuleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.modules = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.module = {
                code: null,
                name: null,
                resource: null,
                id: null
            };
        };
    });
