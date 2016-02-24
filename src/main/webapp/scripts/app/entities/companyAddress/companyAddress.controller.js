'use strict';

angular.module('psmar16App')
    .controller('CompanyAddressController', function ($scope, $state, CompanyAddress, CompanyAddressSearch) {

        $scope.companyAddresss = [];
        $scope.loadAll = function() {
            CompanyAddress.query(function(result) {
               $scope.companyAddresss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            CompanyAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.companyAddresss = result;
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
            $scope.companyAddress = {
                addressLine1: null,
                addressLine2: null,
                suburb: null,
                town: null,
                city: null,
                postcode: null,
                state: null,
                country: null,
                type: null,
                id: null
            };
        };
    });
