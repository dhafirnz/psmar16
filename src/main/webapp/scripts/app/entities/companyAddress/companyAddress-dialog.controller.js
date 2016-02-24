'use strict';

angular.module('psmar16App').controller('CompanyAddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyAddress', 'Company',
        function($scope, $stateParams, $uibModalInstance, entity, CompanyAddress, Company) {

        $scope.companyAddress = entity;
        $scope.companys = Company.query();
        $scope.load = function(id) {
            CompanyAddress.get({id : id}, function(result) {
                $scope.companyAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('psmar16App:companyAddressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.companyAddress.id != null) {
                CompanyAddress.update($scope.companyAddress, onSaveSuccess, onSaveError);
            } else {
                CompanyAddress.save($scope.companyAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
