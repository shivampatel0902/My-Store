package com.orderista.network;


import com.orderista.models.BookResponceModel;
import com.orderista.models.CategoryResponseModel;
import com.orderista.models.OrderResponseModel;
import com.orderista.models.PlaceOrderResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {

    @GET("categories.php")
    Call<CategoryResponseModel> getCategoriesFromServer();


    @FormUrlEncoded
    @POST("books.php")
    Call<BookResponceModel> getBookByCategories(@Field("category") String category);

   @FormUrlEncoded
    @POST("place_order.php")
    Call<PlaceOrderResponse>placeOrder(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("order_history.php")
    Call<OrderResponseModel> getOrders(@Field("email") String email);
}
