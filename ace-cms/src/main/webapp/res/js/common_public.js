/*
===================
通用JS
===================
*/

//获取域名链接
var domainURI = function (str){
    var durl=/(http:\/\/[^\/]+)\//i;
    var domain = str.match(durl);
    return domain[1];
}

//关闭artdialog
var closeartdialog = function(id){
	art.dialog({id: id}).close();
}

var thisdomain	= domainURI(document.location.href);
var thishref	= encodeURIComponent(document.location.href);
var islogin		= false;

//动态验证登录
var	loginarea = function(){
    if($('#login_area').length){
		$.getJSON(GV.SITE_ROOT+"api.php?a=public_checklogin&callback=?",function(data){
            var html = '';
			if(data.status == 1){
				islogin = true;
				html += '<div class="topNav"><dd><h3>';
				if(data.message != 0)
					html += '<a href="'+GV.SITE_ROOT+'user.php?a=inbox"><span class="message">'+data.message+'</span></a>';
				html += '<a href="'+GV.SITE_ROOT+'user.php"><img src="'+data.avatar+'"></a></h3>';
                html += '<ul>';
				html += '<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=5">点赞</a>';
				if(data.count4 != 0)
					html += '<span class="message_count">'+data.count4+'</span>';
				html += '</li>';
				html += '<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=4">评论</a>';
				if(data.count3 != 0)
					html += '<span class="message_count">'+data.count3+'</span>';
				html += '</li>';
				html += '<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=2">通知</a>';
				if(data.count1 != 0)
					html += '<span class="message_count">'+data.count1+'</span>';
				html += '</li>';
				html += '<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=3">私信</a>'
				if(data.count2 != 0)
					html += '<span class="message_count">'+data.count2+'</span>';
				html += '</li>';
				html += '<li><a href="'+GV.SITE_ROOT+'user.php?a=school">我的学校</a></li>';
				html += '<li><a href="'+data.url+'">我的主页</a></li>';
                html += '<li><a href="'+GV.SITE_ROOT+'user.php">个人中心</a></li>';
				html += '<li><a href="'+GV.SITE_ROOT+'myorder">我的订单</a></li>';
				html += '<li><a href="'+GV.SITE_ROOT+'api.php?m=Common&a=logout&forward='+thishref+'&rel=1">退出</a></li></ul></dd></div>';
				$('#login_area').html(html);
				$('#login_area').addClass('login-after');
				jQuery(".topNav").slide({ 
					type:"menu", //效果类型
					titCell:"dd", // 鼠标触发对象
					targetCell:"ul", // 效果对象，必须被titCell包含
					effect:"slideDown",//下拉效果
					delayTime:500, // 效果时间
					defaultPlay:false,  //默认不执行
					returnDefault:true // 返回默认
				});
            }else{
				html += '<a href="'+GV.SITE_ROOT+'user.php?m=Login">登录</a><span>|</span>';
				html += '<a href="'+GV.SITE_ROOT+'user.php?m=Register" target="_blank">注册</a>';
				$('#login_area').html(html);
				$('#login_area').addClass('login-before');
            }
            if(data.popup)
                popup_message(data.popup);
			if(data.everydaylog)
				art.dialog.success(data.everydaylog);
        });
    }

	if($('#login_new').length){
		$.getJSON(GV.SITE_ROOT+"api.php?a=public_checklogin&callback=?",function(data){
            var html = '';
			if(data.status == 1){
				islogin = true;
				html += '<div class="td-top-ucenter">';
				html += '<div class="td-uc-email"><a href="'+GV.SITE_ROOT+'user.php?a=inbox" class="td-email-link">';
				if(data.message != 0)
					html += '<i>'+(data.message > 99 ? '····' : data.message)+'</i>';
				html += '</a><ul>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=5">点赞 /<em>'+data.count4+'</em></a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=4">评论 /<em>'+data.count3+'</em></a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=2">通知 /<em>'+data.count1+'</em></a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php?a=inbox&type=3">私信 /<em>'+data.count2+'</em></a></li>'+
						'</ul>';
				html += '</div>';
				html += '<div class="td-uc-me">';
				html += '<a href="'+GV.SITE_ROOT+'user.php"><img src="'+data.avatar+'"></a>';
				html += '<ul>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php?a=school">我的学校</a></li>'+
							'<li><a href="'+data.url+'">我的主页</a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'user.php">个人中心</a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'myorder">我的订单</a></li>'+
							'<li><a href="'+GV.SITE_ROOT+'api.php?m=Common&a=logout&forward='+thishref+'&rel=1">退出</a></li>'+
						'</ul>';
				html += '</div>';
				html += '</div>';

				$('#login_new').html(html);
            }
            if(data.popup)
                popup_message(data.popup);
			if(data.everydaylog)
				art.dialog.success(data.everydaylog);
        });
    }

    if($('#login_area_en').length){
		$.getJSON(GV.SITE_ROOT+"api.php?a=public_checklogin&callback=?",function(data){
            var html = '';
			if(data.status == 1){
				islogin = true;
				html += '<div class="topNav"><dd><h3>';
				html += '<img src="'+data.avatar+'"></h3>';
                html += '<ul style="top:70px;"><li><a href="'+GV.SITE_ROOT+'user.php?a=logout&forward='+thishref+'&rel=1">Logout</a></li></ul></dd></div>';
				$('#login_area_en').html(html);
				$('#login_area_en').addClass('logn_after');
				jQuery(".topNav").slide({
					type:"menu", //效果类型
					titCell:"dd", // 鼠标触发对象
					targetCell:"ul", // 效果对象，必须被titCell包含
					delayTime:0, // 效果时间
					defaultPlay:false,  //默认不执行
					returnDefault:true // 返回默认
				});
            }else{
				html += '<a href="javascript:void:;" onclick="gotologin_en();">Login</a> / ';
				html += '<a href="'+GV.SITE_ROOT+'en.php?m=Register" target="_blank">Register</a>';
				$('#login_area_en').html(html);
				$('#login_area_en').addClass('logn_again');
            }
			if(data.everydaylog)
				art.dialog.success(data.everydaylog);
        });
    }
}

