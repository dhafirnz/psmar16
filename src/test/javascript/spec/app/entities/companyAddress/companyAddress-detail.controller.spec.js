'use strict';

describe('Controller Tests', function() {

    describe('CompanyAddress Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCompanyAddress, MockCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCompanyAddress = jasmine.createSpy('MockCompanyAddress');
            MockCompany = jasmine.createSpy('MockCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CompanyAddress': MockCompanyAddress,
                'Company': MockCompany
            };
            createController = function() {
                $injector.get('$controller')("CompanyAddressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'psmar16App:companyAddressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
