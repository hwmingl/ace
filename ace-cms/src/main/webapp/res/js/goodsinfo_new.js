//关闭加入购物车 弹出框
function slideUp_fn(){
    $('.ware_cen').slideUp('slow');
}
//关闭加入最爱 弹出框
function slideUp_fn1(){
    $('.ware_cen1').slideUp('slow');
}
/* order */
function sortArr(m,n){
	return m>n?1:(m<n?-1:0);
}
/* check */
function check_arr(goods_id,info,size){
	var specs = goodsspec.spec_id;
	var state = 0;
	for(var key in specs){
		if(goods_id==key){
			state = 1;
			if(size=="s"){
				if(specs[key].size!=info){
					if(specs[key].size!=""){
						if(!goodsspec.bisc(goods_id,specs[key].color,1)){
							specs[key].color = "";
						}
					}
					specs[key].size = info;
				}
			}
			if(size=="c"){
				if(specs[key].color!=info){
					if(specs[key].color!=""){
						if(!goodsspec.bisc(goods_id,specs[key].size,2)){
							specs[key].size = "";
						}
					}
					specs[key].color = info;
				}
			}
		}
	}
	if(state==0){
		var obj = new Object();
		if(size=="s"){
			obj.size = info;
			obj.color = "";
		}
		if(size=="c"){
			obj.color = info;
			obj.size = "";
		}
		specs[goods_id] = obj;
	}
	goodsspec.spec_id = specs;
}
/* get spec_id */
function get_spec_id(goods_id,size,color){
	var specs = goodsspec.specs[goods_id];
	var spec_id = 0;
	for(var i=0;i<specs.length;i++){
		if(specs[i][2]==size && specs[i][1]==color){
			spec_id = specs[i][0];
		}
	}
	return spec_id;
}
/* get price stock */
function set_price_stock(goods_id){
	var gspec = goodsspec.specs[goods_id];
	var specid = goodsspec.spec_id[goods_id];
	var zhekou = Number($("#zhekou").val());
	var jianmoney = Number($("#jianmoney").val());
	for (var i = 0; i < gspec.length; i++){
		var color = gspec[i][1];
		var size = gspec[i][2];	
		if(color==specid.color && size==specid.size){
			$("#price_"+goods_id).html("¥"+gspec[i][3].toFixed(2));
			$("#limit_num").html(gspec[i][4]);
			var money = Number(gspec[i][3]);
			if(zhekou>0 || jianmoney>0){
				/*if(zhekou>0){
					var my = money*zhekou;
				}
				if(jianmoney>0){
					var my = money-jianmoney;
				}
				$("#formerly_money").html("￥"+String(money.toFixed(2)));
				$("#anon_money").html("￥"+String(my.toFixed(2)));*/
			}else{
				$("#general_money").html("￥"+String(money.toFixed(2)));
			}
		}
	}
}
function goodsspec(specs, specQty, defSpec, goods) {
	this.specs = specs;
	this.specQty = specQty;
	this.goods = goods; //组合商品ID
	this.type = "";
	this.spec_id = new Array();
	this.stocknum = 0;
	this.stockdata = new Array();
	/**
	* 比对1是颜色2是尺寸
	*/
	this.bisc = function(goods_id,info,type){
		var being = false;
		var specValues = this.getDistinctValues(goods_id,type,'');
		for (var i = 0; i < specValues.length; i++){
			if(specValues[i]==info){
				being = true;
			}
		}
		return being;
	}
	this.init = function() {
		this.stocknum = $("#limit_num").html();
		var duigou_img = $("#duigou_img").val();
		for(var o=0;o<this.goods.length;o++){
			var goods_id = this.goods[o];
			if(this.specQty[goods_id] == 0){
				var obj = new Object();
				obj.size = "null";
				obj.color = "null";
				this.spec_id[goods_id] = obj;
			}
			if(this.specQty[goods_id] >= 1){
				var spec1Values = this.getDistinctValues(goods_id,2,'');
				for(var i = 0; i < spec1Values.length; i++){
					$("#goods_size_"+goods_id).eq(0).append('<li onclick="goods_size_check(this,'+goods_id+')" title="'+spec1Values[i]+'" id="1">'+spec1Values[i]+'<span style="display:none;"><img src="'+duigou_img+'" /></span></li>');
				}
			}
			if(this.specQty[goods_id] >= 2){
				var spec2Values = this.getDistinctValues(goods_id,1,'');
				for (var i = 0; i < spec2Values.length; i++){
					$("#goods_color_"+goods_id).eq(0).append('<li onclick="goods_color_check(this,'+goods_id+');"  title="'+spec2Values[i]+'" id="1">'+spec2Values[i]+'<span style="display:none;"><img src="'+duigou_img+'" /></span></li>');
				}
			}
			size_over(goods_id);
			color_over(goods_id);
		}
	}
	
	this.getSpec = function(){
		var spec = "";
		var snum = 0;
		for(var key in this.spec_id){
			if(this.spec_id[key].size!="" && this.spec_id[key].color!=""){
				if(this.spec_id[key].size=="null" && this.spec_id[key].color=="null"){
					var spec_id = this.specs[key][0][0];
				}else{
					var spec_id = get_spec_id(key,this.spec_id[key].size,this.spec_id[key].color);
				}
				spec += (spec=="")?spec_id:","+spec_id;
				snum++;
			}
		}
		if(snum==goods.length){
			return spec;
		}else{
			return null;
		}
		return snum;
	}
    
	this.getDistinctValues = function(id,field,spec){
		var gspec = this.specs[id];
        var values = new Array();
		var val = new Array();
		for (var i = 0; i < gspec.length; i++){
			var value = gspec[i][field];
			var number = gspec[i][4];
			if(goodsspec.type == "s"){
				if (spec != '' && spec != gspec[i][2]) continue;
			}
			if(goodsspec.type == "c"){
				if (spec != '' && spec != gspec[i][1]) continue;
			}
            if($.inArray(value, values)<0){
				val.push(number);
                values.push(value);
            }
        }
		this.stockdata = val;
        return values;
    }
}
/*恢复选择框*/
function resume_size_color(obj,goods_id,type){
	var specs = goodsspec.spec_id;
	if(type=="color"){
		specs[goods_id].size="";
	}else{
		specs[goods_id].color="";
	}
	goodsspec.spec_id = specs;
	$(obj).css({"border":"1px solid #c8c9ce","padding":"1px 10px","color":"#666666","background-color":"#FFFFFF"});
	$(obj).children("span").hide();
	$(obj).removeClass("checked");
	$(obj).attr("id","1");
	$.each($("#goods_"+type+"_"+goods_id+" li"),function(n,m){
		if($(this).attr("id")=="0"){
			$(this).css({"border":"1px solid #c8c9ce","padding":"1px 10px","color":"#666666","background-color":"#FFFFFF"});
			$(this).children("span").hide();
			$(this).removeClass("checked");
			$(this).attr("id","1");
		}
	});
	$("#limit_num").html(goodsspec.stocknum);
	color_over(goods_id);
}
/*恢复为正常可选择状态事件*/
function resume_window(o){
	$(o).css({"border":"1px solid #c8c9ce","padding":"1px 10px"});
	$(o).children("span").hide();
	$(o).removeClass("checked");
	$(o).attr("id","1");
}
/*将框变为不可选中状态*/
function no_action(o){
	$(o).css({"border":"1px solid #c8c9ce","padding":"1px 10px","color":"#c8c9ce","background-color":"#ececec"});
	$(o).children("span").hide();
	$(o).removeClass("checked");
	$(o).attr("id","0");
}
/*将框变为选中状态*/
function yse_action(o){
	$(o).css({"border":"2px solid #77b500","padding":"0px 9px","color":"#666666","background-color":"#FFFFFF"});
	$(o).children("span").show();
	$(o).attr("class","checked");
	$(o).attr("id","2");
}
/*尺码 触发事件*/
function goods_size_check(obj,goods_id){
	if($(obj).attr("id")=="0"){
		return;
	}else if($(obj).attr("id")=="2"){
		resume_size_color(obj,goods_id,"color");
		return;
	}
	//勾图片地址
	var duigou_img = $("#duigou_img").val();
	//选中的尺寸
	var size = $(obj).attr("title");
	//恢复其它的效果
	$.each($("#goods_size_"+goods_id+" li"),function(n,m){
		if($(this).attr("id")!="0"){
			resume_window(this);
		}
	});
	yse_action(obj);
	//类型尺寸简称"s"
	goodsspec.type = "s";
	check_arr(goods_id,size,"s");

	var specs = goodsspec.spec_id;
	var Values = goodsspec.getDistinctValues(goods_id,1,size);
	var Stock = goodsspec.stockdata;
	$.each($("#goods_color_"+goods_id+" li"),function(n,m){
		no_action(this);
	});
	for(var i = 0; i < Values.length; i++){
		if(Stock[i]!="0"){
			if(specs[goods_id].color==Values[i]){
				$.each($("#goods_color_"+goods_id+" li"),function(n,m){
					if($(this).attr("title")==Values[i]){
						yse_action(this);
					}
				});
			}else{
				$.each($("#goods_color_"+goods_id+" li"),function(n,m){
					if($(this).attr("title")==Values[i]){
						$(this).css({"color":"#666666","background-color":"#FFFFFF"});
						$(this).attr("id","1");
					}
				});	
			}
		}
	}
	color_over(goods_id);
	/*颜色和尺寸都存在然后更新价格和数量*/
	if(specs[goods_id].color!="" && specs[goods_id].size!="")
		set_price_stock(goods_id);
}