//去登录
var gotologin = function(){
    window.location.href = GV.SITE_ROOT+"user.php?m=Login&forward="+thishref;
}
//英文版登录
var gotologin_en = function(){
    window.location.href = GV.SITE_ROOT+"en.php?m=Login&forward="+thishref;
}

var gotourl = function(url){
    window.location.href = url;
}

var gotologout = function(){
	window.location.href = GV.SITE_ROOT+"api.php?m=Common&a=logout&rel=1&forward="+thishref;
}

//登录后下载
var login_down = function(url){
	if(check_login())
		gotourl(url);
}

//判断有没有登录
var check_login = function(){
	if(!islogin){
		art.dialog.error('您尚未登录');
		setTimeout(function(){gotologin();},2000);
        return false;
	}
	return true;
}

//通用下载附件
var common_downfile = function(onlyid){
	$.post(GV.SITE_ROOT+'api.php?a=common_download&onlyid='+onlyid, function(data) {
		if(data.status){
			window.location.href = data.downurl;
		}else{
			art.dialog.error(data.message);
			if(data.url){
				setTimeout("window.location.href=\""+data.url+"&forward="+thishref+"\"",1500);
			}
		}
	});
}

//图纸删除
var deletefile = function(id){
	art.dialog({
		title: false,
		icon: 'question',
		content: "确定要删除作品吗？",
		close: function () {
			return true;
		},
		ok: function () {
			$.getJSON(GV.SITE_ROOT+"user.php?m=Tuzhi&a=delete&id="+id+"&callback=?",function(result) {
				if(result.status == 1){
					art.dialog.success(result.message);
					setTimeout(function(){
						window.location.reload();
					},2000);
				}else{
					art.dialog.error(result.message);
				}
			});
		},
		cancelVal: '我再想想',
		cancel: true
	});
}

//更改附件
function changefile(id, title) {
    art.dialog.confirm(
        "确定更改作品吗？更改作品后需要重新审核。",
        function(){
            art.dialog.open(GV.SITE_ROOT+"user.php?m=Tuzhi&a=changefile&id="+id, 
            {
               	title: '更改作品《'+title+'》的附件',
               	width: 730,
            	fixed: true,
            	opacity: 0.3,
               	lock: true,
        	});
        },
        function(){return true;}
    );
}

//倒计时
var	countDown = function(time,day_elem,hour_elem,minute_elem,second_elem){
    sys_second=time;
    var timer = setInterval(function(){
        if (sys_second > 0) {
            sys_second -= 1;
            var day = Math.floor((sys_second / 3600) / 24);
            var hour = Math.floor((sys_second / 3600) % 24);
            var minute = Math.floor((sys_second / 60) % 60);
            var second = Math.floor(sys_second % 60);
            day_elem && $(day_elem).text(day);//计算天
            $(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
            $(minute_elem).text(minute<10?"0"+minute:minute);//计算分
            $(second_elem).text(second<10?"0"+second:second);// 计算秒
        } else {
            clearInterval(timer);
        }
    }, 1000);
}

var scrollto = function(id,speed){
    if(typeof(speed)=="undefined")speed=1000;
    var position = $('#'+id).offset().top;
    var myscroll = $(document).scrollTop();
    if(myscroll>position){
        $("html,body").animate({scrollTop:position},speed);
    }
}

var downto = function(id,speed){
    if(typeof(speed)=="undefined")speed=1000;
    var position = $('#'+id).offset().top;
    var myscroll = $(document).scrollTop();
    if(myscroll<=position){
        $("html,body").animate({scrollTop:position},speed);
    }
}

/*
===================
通用JS
===================
*/
//通用获得info信息并增加点击数
var get_commoninfo = function(onlyid){
	if(!onlyid) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=get_commoninfo&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
			if($('#views').length > 0)	$('#views').html(data.views);
			if($('#favs').length > 0)	$('#favs').html(data.favs);
			if($('#likes').length > 0)	$('#likes').html(data.likes);
			if($('#ctotal').length > 0)	$('#ctotal').html(data.ctotal);
			if($('#downcount').length > 0)	$('#downcount').html(data.downcount);
		}
	});
}

