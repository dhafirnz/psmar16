'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('companyAddress', {
                parent: 'entity',
                url: '/companyAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.companyAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyAddress/companyAddresss.html',
                        controller: 'CompanyAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('companyAddress');
                        $translatePartialLoader.addPart('addressType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('companyAddress.detail', {
                parent: 'entity',
                url: '/companyAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.companyAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyAddress/companyAddress-detail.html',
                        controller: 'CompanyAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('companyAddress');
                        $translatePartialLoader.addPart('addressType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CompanyAddress', function($stateParams, CompanyAddress) {
                        return CompanyAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('companyAddress.new', {
                parent: 'companyAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyAddress/companyAddress-dialog.html',
                        controller: 'CompanyAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('companyAddress', null, { reload: true });
                    }, function() {
                        $state.go('companyAddress');
                    })
                }]
            })
            .state('companyAddress.edit', {
                parent: 'companyAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyAddress/companyAddress-dialog.html',
                        controller: 'CompanyAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CompanyAddress', function(CompanyAddress) {
                                return CompanyAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('companyAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('companyAddress.delete', {
                parent: 'companyAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/companyAddress/companyAddress-delete-dialog.html',
                        controller: 'CompanyAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CompanyAddress', function(CompanyAddress) {
                                return CompanyAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('companyAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
