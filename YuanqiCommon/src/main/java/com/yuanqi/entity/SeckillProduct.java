package com.yuanqi.entity;

import java.util.Date;

public class SeckillProduct {
    private int id;
    private int productId;
    private int seckillNum;
    private double seckillPrice;
    private int seckillInventory;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Date approveTime;

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    private int shopId;
    private int state;  //0申请中，1审核通过，2不通过，3上架，4下架
    private double productPrice;

    private String productTitle;
    private String productName;

    private int seckillversion;

    public int getSeckillversion() {
        return seckillversion;
    }

    public void setSeckillversion(int seckillversion) {
        this.seckillversion = seckillversion;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSeckillNum() {
        return seckillNum;
    }

    public void setSeckillNum(int seckillNum) {
        this.seckillNum = seckillNum;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public int getSeckillInventory() {
        return seckillInventory;
    }

    public void setSeckillInventory(int seckillInventory) {
        this.seckillInventory = seckillInventory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
