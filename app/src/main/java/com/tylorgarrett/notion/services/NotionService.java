package com.tylorgarrett.notion.services;

import com.tylorgarrett.notion.interfaces.NotionAPI;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/*
 * Created by tylorgarrett on 10/21/15.
 */
public class NotionService {
    private static String API_ROOT = "http://notion-api-dev.herokuapp.com/";
    private static NotionAPI REST_CLIENT;

    static {
        setUpRestClient();
    }

    public static NotionAPI getApi(){
        return REST_CLIENT;
    }

    private static void setUpRestClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        REST_CLIENT = retrofit.create(NotionAPI.class);
    }

}
