

copyright.controller('menuCtrl', function ($scope,$q,dataLoadService) {

    $scope.init=function(){
        $scope.menuItems=new Array();
        $q.when(dataLoadService.get(COPYRIGHT_URL + "user/menu")).then(function (response) {
            $scope.subMenus =  response.data.subMenus;
        });
    };

    $scope.init();
});


copyright.controller('userCtrl', function ($scope,$q,dataLoadService) {

    $scope.init=function(){
        $scope.loginout= LOGIN_OUT;

        $q.when(dataLoadService.get(COPYRIGHT_URL + "user")).then(function (response) {
            $scope.user =  response.data;
        });
    };
    $scope.init();
});