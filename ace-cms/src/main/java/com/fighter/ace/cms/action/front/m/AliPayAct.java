package com.fighter.ace.cms.action.front.m;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.cms.Constants;
import com.fighter.ace.cms.alipay.config.AlipayConfig;
import com.fighter.ace.cms.alipay.util.AlipayNotify;
import com.fighter.ace.cms.alipay.util.AlipaySubmit;
import com.fighter.ace.cms.entity.external.*;
import com.fighter.ace.cms.enums.PayType;
import com.fighter.ace.cms.service.external.AddrService;
import com.fighter.ace.cms.service.external.OrdersFacadeService;
import com.fighter.ace.cms.util.CmsUtil;
import com.fighter.ace.framework.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hanebert on 16/7/23.
 */
@Controller
public class AliPayAct {

    private static final Logger log = LoggerFactory.getLogger(AliPayAct.class);

    @Autowired
    private OrdersFacadeService ordersFacadeService;
    @Resource
    private AddrService addrService;

    /**
     * 虚拟币充值
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/m/buyCoin",method = RequestMethod.POST)
    public String aliPaying(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        //商户订单号，商户网站订单系统中唯一订单号，必填
        try {
            Member memberVo = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
            String out_trade_no = CmsUtil.getTradeNo(memberVo.getId());
            //付款金额，必填
            String total_fee = request.getParameter("WIDtotal_fee");
            CoinItem coinItem = getItem(total_fee);

            Orders orders = new Orders();
            orders.setMemberId(memberVo.getId());
            orders.setOrderNo(out_trade_no);
            orders.setPayType(PayType.AliPay.getValue());
            orders.setTotalFee(total_fee);
            orders.setStatus(0);
            Long orderId = ordersFacadeService.saveOrders(orders);
            log.info("memberId:"+memberVo.getId()+",buy item:"+coinItem.getName()+",OrderId:"+orderId);
            //订单名称，必填
            String subject = coinItem.getName();//request.getParameter("WIDsubject");
            //商品描述，可空
            String body = coinItem.getDesc();//request.getParameter("WIDbody");
            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfig.service);
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("seller_id", AlipayConfig.seller_id);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("payment_type", AlipayConfig.payment_type);
            sParaTemp.put("notify_url", AlipayConfig.notify_url);
            sParaTemp.put("return_url", AlipayConfig.return_url);
            sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", subject);
            sParaTemp.put("total_fee", total_fee);
            sParaTemp.put("body", body);
            //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
            //如sParaTemp.put("参数名","参数值");

            //建立请求
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
            //PrintWriter out = response.getWriter();
            //out.println(sHtmlText);
            //out.flush();
            modelMap.addAttribute("htmlText",sHtmlText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "m/aliPay";
    }

    /**
     * 购物车
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/m/aliPayForCar",method = RequestMethod.POST)
    public String aliPayForCar(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        //商户订单号，商户网站订单系统中唯一订单号，必填
        try {
            Member memberVo = (Member) request.getSession().getAttribute(Constants.MEMBER_SESSION_KEY);
            String out_trade_no = CmsUtil.getTradeNo(memberVo.getId());
            //付款金额，必填
            String total_fee = request.getParameter("totalFee");
            //CoinItem coinItem = getItem(total_fee);

            Orders orders = new Orders();
            orders.setMemberId(memberVo.getId());
            orders.setOrderNo(out_trade_no);
            orders.setPayType(PayType.AliPay.getValue());
            orders.setTotalFee(total_fee);
            orders.setStatus(0);
            //网站商品订单
            orders.setExt1("3D");

            List<Mall> cartList = new ArrayList<>();
            //Double totalFee = 0.00;
            Object cartObj = request.getSession().getAttribute(Constants.MEMBER_CART);
            if (null != cartObj){
                Map<Long,Mall> mallMap = (Map<Long, Mall>) cartObj;
                cartList.addAll(mallMap.values());
                String items = JSON.toJSONString(cartList);
                orders.setExt2(items);
            }

            String addrId = RequestUtils.getQueryParam(request, "addrId");
            Addr addr = addrService.getById(Long.valueOf(addrId));
            String remark = RequestUtils.getQueryParam(request, "remark");
            JSONObject ext3 = JSON.parseObject(JSON.toJSONString(addr));
            ext3.put("remark",remark);
            orders.setExt3(ext3.toJSONString());

            Long orderId = ordersFacadeService.saveOrders(orders);

            //订单名称，必填
            String subject = "增材智造3D商城-3D耗材产品";//request.getParameter("WIDsubject");
            //商品描述，可空
            String body = subject; //coinItem.getDesc();//request.getParameter("WIDbody");
            log.info("memberId:"+memberVo.getId()+",buy item:"+subject+",OrderId:"+orderId);
            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfig.service);
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("seller_id", AlipayConfig.seller_id);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("payment_type", AlipayConfig.payment_type);
            sParaTemp.put("notify_url", AlipayConfig.notify_url);
            sParaTemp.put("return_url", AlipayConfig.return_url);
            sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", subject);
            sParaTemp.put("total_fee", total_fee);
            //sParaTemp.put("total_fee", "0.01");
            sParaTemp.put("body", body);
            //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
            //如sParaTemp.put("参数名","参数值");

            //建立请求
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
            //PrintWriter out = response.getWriter();
            //out.println(sHtmlText);
            //out.flush();
            modelMap.addAttribute("htmlText",sHtmlText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "m/aliPay";
    }





    /**
     * 支付宝 nofity
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping(value = "alipay/notify",method = RequestMethod.POST)
    public void notify(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //商户订单号
        String out_trade_no = request.getParameter("out_trade_no");
        //支付宝交易号
        String trade_no = request.getParameter("trade_no");
        //交易状态
        String trade_status = request.getParameter("trade_status");
        try {
            PrintWriter out = response.getWriter();
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            if(AlipayNotify.verify(params)){//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码
                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if(trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    Orders ordersVo = ordersFacadeService.getByOrderNo(out_trade_no);
                    int k = 0;
                    if (ordersVo.getExt1().equals("3D") && ordersVo.getStatus()!=null && ordersVo.getStatus() == 0){
                        k = ordersFacadeService.updateOnlyTradeNo(ordersVo.getId(),trade_no);
                        request.getSession().removeAttribute(Constants.MEMBER_CART);
                    } else if(!ordersVo.getExt1().equals("3D") && ordersVo.getStatus()!=null && ordersVo.getStatus() == 0){
                        k = ordersFacadeService.updateTradeNo(ordersVo.getId(),trade_no);
                    }
                    if (k > 0){
                        log.info("OrderNo:"+out_trade_no+",has been finished.");
                        out.print("success");	//请不要修改或删除
                    }else{
                        out.print("fail");
                    }
                }
            }else{//验证失败
                out.print("fail");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "alipay/returnUrl",method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        try {
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //PrintWriter out = response.getWriter();
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            if(AlipayNotify.verify(params)){//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码
                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if(trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    Orders ordersVo = ordersFacadeService.getByOrderNo(out_trade_no);
                    if(ordersVo.getStatus()!=null && ordersVo.getStatus() == 0){
                        int k = ordersFacadeService.updateTradeNo(ordersVo.getId(),trade_no);
                        if (k > 0){
                            //out.print("success");//请不要修改或删除
                            log.info("OrderNo:"+out_trade_no+",has been processed successfully.");
                        }else{
                            //out.print("fail");
                            log.info("OrderNo:"+out_trade_no+",has been processed failed.");
                        }
                    }else {
                        log.info("OrderNo:" + out_trade_no + ",has been finished.");
                        //out.print("success");
                        log.info("OrderNo:"+out_trade_no+",has been processed successfully.");
                    }

                }
            }else{//验证失败
                //out.print("fail");
                log.info("OrderNo:"+out_trade_no+",has been processed failed.");
            }
            //out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:../m/index";
    }


    private CoinItem getItem(String price){
        Integer rmb = Integer.valueOf(price);
        Integer coins = rmb * 20;
        String itemName = "增材智造金币充值"+price+"元";
        CoinItem coinItem = new CoinItem(price,coins,itemName,itemName);
        return coinItem;
    }


}
