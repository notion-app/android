package com.tylorgarrett.notion.interfaces;

import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.CreateNoteBody;
import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.RetrofitResponse;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.Section;
import com.tylorgarrett.notion.models.SetSchool;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.SubscriptionDeleteBody;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.models.UpdateNoteBody;
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
import retrofit.http.Query;

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

    @GET("v1/notebook/{notebook_id}/topic")
    Call<List<Topic>> getUserNotebookNotes(@Header("token") String token, @Path("notebook_id") String notebookId, @Query("user") boolean user);

    @GET("v1/notebook/{notebook_id}/topic")
    Call<List<Topic>> getUserUnjoinedNotebookNotes(@Header("token") String token, @Path("notebook_id") String notebookId, @Query("unjoined") boolean unjoined);

    @GET("v1/notebook/{noteook_id}/note/{note_id}")
    Call<Topic> getNote(@Header("token") String token, @Path("notebook_id") String notebookID, @Path("note_id") String noteId);

    @POST("v1/notebook/{notebook_id}/note")
    Call<Topic> createNote(@Header("token") String token, @Path("notebook_id") String notebookId, @Body CreateNoteBody body);

    @PUT("v1/notebook/{notebook_id}/note/{note_id}")
    Call<Topic> updateNote(@Header("token") String token, @Path("notebook_id") String notebookId, @Path("note_id") String noteId, @Body UpdateNoteBody updateNoteBody);



}
