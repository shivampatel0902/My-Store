package com.orderista.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponceModel {

    @SerializedName("books")
    private List<BookModel> books;

    public List<BookModel> getBooks()
    {
        return  books;
    }

}
