'use strict';

angular.module('psmar16App').controller('ClientAddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClientAddress', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, ClientAddress, Client) {

        $scope.clientAddress = entity;
        $scope.clients = Client.query();
        $scope.load = function(id) {
            ClientAddress.get({id : id}, function(result) {
                $scope.clientAddress = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('psmar16App:clientAddressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.clientAddress.id != null) {
                ClientAddress.update($scope.clientAddress, onSaveSuccess, onSaveError);
            } else {
                ClientAddress.save($scope.clientAddress, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
