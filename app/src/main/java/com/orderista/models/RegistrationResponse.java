package com.orderista.models;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;
}
