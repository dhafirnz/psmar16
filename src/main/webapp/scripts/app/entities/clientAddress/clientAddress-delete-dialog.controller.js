'use strict';

angular.module('psmar16App')
	.controller('ClientAddressDeleteController', function($scope, $uibModalInstance, entity, ClientAddress) {

        $scope.clientAddress = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ClientAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
