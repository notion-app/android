package com.tylorgarrett.notion.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Section;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.services.NotionService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tylorgarrett on 10/29/15.
 */
public class NewNotebookDialog {
    User currentUser;
    MainActivity mainActivity;

    public NewNotebookDialog(User currentUser, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.currentUser = currentUser;
        getSchoolsCourses();
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

    public void createCourseSelector(String text, List<String> data){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle(mainActivity.getResources().getString(R.string.select_course));
        View v = mainActivity.getLayoutInflater().inflate(R.layout.add_notebook_dialog_fragment, null);

        final Spinner selectCourseSpinner = (Spinner) v.findViewById(R.id.add_notebook_dialog_spinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, data);
        EditText editText = (EditText) v.findViewById(R.id.add_notebook_dialog_edittext);
        editText.setVisibility(View.GONE);
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
        builder.setTitle(mainActivity.getResources().getString(R.string.select_section));
        View v = mainActivity.getLayoutInflater().inflate(R.layout.add_notebook_dialog_fragment, null);

        final Spinner selectCourseSpinner = (Spinner) v.findViewById(R.id.add_notebook_dialog_spinner);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, NotionData.getInstance().getSectionNames(course));
        final EditText editText = (EditText) v.findViewById(R.id.add_notebook_dialog_edittext);

        selectCourseSpinner.setAdapter(courseAdapter);

        builder.setView(v);
        builder.setPositiveButton(mainActivity.getResources().getString(R.string.add_subscription), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = selectCourseSpinner.getSelectedItemPosition();
                List<Section> sections = NotionData.getInstance().getSections().get(course);
                Section s = sections.get(position);
                addUserSubscription(s, editText.getText().toString());

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addUserSubscription(Section s, String notebookName){
        Call<Notebook> call = NotionService.getApi().addSubscription(currentUser.getId(), currentUser.getFb_auth_token(), new SubscriptionBody(s.getNotebook_id(), notebookName));
        call.enqueue(new Callback<Notebook>() {
            @Override
            public void onResponse(Response<Notebook> response, Retrofit retrofit) {
                //check for 500 response
                Log.d("api", response.message().toString());
                NotionData.getInstance().addNotebook(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
