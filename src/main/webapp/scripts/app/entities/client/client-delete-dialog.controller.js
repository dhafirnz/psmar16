'use strict';

angular.module('psmar16App')
	.controller('ClientDeleteController', function($scope, $uibModalInstance, entity, Client) {

        $scope.client = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
