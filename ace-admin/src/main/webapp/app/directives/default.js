/**
 * Created by xiangnan.nie on 2015/7/2.
 */
copyright.directive('tableFooter', function() {
        return {
            restrict: 'E',
            templateUrl:'./app/partials/tableFooter.html'
        };
 });

copyright.directive('addContract', function() {
    return {
        restrict: 'E',
        scope:true,
        templateUrl:'./app/partials/addContract.html'
    };
});

copyright.directive('addSongList', function() {
    return {
        restrict: 'E',
        scope:true,
        templateUrl:'./app/partials/addSongList.html'
    };
});


