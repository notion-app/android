package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rey.material.widget.Button;
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

    MainActivity mainActivity;

    @Bind(R.id.login_button)
    Button loginButton;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.login_button)
    public void loginOnClick(View v){
        
    }


}
