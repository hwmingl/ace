$(function(){
  $('#channeltwo_left_02 .channeltwo_left_02a:first-child').addClass('active');
  $('.channeltwo_left_02a:lt(3)').find('h5 span').addClass('ico');
  $('#channeltwo_left_02').find('.channeltwo_left_02a').mousemove(function(){
	$('#channeltwo_left_02').find('.channeltwo_left_02a').removeClass('active');
	$(this).addClass('active');
  });
});