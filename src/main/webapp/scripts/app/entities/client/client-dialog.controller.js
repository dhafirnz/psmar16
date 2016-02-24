'use strict';

angular.module('psmar16App').controller('ClientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Client', 'ClientAddress', 'Company',
        function($scope, $stateParams, $uibModalInstance, entity, Client, ClientAddress, Company) {

        $scope.client = entity;
        $scope.clientaddresss = ClientAddress.query();
        $scope.companys = Company.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('psmar16App:clientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveSuccess, onSaveError);
            } else {
                Client.save($scope.client, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
