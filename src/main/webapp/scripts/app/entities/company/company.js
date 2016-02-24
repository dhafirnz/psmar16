'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('company', {
                parent: 'entity',
                url: '/companys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.company.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/companys.html',
                        controller: 'CompanyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('company');
                        $translatePartialLoader.addPart('companyStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('company.detail', {
                parent: 'entity',
                url: '/company/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'psmar16App.company.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/company-detail.html',
                        controller: 'CompanyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('company');
                        $translatePartialLoader.addPart('companyStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Company', function($stateParams, Company) {
                        return Company.get({id : $stateParams.id});
                    }]
                }
            })
            .state('company.new', {
                parent: 'company',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    phone: null,
                                    tollPhone: null,
                                    mobile: null,
                                    email: null,
                                    status: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('company');
                    })
                }]
            })
            .state('company.edit', {
                parent: 'company',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Company', function(Company) {
                                return Company.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('company.delete', {
                parent: 'company',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-delete-dialog.html',
                        controller: 'CompanyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Company', function(Company) {
                                return Company.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
