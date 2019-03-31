package com.android.list.sort;

import android.support.annotation.NonNull;

/**
 * 货道商品数量
 * Created by Niu on 2015/9/27.
 */
public class ChannelGoodIdCount implements Comparable<ChannelGoodIdCount> {

    private String channel;       // VARCHAR(500), " +							//商品所在货道
    private String goods_id;       // VARCHAR(500), " +							//商品ID
    private String attr_id;       // VARCHAR(500), " +							//商品属性id
    private int good_count = 1;    // VARCHAR(500), " +						//商品在该货道的库存
    private String suppliers_id;   //供应商Id
    private String lastmodify;    //
    private String attr_bar;

    public ChannelGoodIdCount() {
    }

    public ChannelGoodIdCount(String channel, String goods_id, String attr_id, int good_count, String lastmodify) {
        this.channel = channel;
        this.goods_id = goods_id;
        this.attr_id = attr_id;
        this.good_count = good_count;
        this.lastmodify = lastmodify;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public String getLastmodify() {
        return lastmodify;
    }

    public void setLastmodify(String lastmodify) {
        this.lastmodify = lastmodify;
    }

    public String getSuppliers_id() {
        return suppliers_id;
    }

    public void setSuppliers_id(String suppliers_id) {
        this.suppliers_id = suppliers_id;
    }

    public String getAttr_bar() {
        return attr_bar;
    }

    public void setAttr_bar(String attr_bar) {
        this.attr_bar = attr_bar;
    }

    /***
     * @param another the object to compare to this instance.
     * @return a negative (消极的，否认的; [数] 负的)integer if this instance is less than {@code another};
     *         a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(@NonNull ChannelGoodIdCount another) {
        return Integer.valueOf(this.channel) - Integer.valueOf(another.getChannel());
    }

}
