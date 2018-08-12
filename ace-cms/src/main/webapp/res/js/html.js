function top1Html() {
    var htmlCode = "";
    htmlCode = htmlCode + "<div class=\"a_wd clearfix\">";
    htmlCode = htmlCode + "<div class=\"login fl\"><div class=\"login top_nav\" style=\"line-height: 35px;\">";
    htmlCode = htmlCode + " <span><a href=\"/jz3d/register.html\" target=\"_blank\">注册</a><a href=\"/jz3d/login.html\" target=\"_blank\">登陆</a></span></div></div>";
    htmlCode = htmlCode + "<ul class=\"que_nav fr\">";
    htmlCode = htmlCode + "<li><a href=\"#\">晋中智造网 | 增材制造（3D打印）公共服务-晋中分平台</a></li>";
    htmlCode = htmlCode + " <li class=\"site_nav\"><span>网站导航</span><div class=\"site_nav_list clearfix\">";
    htmlCode = htmlCode + "<dl class=\"nav_wd_1\"><dt>模型库</dt><dd><a href=\"3dModel.htm\">最新模型</a></dd><dd><a href=\"3dModel.htm\">热门模型</a></dd>";
    htmlCode = htmlCode + " <dd><a href=\"3dModel.htm\">全部模型</a></dd><dt>3D商城</dt><dd><a href=\"3dMall.htm\">3D商城</a></dd></dl>";

    htmlCode = htmlCode + " <dl class=\"nav_wd_2 clearfix\"><dt>3D学院</dt>";
    htmlCode = htmlCode + "<dd><a href=\"3dStudyList.htm\">视频课程</a></dd><dd><a href=\"3dStudyList.htm\">文字课程</a></dd><dd><a href=\"3dStudyList.htm\">软件下载</a></dd>";
    htmlCode = htmlCode + "<dt>3D资讯</dt><dd><a href=\"3dNewsList.htm\">今日看点</a></dd><dd><a href=\"3dNewsList.htm\">设计前沿</a></dd> ";
    htmlCode = htmlCode + "  <dd><a href=\"3dNewsList.htm\">行业动态</a></dd><dd><a href=\"3dNewsList.htm\">活动公告</a></dd><dd><a href=\"3dNewsList.htm\">策划专题</a></dd></dl>";

    htmlCode = htmlCode + "<dl class=\"nav_wd_3\"><dt>设计师</dt><dd><a href=\"3dDesigner.htm\">设计师</a></dd></dl>";
    htmlCode = htmlCode + " <dl class=\"nav_wd_4\"><dt>联系我们</dt><dd><a href=\"3dAbout.htm#1\">公司简介</a></dd><dd><a href=\"3dAbout.htm#2\">版权声明</a></dd>";
    htmlCode = htmlCode + "<dd> <a href=\"3dAbout.htm#6\">联系方式</a></dd><dd><a href=\"3dAbout.htm#8\">审核标准</a></dd><dd><a href=\"3dAbout.htm#9\">常见问题</a></dd><dd></dl>";
    htmlCode = htmlCode + "</div></li></ul></div>";
    return htmlCode;

}

function top2Html() {
    var htmlCode = "";
    htmlCode = htmlCode + "<div class=\"logo fl\"><h1 id=\"logo\"><a href=\"#\" title=\"3DMaking智造网\"><img src=\"/jz3d/res/images/logo.jpg\" alt=\"3DMaking智造网\" /></a></h1></div>";
    htmlCode = htmlCode + "<div class=\"fr top_weixin_box center\"><p><img src=\"/jz3d/res/images/ewm86.jpg\" alt=\"\" /></p></div>";
    htmlCode = htmlCode + "  <table class=\" fr\"><tr><td><div class=\"fr search\">";
    htmlCode = htmlCode + "<input type=\"text\" x-webkit-speech=\"\" class=\"text\" name=\"q\" id=\"key\" placeholder=\"搜搜自己喜欢的\">";
    htmlCode = htmlCode + " <input type=\"button\" class=\"button\" id=\"Button1\"></div></td>";
    htmlCode = htmlCode + " <td style=\"padding-top: 15px;\"><a title=\"上传模型\" href=\"#\" target=\"_blank\" style=\"border: #ff6600 1px solid; padding: .5em 1em;padding-left: 2em; color: #ff6600; background: url(images/arrow_up.jpg) no-repeat 5px;\">";
    htmlCode = htmlCode + "上传模型</a></td></tr></table>";
    return htmlCode;
}

