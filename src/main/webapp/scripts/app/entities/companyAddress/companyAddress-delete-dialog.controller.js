'use strict';

angular.module('psmar16App')
	.controller('CompanyAddressDeleteController', function($scope, $uibModalInstance, entity, CompanyAddress) {

        $scope.companyAddress = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CompanyAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
