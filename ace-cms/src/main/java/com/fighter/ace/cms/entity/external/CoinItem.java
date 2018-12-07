package com.fighter.ace.cms.entity.external;

/**
 * Created by hanebert on 16/7/23.
 * 支付宝充值-商品
 */
public class CoinItem implements java.io.Serializable {

    private String price;
    private Integer coins;
    private String name;
    private String desc;

    public CoinItem() {
    }

    public CoinItem(String price, Integer coins, String name, String desc) {
        this.price = price;
        this.coins = coins;
        this.name = name;
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }
}
