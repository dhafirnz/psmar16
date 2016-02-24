'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clientAddress', {
                parent: 'entity',
                url: '/clientAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.clientAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientAddress/clientAddresss.html',
                        controller: 'ClientAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientAddress');
                        $translatePartialLoader.addPart('addressType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clientAddress.detail', {
                parent: 'entity',
                url: '/clientAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.clientAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientAddress/clientAddress-detail.html',
                        controller: 'ClientAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientAddress');
                        $translatePartialLoader.addPart('addressType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ClientAddress', function($stateParams, ClientAddress) {
                        return ClientAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clientAddress.new', {
                parent: 'clientAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientAddress/clientAddress-dialog.html',
                        controller: 'ClientAddressDialogController',
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
                        $state.go('clientAddress', null, { reload: true });
                    }, function() {
                        $state.go('clientAddress');
                    })
                }]
            })
            .state('clientAddress.edit', {
                parent: 'clientAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientAddress/clientAddress-dialog.html',
                        controller: 'ClientAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ClientAddress', function(ClientAddress) {
                                return ClientAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clientAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('clientAddress.delete', {
                parent: 'clientAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientAddress/clientAddress-delete-dialog.html',
                        controller: 'ClientAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ClientAddress', function(ClientAddress) {
                                return ClientAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clientAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
