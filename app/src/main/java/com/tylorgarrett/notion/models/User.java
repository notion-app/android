package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 10/21/15.
 */
public class User {
    String id;
    String name;
    String email;
    boolean verified;
    String school_id;
    String auth_method;
    String fb_user_id;
    String fb_auth_token;
    String fb_profile_pic;

    public User(String id, String name, String email, boolean verified, String school_id, String auth_method, String fb_user_id, String fb_auth_token, String fb_profile_pic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.verified = verified;
        this.school_id = school_id;
        this.auth_method = auth_method;
        this.fb_user_id = fb_user_id;
        this.fb_auth_token = fb_auth_token;
        this.fb_profile_pic = fb_profile_pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getAuth_method() {
        return auth_method;
    }

    public void setAuth_method(String auth_method) {
        this.auth_method = auth_method;
    }

    public String getFb_user_id() {
        return fb_user_id;
    }

    public void setFb_user_id(String fb_user_id) {
        this.fb_user_id = fb_user_id;
    }

    public String getFb_auth_token() {
        return fb_auth_token;
    }

    public void setFb_auth_token(String fb_auth_token) {
        this.fb_auth_token = fb_auth_token;
    }

    public String getFb_profile_pic() {
        return fb_profile_pic;
    }

    public void setFb_profile_pic(String fb_profile_pic) {
        this.fb_profile_pic = fb_profile_pic;
    }
}
