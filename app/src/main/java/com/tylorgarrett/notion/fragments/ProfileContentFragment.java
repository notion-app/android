package com.tylorgarrett.notion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileContentFragment extends Fragment {

    MainActivity mainActivity;

    @Bind(R.id.profile_content_logout_button)
    Button logoutButton;

    @Bind(R.id.profile_content_circleimageview)
    CircleImageView profileImage;

    @Bind(R.id.profile_content_name_textview)
    TextView profileName;

    public static ProfileContentFragment newInstance() {
        ProfileContentFragment fragment = new ProfileContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileContentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        ButterKnife.bind(this, v);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                mainActivity.performFragmentTransaction(LoginFragment.newInstance(), false);
                mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });
        profileImage.setImageBitmap(mainActivity.getProfileImage());
        profileName.setText(mainActivity.getFacebookUserName());
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_profile_settings, menu);
        mainActivity.toolbar.setTitle(getResources().getString(R.string.profile_settings));
        super.onCreateOptionsMenu(menu, inflater);
    }
}
