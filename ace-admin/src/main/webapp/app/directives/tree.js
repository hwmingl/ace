/**
 * Created by Administrator on 2015/7/10.
 */
copyright.directive('tree', function() {
    return {
        restrict: 'E',
        link:function($scope,element,attrs){
            var zNodes = [
            {text :111111, name: "普通的父节点",type:"folder",additionalParameters:{'children' : [
               {name: 'Appliances', type: 'item'},
               {name: 'Arts & Crafts', type: 'item'},
               {name: 'Clothing', type: 'item'},
               {name: 'Computers', type: 'item'},
                 {name: 'Jewelry', type: 'item'},
                {name: 'Office & Business', type: 'item'},
               {name: 'Sports & Fitness', type: 'item'}
            ]} },
            {text :111111, name: "叶子节点 - 1",type:"folder",additionalParameters:{}},
            { text :111111, name: "叶子节点 - 2", type:"folder",additionalParameters:{}}];
            var dataSource = function (data) {
                this._data = data;
                /*
                 if (parent_id !== null) {
                 $.ajax({
                 url: remoteUrl,
                 data: 'id=' +parent_id,
                 type: 'POST' ,
                 dataType: 'json' ,
                 success : function (response) {
                 if (response.status == "OK" )
                 callback({ data: response.data })
                 },
                 error: function (response) {
                 //console.log(response);
                 }
                 })
                 }*/
            };
            dataSource.prototype.data = function(options, callback) {
                var $data = null;
                if("type" in options && options.type == "folder") {
                    if("additionalParameters" in options && "children" in options.additionalParameters)
                        $data = options.additionalParameters.children;
                    else $data = {}//no data
                }else{
                    $data=this._data
                }
                if($data!=null)
                callback({data: $data });
            };
            element.ace_tree({
                    dataSource: new dataSource(zNodes) ,
                    multiSelect:true,
                    loadingHTML:'<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
                    'open-icon' : 'icon-minus',
                    'close-icon' : 'icon-plus',
                    'selectable' : true,
                    'selected-icon' : 'icon-ok',
                    'unselected-icon' : 'icon-remove'
            });
        },
        replace:true,
        template:'<div class="tree"></div>'
    };
});


/* App Module */
/*
var appModule = angular.module('app', []);
appModule.directive('tree', function () {
    return {
            require: '?ngModel',
    restrict: 'A',
    link: function ($scope, element, attrs, ngModel) {
//var opts = angular.extend({}, $scope.$eval(attrs.nlUploadify));
        var setting = {
                data: {
            key: {
                title: "t"
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                $scope.$apply(function () {
                    ngModel.$setViewValue(treeNode);
                });
            }
        }
    };
    var zNodes = [
        { id: 1, pId: 0, name: "普通的父节点", t: "我很普通，随便点我吧", open: true },
        { id: 11, pId: 1, name: "叶子节点 - 1", t: "我很普通，随便点我吧" },
        { id: 12, pId: 1, name: "叶子节点 - 2", t: "我很普通，随便点我吧" },
        { id: 13, pId: 1, name: "叶子节点 - 3", t: "我很普通，随便点我吧" },
        { id: 2, pId: 0, name: "NB的父节点", t: "点我可以，但是不能点我的子节点，有本事点一个你试试看？", open: true },
        { id: 21, pId: 2, name: "叶子节点2 - 1", t: "你哪个单位的？敢随便点我？小心点儿..", click: false },
        { id: 22, pId: 2, name: "叶子节点2 - 2", t: "我有老爸罩着呢，点击我的小心点儿..", click: false },
        { id: 23, pId: 2, name: "叶子节点2 - 3", t: "好歹我也是个领导，别普通群众就来点击我..", click: false },
        { id: 3, pId: 0, name: "郁闷的父节点", t: "别点我，我好害怕...我的子节点随便点吧...", open: true, click: false },
        { id: 31, pId: 3, name: "叶子节点3 - 1", t: "唉，随便点我吧" },
        { id: 32, pId: 3, name: "叶子节点3 - 2", t: "唉，随便点我吧" },
        { id: 33, pId: 3, name: "叶子节点3 - 3", t: "唉，随便点我吧" }
    ];
    $.fn.zTree.init(element, setting, zNodes);
}
};
});
appModule.controller('MyController', function ($scope) {
});
*/