//添加关注，不显示关注数
var add_care = function(userid,returnid) {
	if(userid == 0) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_care&userid="+userid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())+1;
				$("#"+returnid).html(num);
			}
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//通用添加关注
var common_add_care = function(userid,obj,returnid,on,cancel){
	if(userid == 0) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_care&userid="+userid+"&callback=?", function(data){
		if(data.status){
            art.dialog.success(data.message);
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())+1;
				$("#"+returnid).html(num);
			}
			if ($(obj).hasClass('td-foucus')){
				$(obj).addClass('disabled').text('互相关注');
				$(obj).attr('onclick','');
				return false;
			}
            if ($(obj).text() == '马上关注') {
                $(obj).text('取消关注')
            }
			$(obj).addClass(on);
			if (cancel)
				$(obj).attr("onclick","common_del_care("+userid+", this, '"+returnid+"', '"+on+"', " + cancel + ")");
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//通用取消关注
var common_del_care = function(userid,obj,returnid,on,cancel){
	if(userid == 0) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_care&remove=1&userid="+userid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())-1;
				$("#"+returnid).html(num);
			}
            if ($(obj).text() == '取消关注') {
                $(obj).text('马上关注');
            }
			$(obj).removeClass(on);
			if (cancel)
				$(obj).attr("onclick","common_add_care("+userid+", this, '"+returnid+"', '"+on+"', " + cancel + ")");
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//订阅学社
var study_add_fans = function(study_id,obj,on,cancel){
	if(study_id == 0) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=study_fans&study_id="+study_id+"&callback=?", function(data){
		if(data.status){
			$(obj).addClass(on).html("已订阅");
			if (cancel)
				$(obj).attr("onclick","study_del_fans("+study_id+", this, '"+on+"', " + cancel + ")");
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//取消订阅学社
var study_del_fans = function(study_id,obj,on,cancel){
	if(study_id == 0) return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=study_fans&remove=1&study_id="+study_id+"&callback=?", function(data){
		if(data.status){
			$(obj).removeClass(on).html("订阅");
			if (cancel)
				$(obj).attr("onclick","study_add_fans("+study_id+", this, '"+on+"', " + cancel + ")");
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//收藏，不显示收藏数
var add_favs = function(onlyid,returnid) {
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_favs&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())+1;
				$("#"+returnid).html(num);
			}
			art.dialog.success(data.message);
        }else
            art.dialog.error(data.message);
    });
}

//通用加入收藏
var common_add_favs = function(onlyid,obj,returnid,on,cancel){
    $.getJSON(GV.SITE_ROOT+"api.php?a=common_favs&onlyid="+onlyid+"&callback=?", function(data){
        if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())+1;
				$("#"+returnid).html(num);
			}
			$(obj).addClass(on);
			if (cancel)
				$(obj).attr("onclick","common_del_favs('"+onlyid+"', this, '"+returnid+"', '"+on+"', "+cancel+")");
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//通用移出收藏
var common_del_favs = function(onlyid,obj,returnid,on,cancel){
    $.getJSON(GV.SITE_ROOT+"api.php?a=common_favs&remove=1&onlyid="+onlyid+"&callback=?", function(data){
        if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())-1;
				$("#"+returnid).html(num);
			}
			$(obj).removeClass(on);
			if (cancel)
				$(obj).attr("onclick","common_add_favs('"+onlyid+"', this, '"+returnid+"', '"+on+"', "+cancel+")");
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
            if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
    });
}

//点赞，不显示点赞数
var add_likes = function(onlyid,returnid) {
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_likes&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined'){
				var num=parseInt($("#"+returnid).html())+1;
				$("#"+returnid).html(num);
			}
			art.dialog.success(data.message);
        }else
            art.dialog.error(data.message);
    });
}

