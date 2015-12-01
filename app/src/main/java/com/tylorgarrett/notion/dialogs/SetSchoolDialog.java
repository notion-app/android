package com.tylorgarrett.notion.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.RetrofitResponse;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.SetSchool;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.services.NotionService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tylorgarrett on 11/4/15.
 */
public class SetSchoolDialog {

    MainActivity mainActivity;
    User currentUser;
    NotionData notionData;

    public SetSchoolDialog(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.currentUser = mainActivity.getCurrentUser();
        this.notionData = NotionData.getInstance();
        setSchoolDialog(notionData.getSchools());
    }

    public void setSchoolDialog(final List<School> schools){
        List<String> schoolName = new ArrayList<String>(schools.size());
        for (School s: schools){
            schoolName.add(s.getName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Select A School");
        View view = mainActivity.getLayoutInflater().inflate(R.layout.school_dialog_fragment, null);
        final AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.school_dialog_textview);
        textView.setThreshold(2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, schoolName);
        textView.setAdapter(adapter);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean found = false;
                String schoolId = "";
                String schoolName = textView.getText().toString();
                for(School s: schools){
                    if ( s.getName().equals(schoolName) ){
                        found = true;
                        schoolId = s.getId();
                        break;
                    }
                }
                if (found){
                    mainActivity.getCurrentUser().setSchool_id(schoolId);
                    setCurrentUserSchool(schoolId);
                } else {
                    //ask to create a new request for adding a school.
                    mainActivity.debugToast("Request for a new School to be added");
                }
            }
        });
        AlertDialog schoolDialog = builder.create();
        schoolDialog.show();
    }

    public void setCurrentUserSchool(String schoolId){
        Call<RetrofitResponse> call = NotionService.getApi().setUserSchool(currentUser.getId(), currentUser.getFb_auth_token(), new SetSchool(schoolId));
        call.enqueue(new Callback<RetrofitResponse>() {
            @Override
            public void onResponse(Response<RetrofitResponse> response, Retrofit retrofit) {
                mainActivity.debugToast("Set User School Success" + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                mainActivity.debugToast("Set User School Failure" + t.getMessage());
            }
        });
    }
}