function footHtml() {
    var htmlCode="";
    htmlCode = htmlCode + "<div class=\"a_wd clearfix\">";
    htmlCode =htmlCode+"<div id=\"copyright\">";
    htmlCode =htmlCode+"<dl><dt><img src=\"/jz3d/res/images/logo.jpg\" alt=\"\"></dt>";
    htmlCode =htmlCode+"<dd>核心运营：某某某某科技有限公司<br>©版权所有3DMAKING网,All Rights Reserved<br>ICP证：苏ICP备 15053742号7";
    htmlCode=htmlCode+"<br>网站备案：<a href=\"http://www.miitbeian.gov.cn\" target=\"_blank\">苏ICP备 15053742号</a>";      
    htmlCode =htmlCode+" <a href=\"#\" target=\"_blank\"><img src=\"/jz3d/res/images/foot_15.jpg\" alt=\"\"/></a> <a href target=\"_blank\"><img src=\"images/foot_21.jpg\" alt=\"\" /></a>";
    htmlCode = htmlCode + "</dd></dl> </div>";


    htmlCode = htmlCode + " <div class=\"footer_nav\" style=\"width: 810px;\">";
    htmlCode = htmlCode + "<dl id=\"site_server\"><dt>模型相关</dt><dd><a href=\"3dAbout.htm#1\" target=\"_blank\">如何上传模型</a></dd>";
    htmlCode = htmlCode + "<dd><a href=\"3dAbout.htm#1\" target=\"_blank\">如何下载模型</a></dd><dd><a href=\"3dStudyList.htm\" target=\"_blank\">3D模型设计软件</a></dd> ";
    htmlCode = htmlCode + " <dd><a href=\"3dAbout.htm#1\" target=\"_blank\">广告服务</a></dd></dl>";

    htmlCode = htmlCode + "<dl id=\"aboutus\"><dt>网站服务</dt>";
    htmlCode = htmlCode + " <dd><a href=\"3dAbout.htm#1\" target=\"_blank\">联系我们</a></dd>";
    htmlCode = htmlCode + " <dd><a href=\"3dAbout.htm#1\" target=\"_blank\">招纳贤士</a></dd></dl>";

    htmlCode = htmlCode + "<dl class=\"no_border\" id=\"gz_weixin\"><dt>关注我们</dt><dd>";
    htmlCode = htmlCode + "<ul class=\"clearfix up_2\">";
    htmlCode = htmlCode + "<li><a href=\"#\" target=\"_blank\"><span class=\"guanzhu_1\"></span></a><a href=\"#\" target=\"_blank\"><span class=\"guanzhu_2\"></span></a><div class=\"up_3\">029-88888888</div></li>";
    htmlCode = htmlCode + "<li class=\"center footer_weix\"><p><img src=\"/jz3d/res/images/ewm86.jpg\" width=\"86\" height=\"86\" alt=\"\" /></p>3DMAKING网</li>";
    htmlCode = htmlCode + "<li class=\"center footer_weix\"><p><img src=\"/jz3d/res/images/ewm86.jpg\" width=\"86\" height=\"86\" alt=\"\" /></p>3DMAKING网</li>";
    htmlCode = htmlCode + "<li class=\"center footer_weix\"><p><img src=\"/jz3d/res/images/ewm86.jpg\" width=\"86\" height=\"86\" alt=\"\" /></p>3DMAKING网</li></ul></dd></dl>";
    htmlCode = htmlCode + "</div></div>";  
              
    return htmlCode ;

}

function side_bar() {
    var htmlCode = "";
    htmlCode = htmlCode + " <a class=\"icon_erwei\"><i><img src=\"images/ewm86.jpg\" alt=\"\"></i></a> ";
    htmlCode = htmlCode + "<a href=\"http://wpa.b.qq.com/cgi/wpa.php?ln=2&amp;uin=36862742\" class=\"icon_qq\" target=\"_blank\"></a>";
    htmlCode = htmlCode + " <a href=\"3dAbout.htm#10\" target=\"_blank\" class=\"icon_chat\"></a>";
    htmlCode = htmlCode + " <a href=\"#\" class=\"cd-top\"></a>";
    return htmlCode;
}

function linkCom_Html() {
    var htmlCode = "";
    htmlCode = htmlCode + "<div class=\"a_wd clearfix\" style=\"margin-top: 10px;\"> <div class=\"siteFont\"><span style=\" font-weight:bold; color:#333;\">友情链接：</span>";

    htmlCode = htmlCode + " <a href=\"#\" title=\"3D智造网\" class=\"xin_c66 xin_bianse\">3D智造网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印材料批发网\" class=\"xin_c66 xin_bianse\">3D打印材料批发网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国际3D技术网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"中国科技学院\" class=\"xin_c66 xin_bianse\">中国科技学院</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国际3D技术网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印网\" class=\"xin_c66 xin_bianse\">3D打印网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D开发信息中心\" class=\"xin_c66 xin_bianse\">3D开发信息中心</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D智造网\" class=\"xin_c66 xin_bianse\">3D智造网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印材料批发网\" class=\"xin_c66 xin_bianse\">3D打印材料批发网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国技术网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"中国科技学院\" class=\"xin_c66 xin_bianse\">中国科技学院</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国际3D技术网</a>&nbsp;&nbsp;<br>";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印网\" class=\"xin_c66 xin_bianse\">3D打印网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D开发信息中心\" class=\"xin_c66 xin_bianse\">3D开发信息中心</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D智造网\" class=\"xin_c66 xin_bianse\">3D智造网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印材料批发网\" class=\"xin_c66 xin_bianse\">3D打印材料批发网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国际3D技术网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"中国科技学院\" class=\"xin_c66 xin_bianse\">中国科技学院</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"国际3D技术网\" class=\"xin_c66 xin_bianse\">国际3D技术网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D打印网\" class=\"xin_c66 xin_bianse\">3D打印网</a>&nbsp;&nbsp;";
    htmlCode = htmlCode + " <a href=\"#\" title=\"3D开发信息中心\" class=\"xin_c66 xin_bianse\">3D开发信息中心</a>&nbsp;&nbsp;";

    htmlCode = htmlCode + "</div></div>";
    return htmlCode;

}