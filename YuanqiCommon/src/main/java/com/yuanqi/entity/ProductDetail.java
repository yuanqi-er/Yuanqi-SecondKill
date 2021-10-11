package com.yuanqi.entity;

public class ProductDetail {
    private int id;
    private int productId;
    private String productPlace;
    private String productBrand;
    private String productDescription;
    private String productWeight;
    private String productDetailPictureUrl;
    private String SpecificationAndPack;        //规格和包装

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

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductDetailPictureUrl() {
        return productDetailPictureUrl;
    }

    public void setProductDetailPictureUrl(String productDetailPictureUrl) {
        this.productDetailPictureUrl = productDetailPictureUrl;
    }

    public String getSpecificationAndPack() {
        return SpecificationAndPack;
    }

    public void setSpecificationAndPack(String specificationAndPack) {
        SpecificationAndPack = specificationAndPack;
    }
}
