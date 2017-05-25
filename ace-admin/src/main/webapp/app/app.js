/**
 * Created by xnnie on 15-4-1.
 */
var copyright = angular.module('copyright', ['ngCookies']);
var portal = angular.module('portal', ['ngRoute','copyright']);
portal.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when(
            '/license', {
                templateUrl: 'app/partials/license.html',
                reloadOnSearch: false
            }).when(
            '/ncso', {
                templateUrl: 'app/partials/ncso.html',
                reloadOnSearch: false
            });
    }]);

