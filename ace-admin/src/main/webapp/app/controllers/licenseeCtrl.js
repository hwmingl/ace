/**
 * Created by xnnie on 15-4-1.
 */

copyright.controller('licenceeCtrl', function($scope,$filter,$q, $location,baseTableService,dataLoadService) {
    $scope.$on(
        "$routeUpdate",
        function () {
            $scope.render();
        }
    );
    $scope.init=function(){
        $scope.licensetype=license_type;
        $scope.initial= initial;
        $scope.liceseeSign= licensee_sign;
        this.initCondition();
        this.initDateRange();
        $scope.table= baseTableService.getCustomizedTable($scope);
        $scope.render();
    };

    $scope.initCondition =  function (){
        $scope.condition = new Object();
        $scope.condition.pageNumber = 1;
        $scope.condition.pageSize =  25;
    };

    $scope.initDateRange =  function (){
        $scope.condition.endTime= moment().getDate;
        $scope.condition.startTime= moment().subtract(7, 'days').getDate,
            $('#daterange').daterangepicker({
                format: 'MM/DD/YYYY',
                startDate:  $scope.condition.startTime,
                endDate:  $scope.condition.endTime
            }, function(start, end, label) {
                $scope.condition.startTime=start.toDate().getTime();
                $scope.condition.endTime=end.toDate().getTime();
            });
    };

    $scope.search = function () {
        $scope.condition.pageNumber = 1;
        $location.search($scope.condition);
        $scope.render();
    };

    $scope.save = function () {
        this.checkCheckbox();
        if($scope.licesee.id==null){
            this.add();
        }else{
            this.update();
        }
        this.hiddenPopForm();
    };

    $scope.add = function () {
        $q.when(dataLoadService.post(COPYRIGHT_URL + "licensee", $scope.licesee)).then(function (response) {
        });
    };

    $scope.update = function () {
        var url=  $scope.url+'/license';
        $q.when(dataLoadService.put(COPYRIGHT_URL + "licensee", $scope.licesee)).then(function (response) {
        });
    };

    $scope.select =  function (licesee){
        if( $scope.licesee!=licesee){
            $scope.licesee = licesee;
            $scope.check=(licesee.isSign==1)
        }
    };

    $scope.render = function () {
        $scope.loading=true;
        $q.when(dataLoadService.get(COPYRIGHT_URL + "licensee", $scope.condition)).then(function (response) {
            $scope.dataList =  response.data.pageList;
            $scope.totalRecords=response.data.totalRecords;
            $scope.loading=false;
        });
    }

    $scope.hiddenPopForm =  function (){
        $('#modal-form').modal('hide');
        $scope.render();
    };

    $scope.clearSelect = function () {
        $scope.licesee=null;
    };

    $scope.checkCheckbox = function () {
        if($scope.check==true){
            $scope.licesee.isSign=1;
        }else{
            $scope.licesee.isSign=0;
        }
    };
    $scope.fullNameBlur = function () {
        if($scope.licesee.id==null){
            var fullName=$scope.licesee.fullName;
            if(fullName!=null){
                $q.when(dataLoadService.get(COPYRIGHT_URL + "licensee/checkName?fullname=", fullName)).then(function (response) {
                    if(response.data){
                        $scope. fullnameCheck=true;
                    }else{
                        $scope. fullnameCheck=false;
                    }
                });
            }
        }
    };

    $scope.addContract =  function (licesee){
        $scope.licenseeId=licesee.id;
    };
    $scope.init();



});

