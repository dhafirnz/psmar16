'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('module', {
                parent: 'entity',
                url: '/modules',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.module.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/modules.html',
                        controller: 'ModuleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('module.detail', {
                parent: 'entity',
                url: '/module/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.module.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/module/module-detail.html',
                        controller: 'ModuleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('module');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Module', function($stateParams, Module) {
                        return Module.get({id : $stateParams.id});
                    }]
                }
            })
            .state('module.new', {
                parent: 'module',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/module/module-dialog.html',
                        controller: 'ModuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    resource: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('module', null, { reload: true });
                    }, function() {
                        $state.go('module');
                    })
                }]
            })
            .state('module.edit', {
                parent: 'module',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/module/module-dialog.html',
                        controller: 'ModuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Module', function(Module) {
                                return Module.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('module', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('module.delete', {
                parent: 'module',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/module/module-delete-dialog.html',
                        controller: 'ModuleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Module', function(Module) {
                                return Module.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('module', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
