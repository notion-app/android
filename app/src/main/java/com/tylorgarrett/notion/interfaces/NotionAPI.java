package com.tylorgarrett.notion.interfaces;

import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.RetrofitResponse;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.Section;
import com.tylorgarrett.notion.models.SetSchool;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.SubscriptionDeleteBody;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.models.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/*
 * Created by tylorgarrett on 10/21/15.
 */
public interface NotionAPI {
    @POST("v1/login")
    Call<User> login(@Body LoginBody loginBody);

    @GET("v1/school")
    Call<List<School>> getSchools();

    @PUT("v1/user/{user_id}/school")
    Call<RetrofitResponse> setUserSchool(@Path("user_id") String userId, @Header("token") String token, @Body SetSchool school);

    @GET("v1/user/{user_id}/subscription")
    Call<List<Notebook>> getUserSubscriptions(@Path("user_id") String userId, @Header("token") String token);

    @GET("v1/school/{school_id}/course")
    Call<List<Course>> getSchoolCourses(@Path("school_id") String schoolId, @Header("token") String token);

    @GET("v1/school/{school_id}/course/{course_id}/section")
    Call<List<Section>> getCourseSections(@Path("school_id") String schoolId, @Path("course_id") String courseId, @Header("token") String token);

    @POST("v1/user/{user_id}/subscription")
    Call<Notebook> addSubscription(@Path("user_id") String userId, @Header("token") String token, @Body SubscriptionBody body);

    @PUT("v1/user/{user_id}/subscription")
    Call<Notebook> changeSubscription(@Header("token") String token, @Path("user_id") String userId, @Body SubscriptionBody body);

    @DELETE("v1/user/{user_id}/subscription/{notebook_id}")
    Call<Notebook> deleteSubscription(@Header("token") String token, @Path("user_id") String userId, @Path("notebook_id") String notebookId);

    @GET("v1/notebook/{notebook_id}/topic?user={user}&unjoined={unjoined}")
    Call<List<Topic>> getNotebookNotes(@Header("token") String token, @Path("user") boolean user, @Path("unjoined") boolean unjoined);

}