//通用点赞
function common_add_likes(ob){
//    $.getJSON(GV.SITE_ROOT+"api.php?a=common_likes&onlyid="+onlyid+"&callback=?", function(data){
//        if(data.status){
//			if(typeof(returnid) != 'undefined'){
//				var num=parseInt($("#"+returnid).html())+1;
//				$("#"+returnid).html(num);
//			}
//			$(obj).addClass(on);
//            art.dialog.success(data.message);
//        }else{
//            art.dialog.error(data.message);
//        }
//    });
 var id=$(ob).attr("id");
 $(ob).addClass("zan_on");
$("#dz_series_57").html("12345");
}

/*TAB切换*/
var changetab = function(col,tab,len,i,currclass){
    if(typeof(currclass)=='undefined'){
        currclass = "curr"
    }
    for(var j=1;j<=len;j++){
        $('#'+tab+j).hide();
        $('#'+col+j).removeClass(currclass);
    }
    $('#'+tab+i).show();
    $('#'+col+i).addClass(currclass);
}

//加入购物车效果,从初始地点到目标地点
function MoveBox(origin,target,returnid) {
	var divTop = $(target).offset().top;
	var divLeft = $(target).offset().left;
	$(origin).removeClass('hide');
	$(origin).css({
		"position": "absolute",
		"z-index": "1000",
		"opacity":'1',
		"width": "119px",
		"height":"auto"
	});
	$(origin).animate({
			"left": (divLeft - 100) + "px",
			"top": (divTop - 100) + "px",
			"width": "40px",
			"height": "40px"
		},
		1500,
		function(){
			$(origin).animate({
				"left": (divLeft + 15) + "px",
				"top": divTop + "px",
				"width": "40px",
				"height": "40px"
			},1500).fadeTo(0, 0.1).hide(0);
		}
	);
	if(typeof(returnid) != 'undefined'){
		setTimeout(function(){
			var num=parseInt($(returnid).html())+1;
			$(returnid).html(num);
		},2500);
	}
}

/*加入购物车*/
var addCart = function(onlyid,origin,target){
	if(typeof(onlyid) == 'undefined') return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_cart&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
			MoveBox(origin,target,'#cartnum');
        }else{
            art.dialog.error(data.message);
			if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
	});
}

/*立即购买*/
var quickBuy = function(onlyid){
	if(typeof(onlyid) == 'undefined') return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_order&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
            window.location.href=""+data.url+"";
        }else{
            art.dialog.error(data.message);
			if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
	});
}

/*移出购物车*/
var removeCart = function(onlyid,obj){
	if(typeof(onlyid) == 'undefined') return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=common_cart&remove=1&onlyid="+onlyid+"&callback=?", function(data){
		if(data.status){
			art.dialog.success(data.message);
            $(obj).parent().parent().remove();
        }else{
            art.dialog.error(data.message);
			if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
	});
}

