package com.tylorgarrett.notion.interfaces;

import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by tylorgarrett on 10/21/15.
 */
public interface NotionAPI {
    @POST("/v1/login")
    Call<User> login(@Body LoginBody loginBody);
}
