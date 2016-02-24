'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {

        $stateProvider
            .state('modulegrid', {
                parent: 'site',
                url: '/modulegrid',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Modules are here'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/landing/landing-modules.html'
                    }
                }
            })
    });