var check_fee = function(){
	$.getJSON(GV.SITE_ROOT+"api.php?a=check_fee&callback=?", function(data){
		if(data.status){
			window.location.href = data.url;
        }else{
            art.dialog.error(data.message);
			if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
	});
}

//生成随机字符串
var randomString = function(len) {
　　len = len || 32;
　　var $chars = 'abcdefhijkmnprstwxyz';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
　　var maxPos = $chars.length;
　　var pwd = '';
　　for (i = 0; i < len; i++) {
　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return pwd;
}

//获取相关资讯
var get_relative_news = function(modelid,id) {
	$.getJSON(GV.SITE_ROOT+'api.php?m=Common&a=get_relative_news&modelid='+modelid+'&id='+id+'&callback=?',function(data){
		var html = '';
		for(one in data){
			if(data[one]['title']){
				html += '<dl class="kt_r_2"><dt><a href="'+data[one]['url']+'" target="_blank">';
				html += '<img src="'+data[one]['thumb']+'" width="99" height="63" title="'+data[one]['title']+'"/></a></dt><dd><p>';
				html += '<a href="'+data[one]['url']+'" target="_blank" title="'+data[one]['title']+'">'+data[one]['title']+'</a>';
				html +=	'</p><font class="fr">'+data[one]['inputtime']+'</font></dd></dl><div class="clear"></div>';
			}
		}
		$('#relative').html(html);
        return false;
    });
}

//获取相关资讯
var get_relative = function(modelid,catid) {
	$.getJSON(GV.SITE_ROOT+'api.php?m=Common&a=get_relative&modelid='+modelid+'&catid='+catid+'&callback=?',function(data){
		var html = '';
		for(one in data){
			if(data[one]['title']){
				html += '<dl class="kt_r_2"><dt><a href="'+data[one]['url']+'" target="_blank">';
				html += '<img src="'+data[one]['thumb']+'" width="99" height="63" title="'+data[one]['title']+'" /></a></dt><dd><p>';
				html += '<a href="'+data[one]['url']+'" target="_blank" title="'+data[one]['title']+'">'+data[one]['title']+'</a>';
				html +=	'</p><font class="fr">'+data[one]['inputtime']+'</font></dd></dl><div class="clear"></div>';
			}
		}
		$('#relative').html(html);
        return false;
    });
}

//获取相关资讯-热点推荐-新版样式
var get_hot_news = function(modelid,catid) {
	$.getJSON(GV.SITE_ROOT+'api.php?m=Common&a=get_relative&modelid='+modelid+'&catid='+catid+'&callback=?',function(data){
		var html = '';
		for(one in data){
			if(data[one]['title']){
				html += '<li><a href="'+data[one]['url']+'" target="_blank"><img src="'+data[one]['thumb']+'"  width="120" height="75" title="'+data[one]['title']+'" class="fl"><span class="fr">'+data[one]['title']+'</span></a></li>';
			}
		}
		$('#hot_news').html(html);
        return false;
    });
}

//获取相关培训
var get_relative_train = function(modelid,id,teacher) {
	$.getJSON(GV.SITE_ROOT+'api.php?m=Common&a=get_relative_train&modelid='+modelid+'&id='+id+'&teacher='+teacher+'&callback=?',function(data){
		var html = '';
		for(one in data){
			if(data[one]['title']){
				html += '<li><a href="'+data[one]['url']+'" title="'+data[one]['title']+'" target="_blank">';
				html += '<img src="'+data[one]['thumb']+'" width="240" height="144" /></a>';
				html += '<span><a href="'+data[one]['url']+'" title="'+data[one]['title']+'" target="_blank">'+data[one]['title']+'</a></span></li>';
			}
		}
		$('#relative').html(html);
        return false;
    });
}

//参加大赛
var join = function (contest_id){
	$.post(GV.SITE_ROOT+'api.php?a=join_contest', {'contest_id':contest_id}, function(data){
        if(data.status) {
            art.dialog.open(GV.SITE_ROOT+"user.php?m=Tuzhi&a=join&contest_id=" + contest_id, {
                id:'join_contest',
                title:"请选择参赛作品",
                width:780,
                lock:true,
                fixed:true,
            });
        } else {
            art.dialog.error(data.message, 5);
            if(data.url)
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',5000);
        }
    },'JSON');
}

//历史作品参赛
var join_contest = function (id) {
	art.dialog.open(GV.SITE_ROOT+'user.php?m=Tuzhi&a=join_contest&id='+id,{
    	lock: true,
   		title: '参加大赛',
      	width: 1000,
        height: 520,
     	opacity: 0.3,
      	drag: false,
	});
}

/*
===================
ajax表单验证并提交
===================
*/
var J_ajax_validator_form = $('form.J_ajax_validator_form');
$(document).ready(function(){
	if(J_ajax_validator_form.length>0){
		Wind.usepath('bootstrap/validator/js/bootstrapValidator.min.js', function(){
			var this_bootstrapValidator = J_ajax_validator_form.bootstrapValidator();
			var this_ajax_submit_btn = J_ajax_validator_form.find('.J_ajax_submit_btn');
			var ajax_submit_callback = this_ajax_submit_btn.data('callback');

			if(this_ajax_submit_btn.length>0){
				this_bootstrapValidator.on('success.form.bv', function(e) {
					e.preventDefault();
					var $form = $(e.target);
					$.post($form.attr('action'), $form.serialize(), function(result) {
						if(result.status == 1){
							if(result.info)
								art.dialog.success(result.info, 2);
							if(result.url){
								if (result.url == -1) {
                                    reloadPage(window);
                                    return false;
                                }
								//跨域问题
								if(result.url.search('i3done.com/js.php')>0){
									setTimeout(function(){
										window.location.href=""+result.url+"";
									},2000);
									return false;
								}

								if(window.parent.art){
									if(result.url == 'self'){
										setTimeout(function(){
											reloadPage(window.parent);
										},2000);
									}else if(result.url == 'closeart'){
										setTimeout(function(){
											art.dialog.close();
										},2000);
									}else if(result.url == 'closeWXWindow'){//微信关闭页面
										setTimeout(function(){
											WeixinJSBridge.invoke('closeWindow',{},function(res){});
										},2000);
									}else{
										setTimeout(function(){
											window.parent.location.href = ""+result.url+"";
										},2000);
									}
								}else{
									if(result.url == 'self'){
										setTimeout(function(){
											window.location.reload();
										},2000);
									}else if(result.url == 'closeWXWindow'){//微信关闭页面
										setTimeout(function(){
											WeixinJSBridge.invoke('closeWindow',{},function(res){});
										},2000);
									}else{
										setTimeout(function(){
											window.location.href=""+result.url+"";
										},2000);
									}
								}
							}else{
								setTimeout(function(){
									if(!ajax_submit_callback)
										$form.find('.J_ajax_submit_btn').removeAttr("disabled");
									else
										submitcallback.call(this,ajax_submit_callback);
								},2000);
							}
						}else if(result.status == 0){
							art.dialog.error(result.info, 2);
							$form.find('.J_ajax_submit_btn').removeAttr("disabled");
							if(result.url == 'closeWXWindow'){//微信关闭页面
								setTimeout(function(){
									WeixinJSBridge.invoke('closeWindow',{},function(res){});
								},2000);
							}
						}
					}, 'json');
				});
			}
		});
	}
});

var submitcallback = function(obj){
	if(obj == 'closewinx'){
		window.opener.location.reload();
		window.close();
	}else if(obj == 'close'){
		window.close();
	}
}

//报名
var signup = function(id) {
	$.getJSON(GV.SITE_ROOT+"api.php?m=Common&a=commonform_check&id="+id+"&callback=?",function(data){
		if(data.status && !islogin){
			art.dialog.error('您尚未登录');
			setTimeout(function(){gotologin();},2000);
		}else{
			art.dialog.open(GV.SITE_ROOT+'api.php?m=Common&a=signup&formid='+id, {
				lock: true,
				title: false,
				width: 520,
				focus: false
			});
		}
	});
}

//挑战擂台
var battle = function(pk_id){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"index.php?g=tuzhi&m=PK&a=battle&pk_id="+pk_id, {
			id:'battle',
			title:false,
	        width:534,
	        lock:true,
	        fixed:true,
	    });
	}
}

