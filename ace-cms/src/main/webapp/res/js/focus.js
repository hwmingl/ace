$(function( ) {
	var sWidth = resizew();	
	$('#focus,#focus ul li,').css({width:sWidth});
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	//以下代码添加数字按钮
	var btn = "<div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div>";
	$("#btn").append(btn);
	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#btn .btn span").mouseenter(function() {
		index = $("#btn .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");
	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},5000); //此5000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) {//普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},500); //通过animate()调整ul元素滚动到计算出的position
		$("#btn .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		var imgLeft = (sWidth-$("#focus ul li img").eq(index).width())/2;
		$("#focus ul li img").eq(index).css("left",imgLeft);
	}
	$(window).resize(function() {
          sWidth = $(window).width();
		$('#focus,#focus ul li').css({width:sWidth});
		$("#focus ul").css("width",sWidth * (len));
		showPics(index);
    });
	
});
function resizew (){
	var wid=$(window).width();
	$(window).resize(function() {
        wid = $(window).width(); 
    });
	return wid;
}

 

 