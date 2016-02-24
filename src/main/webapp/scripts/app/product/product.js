'use strict';

angular.module('psmar16App')
    .config(function ($stateProvider) {

        $stateProvider
            .state('product', {
                parent: 'site',
                url: '/product',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Modules are here'
                },
                views: {
                    'content@': {
                    templateUrl: 'scripts/app/product/product.html'
                    }
                }
            })
    });