//申请
var apply = function(type){
	if(check_login()){
		var url = "";
		if(type == 3)//申请合作商
			url = GV.SITE_ROOT+"index.php?g=Pay&a=dialog_apply";
		else if(type == 2){//申请导师
            $.post(GV.SITE_ROOT+'user.php?a=check_mobile_verify', function(data) {
                if (data.verify == 1) {
                    url = GV.SITE_ROOT+"user.php?m=Home&a=dialog_apply_tutor";
                    apply_open(url);
                } else {
                    art.dialog({
                        id: "question",
                        title:false,
                        width:250,
                        lock:true,
                        fixed:true,
                        content: "申请创客导师，请先验证手机",
                        ok:function(){
                            redirect(GV.SITE_ROOT+"user.php?a=security");
                        },
                        okVal:"前往验证"
                    });
                }
            });
            return false;
        } else//申请创客
			url = GV.SITE_ROOT+"user.php?m=Home&a=dialog_apply";
		if (url) apply_open(url);
	}
}
var apply_open = function (url){
    art.dialog.open(url, {
		id:'shenqing',
		title:false,
		width:680,
		lock:true,
		fixed:true,
	});
}

//提交心愿
var wish = function(){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"index.php?g=Shop&m=Home&a=dialog_wish", {
			id:'wish',
			title:false,
	        width:680,
	        lock:true,
	        fixed:true,
	    });
	}
}

//学生提问
var ask_tutor = function(id){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"user.php?m=Home&a=dialog_ask&id="+id, {
			id:'ask_tutor',
			title:false,
	        width:600,
	        height:600,
	        lock:true,
	        fixed:true,
	    });
	}
}

//上传案例
var upcase = function(){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"user.php?m=Home&a=dialog_case", {
			id:'tutor_case',
			title:false,
	        width:"90%",
			height:"90%",
	        lock:true,
	        fixed:true,
			cancel:false
	    });
	}
}

//上传照片
var upphoto = function(){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"user.php?m=Home&a=dialog_photo", {
			id:'upphoto',
			title:false,
	        width:680,
	        lock:true,
	        fixed:true,
	    });
	}
}

//上传企业资讯
var upcompanynews = function(catid){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"index.php?g=Company&a=dialog_news&catid=" + catid, {
            id:'upcompanynews',
			title:false,
	        width:"90%",
			height:"90%",
	        lock:true,
	        fixed:true,
			cancel:false
        });
	}
}

//推荐校园创客
var recommend = function(userid){
	if(!userid) return false;
	if(check_login()){
		art.dialog.open(GV.SCHOOL_ROOT+"index.php?a=recommend&userid="+userid, {
            id:'recommend',
            title:false,
            width:680,
            lock:true,
            fixed:true
        });
	}
}