/*颜色分类 触发事件*/
function goods_color_check(obj,goods_id){
	if($(obj).attr("id")=="0"){
		return;
	}else if($(obj).attr("id")=="2"){
		resume_size_color(obj,goods_id,"size");
		return;
	}
	//勾图片地址
	var duigou_img = $("#duigou_img").val();
	//选中的颜色
	var color = $(obj).attr("title");
	//恢复其它的效果
	$.each($("#goods_color_"+goods_id+" li"),function(n,m){
		if($(this).attr("id")!="0"){
			resume_window(this);
		}
	});
	yse_action(obj);
	//类型颜色简称"c"
	goodsspec.type = "c";
	//核实比对
	check_arr(goods_id,color,"c");

	var specs = goodsspec.spec_id;
	var Values = goodsspec.getDistinctValues(goods_id,2,color);
	var Stock = goodsspec.stockdata;
	$.each($("#goods_size_"+goods_id+" li"),function(n,m){
		no_action(this);
	});
	for (var i = 0; i < Values.length; i++){
		if(Stock[i]!="0"){
			if(specs[goods_id].size==Values[i]){
				$.each($("#goods_size_"+goods_id+" li"),function(n,m){
					if($(this).attr("title")==Values[i]){
						yse_action(this);
					}
				});
			}else{
				$.each($("#goods_size_"+goods_id+" li"),function(n,m){
					if($(this).attr("title")==Values[i]){
						$(this).css({"color":"#666666","background-color":"#FFFFFF"});
						$(this).attr("id","1");
					}
				});	
			}
		}
	}
	size_over(goods_id);
	/*颜色和尺寸都存在然后更新价格和数量*/
	if(specs[goods_id].color!="" && specs[goods_id].size!="")
		set_price_stock(goods_id);
}
function size_over(){
	$("#goods_size_"+goods_id+" li").not($(".checked")).hover(function(){
		if($(this).attr("id")=="1"){
			$("#goods_size_"+goods_id+" li").not($(".checked")).css({"border":"1px solid #c8c9ce","padding":"1px 10px"});
			$(this).not($(".checked")).css({"border":"2px solid #77b500","padding":"0 9px"});
		}
	},function(){
		if($(this).attr("id")=="1"){
			$("#goods_size_"+goods_id+" li").not($(".checked")).css({"border":"1px solid #c8c9ce","padding":"1px 10px"});
		}
	});
}
function color_over(goods_id){
	$("#goods_color_"+goods_id+" li").not($(".checked")).hover(function(){
		if($(this).attr("id")=="1"){
			$("#goods_color_"+goods_id+" li").not($(".checked")).css({"border":"1px solid #c8c9ce","padding":"1px 10px"});
			$(this).not($(".checked")).css({"border":"2px solid #77b500","padding":"0 9px"});
		}
	},function(){
		if($(this).attr("id")=="1"){
			$("#goods_color_"+goods_id+" li").not($(".checked")).css({"border":"1px solid #c8c9ce","padding":"1px 10px"});
		}
	})
}
$(function(){
	goodsspec.init();
});