package com.tylorgarrett.notion.interfaces;

import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/*
 * Created by tylorgarrett on 10/21/15.
 */
public interface NotionAPI {
    @POST("v1/login")
    Call<User> login(@Body LoginBody loginBody);

    @GET("v1/school")
    Call<List<School>> getSchools();


}