//签到(学校主页)
var signin = function(schoolid,returnid){
	if(typeof(schoolid) == 'undefined') return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=signin&schoolid="+schoolid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined')
				$("#"+returnid).attr("src", GV.IMG_ROOT+'school/btn_qiandaoaft.png');
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
			if(data.url){
                setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
            }
        }
	});
}

//签到(创客教育云)
var cloud_signin = function(schoolid,returnid,classname,text){
	if(typeof(schoolid) == 'undefined') return false;
	$.getJSON(GV.SITE_ROOT+"api.php?a=signin&schoolid="+schoolid+"&callback=?", function(data){
		if(data.status){
			if(typeof(returnid) != 'undefined')
				$("#"+returnid).html("已签到");
            if(typeof(classname) != 'undefined')
                $("#"+returnid).addClass(classname);
            if(typeof(text) != 'undefined')
                $("#"+returnid).html(text);
            art.dialog.success(data.message);
        }else{
            art.dialog.error(data.message);
        }
	});
}

//抽奖
var lucky_draw = function(lid){
	if(!islogin){
		art.dialog.error('您尚未登录');
		setTimeout(function(){gotologin();},2000);
	}else{
		art.dialog.open(GV.SITE_ROOT+"api.php?a=lucky_draw&lid="+lid, {
			id:'lucky_draw',
			title:false,
			width:800,
			lock:true,
			fixed:true,
		});
	}
}

/**
 * 弹窗提示
 * @param string content		内容
 */
var popup_message = function(content){
	if(!$.trim(content)) return false;
    art.dialog({
        title: "社区小编告诉你",
        content: content,
        fixed: true,
        width: '300px',
        height: 'auto',
        left: '100%',
        top: '100%',
        zIndex: 999999,
        drag: false,
        esc: false,
        ok: function(){},
		okVal : '关闭'
    });
}

//学校申请入驻
var school_enter = function(){
	art.dialog.open(GV.SITE_ROOT+"api.php?m=Common&a=dialog_school", {
		id:'school_enter',
		title:false,
        width:680,
        lock:true,
    });
}

//拼图小游戏
var jigsaw = function(){
	art.dialog.open(GV.SITE_ROOT+"api.php?m=Common&a=jigsaw", {
		id:'jigsaw',
		title:false,
        width:480,
        lock:true,
        fixed:true,
    });
}

//会员私信
var letter = function(to_userid,type,refresh){
	if(check_login()){
		if(typeof(type) == 'undefined') type = 3;
		art.dialog.open(GV.SITE_ROOT+"user.php?m=Home&a=dialog_letter&to_userid="+to_userid+"&type="+type, {
			id:'letter',
			title:false,
	        width:680,
			height:600,
	        lock:true,
	        fixed:true,
			close:function(){
				if(refresh)
					location.reload();
			}
	    });
	}
}

//查看勋章
var medal_preview = function(userid){
	art.dialog.open(GV.SITE_ROOT+"user.php?m=Home&a=dialog_my_medal&userid="+userid, {
		title:false,
		width:450,
		lock:true,
		fixed:true,
	});
}

//图片使用dialog查看
function image_preview(img, imgWidth, imgHeight) {
    if (img == '') return;
    if (imgWidth == undefined) imgWidth = "100%";
    if (imgHeight == undefined) imgHeight = "100%";
	art.dialog({
		title: false,
		fixed: true,
		width: "800px",
		height: "560px",
		id: "image_preview",
		lock: true,
		background: "#CCCCCC",
		opacity: 0,
		content: '<img src="' + img + '" width="'+imgWidth+'" height="'+imgHeight+'" />',
		time: 10,
		padding: 0,
	});
}

//豌豆下载
var common_download = function(onlyid){
	if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"api.php?a=common_download&onlyid="+onlyid, {
			id:'common_download',
			title:false,
	        width:375,
	        lock:true,
	        fixed:true,
	    });
	}
}

//金币支付
var coinpay = function(order_id){
    if(check_login()){
		art.dialog.open(GV.SITE_ROOT+"index.php?g=Pay&a=coinpay&order_id="+order_id, {
			id:'coinpay',
			title:false,
	        width:375,
	        lock:true,
	        fixed:true,
	    });
	}
}

