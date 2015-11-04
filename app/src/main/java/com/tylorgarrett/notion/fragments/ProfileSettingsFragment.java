package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSettingsFragment extends Fragment {

    public static String TAG = "ProfileSettingsFragment";

    private static ProfileSettingsFragment instance = null;

    User currentUser;
    MainActivity mainActivity;

    @Bind(R.id.profile_settings_email_textview)
    TextView emailTextView;

    @Bind(R.id.profile_settings_imageview)
    ImageView imageView;

    @Bind(R.id.profile_settings_name_textview)
    TextView nameTextView;

    @Bind(R.id.profile_settings_school_textview)
    TextView schoolTextView;

    public ProfileSettingsFragment() {}

    public static Fragment getInstance(){
        if ( instance == null ){
            instance = new ProfileSettingsFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        currentUser = mainActivity.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        ButterKnife.bind(this, v);

        School school = NotionData.getInstance().getSchoolById(currentUser.getSchool_id());

        imageView.setImageBitmap(mainActivity.getProfileImage());
        emailTextView.setText(currentUser.getEmail());
        nameTextView.setText(currentUser.getName());
        schoolTextView.setText(school.getName());

        return v;
    }


}
