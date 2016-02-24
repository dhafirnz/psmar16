'use strict';

angular.module('psmar16App').controller('ModuleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Module', 'Company',
        function($scope, $stateParams, $uibModalInstance, entity, Module, Company) {

        $scope.module = entity;
        $scope.companys = Company.query();
        $scope.load = function(id) {
            Module.get({id : id}, function(result) {
                $scope.module = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('psmar16App:moduleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.module.id != null) {
                Module.update($scope.module, onSaveSuccess, onSaveError);
            } else {
                Module.save($scope.module, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
