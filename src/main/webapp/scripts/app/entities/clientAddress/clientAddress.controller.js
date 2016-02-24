'use strict';

angular.module('psmar16App')
    .controller('ClientAddressController', function ($scope, $state, ClientAddress, ClientAddressSearch) {

        $scope.clientAddresss = [];
        $scope.loadAll = function() {
            ClientAddress.query(function(result) {
               $scope.clientAddresss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ClientAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.clientAddresss = result;
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
            $scope.clientAddress = {
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
