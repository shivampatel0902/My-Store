package com.orderista.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class SignInResponse {
    @SerializedName("success")
    public Boolean success;


    @SerializedName("message")
    public String message;

    @SerializedName("user_data")
    public UserData userData;

    public  class UserData{
    @SerializedName("user_details")

    public List<UserDetails>userDetails;
    }
    public  class UserDetails{
        @SerializedName("user_id")
        public String user_id;

        @SerializedName("full_name")
        public String full_name;

        @SerializedName("mobile")
        public String mobile;


        @SerializedName("email")
        public String email;



    }

}
