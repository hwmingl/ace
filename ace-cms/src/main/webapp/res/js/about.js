var aboutJs = {
    titleArr: ['公司简介', '版权声明', '免责声明', '关于隐私', '关于智造网', '联系方式', '投稿方式', '审核标准', '网站留言'],
	checkChange: function(){
		var _url = window.location.href.split('#')[0];
		// console.log(_url);
		var _this = this;
		$('.about_left ul').children('li').click(function(){
			// console.log($(this).index());
			var i = $(this).index();
			$(this).siblings().removeClass('active');
			$(this).addClass('active');
			$('.about_line').eq(i).siblings().hide();
			$('.about_line').eq(i).show();
			window.location.href = _url + '#' +(i+1);
			aboutJs.changeTitle(i);
			$('body').scrollTop(0);
		})
	},
	/*autoHeight: function(){
		var h = $(window).height();
		$('.about_right').css('min-height',h-510+'px');
	},*/
	urlIndex: function(){
		var _this = this;
		var u = window.location.href;
		var i = u.split('#')[1]-1;
		// console.log(i);
		if(i){
			$('.about_left ul').children('li').eq(i).siblings().removeClass('active');
			$('.about_left ul').children('li').eq(i).addClass('active');
			$('.about_line').eq(i).siblings().hide();
			$('.about_line').eq(i).show();
			// _this.alignHeight();
		}else{return;}
	},
	changeTitle: function(i){
		$('title').html(aboutJs.titleArr[i]);
	}
}

$(document).ready(function(){
	// aboutJs.autoHeight();
	// aboutJs.alignHeight();
	var hash = location.hash.split('#')[1];
	aboutJs.changeTitle(hash-1);
	aboutJs.checkChange();
	aboutJs.urlIndex();
})
