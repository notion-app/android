package com.tylorgarrett.notion.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * created by tylorgarrett
 * 09/14/2015
 */
public class LoginFragment extends Fragment {

    MainActivity activity;
    CallbackManager callbackManager;
    public static String TAG = "LoginFragment";

    @Bind(R.id.login_button)
    LoginButton loginButton;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        callbackManager = CallbackManager.Factory.create();
        activity.getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                activity.setIsLoggedIn(true);
                restoreActionBar();
                activity.userLoggedIn();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "Cacnelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void restoreActionBar(){
        activity.getSupportActionBar().show();
    }


}
