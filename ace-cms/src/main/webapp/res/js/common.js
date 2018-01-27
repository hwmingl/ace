//购买数量的加减
function quantity_onclick(type) {
    var quantity = Number($("#quantity").val());
    var limit_num = Number($("#limit_num").html());
    var num = 0;
    if (type == 0) {
        num = (quantity < 2) ? 1 : (quantity - 1);
    } else {
        num = (quantity >= limit_num) ? limit_num : (quantity + 1);
    }
    $("#quantity").val(num);
}

//通用点赞
function common_add_likes(ob) {
    
    var id = $(ob).attr("id");
    $(ob).addClass("zan_on");
    $(ob).children("font").html( "123456" );
   
}