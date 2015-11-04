package com.tylorgarrett.notion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment{

    public static String TAG = "SettingsFragment";

    MainActivity mainActivity;

    @Bind(R.id.settings_logout_cardview)
    CardView logoutButton;

    @Bind(R.id.setting_notebook_cardview)
    CardView notebookSettingsCard;

    @Bind((R.id.setting_note_cardview))
    CardView noteSettingsCard;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_profile_settings, menu);
        mainActivity.toolbar.setTitle(getResources().getString(R.string.settings_title));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OnClick({R.id.settings_logout_cardview, R.id.setting_notebook_cardview, R.id.settings_profile_cardview})
    public void onClick(View v){
        if ( v.getId() == R.id.settings_logout_cardview ){
            LoginManager.getInstance().logOut();
            mainActivity.performFragmentTransaction(LoginFragment.newInstance(), false, LoginFragment.TAG);
            mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if ( v.getId() == R.id.setting_notebook_cardview ){
            mainActivity.performFragmentTransaction(SubscriptionSettingsFragment.newInstance(), true, SubscriptionSettingsFragment.TAG);
        } else if (v.getId() == R.id.setting_note_cardview ){

        } else if ( v.getId() == R.id.settings_profile_cardview ){
            mainActivity.performFragmentTransaction(ProfileSettingsFragment.getInstance(), true, ProfileSettingsFragment.TAG);
        }
    }
}
