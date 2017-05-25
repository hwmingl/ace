
copyright.controller('ncsoCtrl', function($scope,$filter,$q, $location,baseTableService,dataLoadService) {
    $scope.$on(
        "$routeUpdate",
        function () {
            $scope.render();
        }
    );

    $scope.init=function(){

        $scope.render();
    };

    $scope.init();

});