package com.orderista.models;

import com.google.gson.annotations.SerializedName;

public class OrderModel {

    @SerializedName("id")
    private String id;

    @SerializedName("order_date")
    private String order_Date;

    @SerializedName("products")
    private String products;

    @SerializedName("total_amount")
    private String total_amount;

    public String getId(){
        return id;
    }
    public String getOrder_Date(){
        return order_Date;
    }
    public String getProducts(){
        return products;
    }
    public String getTotal_amount(){
        return total_amount;
    }

}