//精选课程包免费下载
var package_free = function(onlyid){
    if(check_login()){
        $.getJSON(GV.SITE_ROOT+"api.php?a=check_cloud_auth&callback=?", function(data){
            if(data.status){
                if (data.auth) {
                    //调用下载接口
                    $.getJSON(GV.SITE_ROOT+"api.php?a=package_free_download&callback=?", {'onlyid':onlyid}, function(data){
                        if(data.status){
                            window.location.href = data.message.downurl;
                        }else{
                            if (data.message.school_auth == 0) {
                                art.dialog.open(GV.SITE_ROOT+"api.php?a=school_auth", {
                                    id:'school_auth',
                                    title:false,
                                    width:424,
                                    lock:true,
                                    fixed:true,
                               });
                            } else {
                                art.dialog.error(data.message);
                                if(data.url){
                                    setTimeout("window.location.href=\""+data.url+"&forward="+thishref+"\"",1500);
                                }
                            }
                        }
                    });
                } else {
                    art.dialog.open(GV.SITE_ROOT+"api.php?a=school_auth", {
                        id:'school_auth',
                        title:false,
                        width:424,
                        lock:true,
                        fixed:true,
                   });
                }
            }else{
                art.dialog.error(data.message);
                if(data.url){
                    setTimeout('window.location.href="'+data.url+'"',1500);
                }
            }
        });
        return false;
    }
}

//申请授权操作
var auth_operate = function(){
	var flag = 0;
	$.ajaxSettings.async = false;
	$.ajax({
		type : "get",  
		url : GV.SITE_ROOT+"api.php?a=auth_operate",  
		async : false,  
		dataType : 'jsonp',
		success : function(data){  
			if(data.status == 0){
				art.dialog.error(data.message);
				if(data.url){
					setTimeout('window.location.href="'+data.url+'&forward='+thishref+'"',1500);
				}
			}else if(data.status == 2){
				art.dialog.open(GV.SITE_ROOT+"api.php?a=auth_operate", {
					id:'auth_operate',
					title:false,
					width:424,
					lock:true,
					fixed:true,
				});
			}else{
				return flag = 1;
			}
		}  
	});
}

//字数限制
function word_limit(obj, count){
	if(typeof(count) == 'undefined') count = 100;
    var len = $(obj).val().length;
    if(len > count){
        art.dialog.error("请控制在" + count + "字以内");
        $("textarea[name='data[description]']").val(obj.value.substring(0, count));
    }
}

//获取链接中的参数
function get_href_param (href, name) {
    href = href.split('?');
    href = href.join('?');
    href = href.split('&');
    href.shift();
    var query = {};
    for (var i = 0; i < href.length; i += 1) {
        var q = href[i].split('=');
        query[q[0]] = q[1];
    }
    return query[name];
}

//图纸预览
function tuzhi_preview(id) {
	art.dialog.open( GV.SITE_ROOT + "index.php?g=Tuzhi&a=preview&id=" + id, {
		title	: false,
		width	: 1050,
		height	: 526,
		lock	: true,
		fixed	: true,
	});
}

/*
===================
初始化运行区
===================
*/
//通用加载运行
$(document).ready(function(){
	loginarea();	//登录信息

	//懒加载和滚动
	if($('img.lazy').length){
		Wind.usepath("common/jquery.lazyload.min.js",function(){
			$("img.lazy").lazyload({'effect':'fadeIn','threshold':'100','skip_invisible': false});
			$("img.lazy").attr('alt', '');
			$("img.lazy").attr('title', '');
		});
	}

	//搜索推荐
	if($('.J_Suggest_input').length>0){
		Wind.usepath("common/jquery.jSuggest.js",function(){
			var vars = $(".J_Suggest_input").data('vars');
     		$(".J_Suggest_input").jSuggest({
				minchar: 1,
				url: GV.SITE_ROOT+"api.php?m=Common&a=jsuggest",
				type: "GET",
				data: "vars="+vars,
				loadingImg: GV.IMG_ROOT+"aloader.gif",
				autoChange: false,
				delay: 1000,
			});
		});
	}

	//复制功能
	if($("[id=copy-button]").length>0){
		Wind.usepath("common/zeroclipboard.min.js",function(){
			$("[id=copy-button]").each(function(){ 
				var msg = $(this).data('msg');
				if(typeof(msg)=='undefined'){
					msg = "链接复制成功";
				}
				var clip = new ZeroClipboard($(this), {
					moviePath: GV.JS_ROOT+"common/zeroclipboard.swf"
				});
				clip.on('mousedown', function(client) {
					art.dialog.success(msg);
				});
			});
		});
	}

	if($('.icheck').length>0){
		Wind.css(GV.CSS_ROOT+'skins/all.css');
		Wind.usepath("bootstrap/icheck.min.js",function(){
			$('.icheck').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%'
			});
		});
	}

    //图纸列表遮盖层上的点赞
    $('#filter_span .dz').live('click',function(event){
        event.stopPropagation();
        add_likes($(this).data('onlyid'),$(this).attr('id'));
    });
    $('#filter_span a').live('click',function(event){
        event.stopPropagation();
    });
    $('#filter_span').live('click',function(){
        if($(this).data("url")){
            window.open($(this).data("url"));
        }
    });
    
});