'use strict';

describe('Controller Tests', function() {

    describe('Company Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCompany, MockCompanyAddress, MockModule, MockClient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCompany = jasmine.createSpy('MockCompany');
            MockCompanyAddress = jasmine.createSpy('MockCompanyAddress');
            MockModule = jasmine.createSpy('MockModule');
            MockClient = jasmine.createSpy('MockClient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Company': MockCompany,
                'CompanyAddress': MockCompanyAddress,
                'Module': MockModule,
                'Client': MockClient
            };
            createController = function() {
                $injector.get('$controller')("CompanyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'psmar16App:companyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
