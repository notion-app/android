package com.tylorgarrett.notion.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.SettingsAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Section;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.services.NotionService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionSettingsFragment extends Fragment {

    List data;
    MainActivity mainActivity;
    User currentUser;

    @Bind(R.id.subscription_settings_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.subscription_settings_fab)
    FloatingActionButton floatingActionButton;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public SubscriptionSettingsFragment() {}

    public static Fragment newInstance(){
        SubscriptionSettingsFragment f = new SubscriptionSettingsFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        currentUser = mainActivity.getCurrentUser();
        data = NotionData.getInstance().getNotebooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subscription_settings, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SettingsAdapter(data, mainActivity);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @OnClick(R.id.subscription_settings_fab)
    public void onClick(View v){
        getSchoolsCourses();
    }

    public void createCourseSelector(String text, List<String> data){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle(getResources().getString(R.string.add_subscription));
        View v = mainActivity.getLayoutInflater().inflate(R.layout.add_notebook_dialog_fragment, null);

        TextView textView = (TextView) v.findViewById(R.id.add_notebook_dialog_textview);
        textView.setText(text);

        final Spinner selectCourseSpinner = (Spinner) v.findViewById(R.id.add_notebook_dialog_spinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, data);
        selectCourseSpinner.setAdapter(courseAdapter);

        builder.setView(v);
        builder.setPositiveButton("Select Section", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = selectCourseSpinner.getSelectedItem().toString();
                Course c = NotionData.getInstance().getCourseByName(courseName);
                getCourseSections(c);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getSchoolsCourses(){
        Call<List<Course>> call = NotionService.getApi().getSchoolCourses(currentUser.getSchool_id(), currentUser.getFb_auth_token());
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Response<List<Course>> response, Retrofit retrofit) {
                NotionData.getInstance().setCourses(response.body());
                createCourseSelector("Select Course", NotionData.getInstance().getCourseNames());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void getCourseSections(Course course){
        final Course c = course;
        Call<List<Section>> call = NotionService.getApi().getCourseSections(currentUser.getSchool_id(), course.getId(), currentUser.getFb_auth_token());
        call.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Response<List<Section>> response, Retrofit retrofit) {
                NotionData.getInstance().getSections().put(c, response.body());
                createSectionSelector(c);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void createSectionSelector(final Course course){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle(getResources().getString(R.string.add_subscription));
        View v = mainActivity.getLayoutInflater().inflate(R.layout.add_notebook_dialog_fragment, null);

        TextView textView = (TextView) v.findViewById(R.id.add_notebook_dialog_textview);
        textView.setText("Select Section");


        final Spinner selectCourseSpinner = (Spinner) v.findViewById(R.id.add_notebook_dialog_spinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, NotionData.getInstance().getSectionNames(course));
        selectCourseSpinner.setAdapter(courseAdapter);

        builder.setView(v);
        builder.setPositiveButton("Select Section", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //add the subscription
                //get the selected Course
                int position = selectCourseSpinner.getSelectedItemPosition();
                List<Section> sections = NotionData.getInstance().getSections().get(course);
                Section s = sections.get(position);
                addUserSubscription(s);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addUserSubscription(Section s){
        Call<Notebook> call = NotionService.getApi().addSubscription(currentUser.getId(), currentUser.getFb_auth_token(), new SubscriptionBody(s.getNotebook_id(), "New Notebook"));
        call.enqueue(new Callback<Notebook>() {
            @Override
            public void onResponse(Response<Notebook> response, Retrofit retrofit) {
                NotionData.getInstance().addNotebook(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
