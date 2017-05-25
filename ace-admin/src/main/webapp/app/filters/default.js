/**
 * Created by xiangnan.nie on 2015/7/20.
 */
copyright.filter("liceseeType",function(){
   return function(type){
      return license_type[type];
   }
});

copyright.filter("songLevel",function(){
   return function(type){
      return SONG_LEVEL[type];
   }
});

copyright.filter("percent",function(){
   return function(value){
      return value*100+"%";
   }
});