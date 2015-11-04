package com.tylorgarrett.notion;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.fragments.LoginFragment;
import com.tylorgarrett.notion.fragments.NotebooksFragment;
import com.tylorgarrett.notion.fragments.SettingsFragment;
import com.tylorgarrett.notion.listeners.OnUserSubscriptionsReadyListener;
import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.RetrofitResponse;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.SetSchool;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.services.NotionService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    boolean debug = false;

    LoginFragment loginFragment;
    NotionData notionData;
    Bitmap profileImage;
    User currentUser;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.navigation_view)
    public NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @Bind(R.id.nav_header_layout)
    RelativeLayout navHeaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar();

        loginFragment = LoginFragment.newInstance();
        notionData = NotionData.getInstance();
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderLayout.setOnClickListener(this);

        if ( !isLoggedIn() ){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            performFragmentTransaction(loginFragment, false, LoginFragment.TAG);
        } else {
            debugToast("User is logged in");
            userLoggedIn();
        }

    }

    public void performFragmentTransaction(Fragment fragment, boolean addToBackStack, String tag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, fragment, tag);
        if ( addToBackStack ){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public Fragment findFragmentByTag(String tag){
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(tag);
    }

    public void userLoggedIn(){
        getUserData();
    }

    public void setUpNavHeader() throws IOException{
        final CircleImageView circleImageView = (CircleImageView) findViewById(R.id.nav_header_image);
        final TextView textView = (TextView) findViewById(R.id.nav_header_tv);
        textView.setText(currentUser.getName());
        final URL url = new URL(currentUser.getFb_profile_pic());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    setProfileImage(bitmap);
                } catch (IOException e){
                    e.printStackTrace();
                }
                final Bitmap result = bitmap;
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        circleImageView.setImageBitmap(result);
                    }
                });

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.getItem(0);
        SpannableString searchString = new SpannableString("Log Out");
        searchString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.NotionYellow)), 0, searchString.length(), 0);
        searchItem.setTitle(searchString);
        return true;
    }


    public void getUserData(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Call<User> loginResponseCall = NotionService.getApi().login(new LoginBody("facebook", accessToken.getToken()));
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                currentUser = response.body();
                getUserSubscriptions();
                finishUserSetup();
                debugToast("Get User Data Success" + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                debugToast("Get User Data Failure" + t.getMessage());
            }
        });
    }

    public void finishUserSetup(){
        getListOfSchools();
        try{
            setUpNavHeader();
        } catch (IOException e){
            e.printStackTrace();
        }
        getUserSubscriptions();
    }

    public void getListOfSchools(){
        Call<List<School>> schoolCall = NotionService.getApi().getSchools();
        schoolCall.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Response<List<School>> response, Retrofit retrofit) {
                List<School> schools = response.body();
                NotionData.getInstance().setSchools(schools);
                if (currentUser.getSchool_id().equals("")) {
                    setSchoolDialog(schools);
                }
                debugToast(response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                debugToast(t.getMessage());
            }
        });
    }

    public void setSchoolDialog(final List<School> schools){
        List<String> schoolName = new ArrayList<String>(schools.size());
        for (School s: schools){
            schoolName.add(s.getName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select A School");
        View view = getLayoutInflater().inflate(R.layout.school_dialog_fragment, null);
        final AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.school_dialog_textview);
        textView.setThreshold(2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schoolName);
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
                    currentUser.setSchool_id(schoolId);
                    setCurrentUserSchool(schoolId);
                } else {
                    //ask to create a new request for adding a school.
                    debugToast("Request for a new School to be added");
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
                debugToast("Set User School Success" + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                debugToast("Set User School Failure" + t.getMessage());
            }
        });
    }

    public void getUserSubscriptions(){
        Call<List<Notebook>> userSubs = NotionService.getApi().getUserSubscriptions(currentUser.getId(), currentUser.getFb_auth_token());
        userSubs.enqueue(new Callback<List<Notebook>>() {
            @Override
            public void onResponse(Response<List<Notebook>> response, Retrofit retrofit) {
                NotionData.getInstance().setNotebooks(response.body());
                openMainFragment();
                debugToast("Get User Subscriptions Success: " + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                debugToast("Get User Subscription Failure: " + t.getMessage());
            }
        });
    }

    public void openMainFragment(){
        if ( loginFragment != null ){
            getSupportFragmentManager().beginTransaction().remove(loginFragment).commit();
        }
        performFragmentTransaction(NotebooksFragment.getInstance(), false, NotebooksFragment.TAG);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ( isLoggedIn() ){
            super.onBackPressed();
        }
    }

    public boolean isLoggedIn() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token != null;
    }

    public void setUpToolbar(){
        toolbar.setTitleTextColor(getResources().getColor(R.color.NotionYellow));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setSupportActionBar(toolbar);
    }

    public User getCurrentUser(){
        return currentUser;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int option = menuItem.getItemId();
        menuItem.setChecked(true);
        String title = "";
        switch (option) {
            case R.id.navigation_item_1:
                title = "Notebooks";
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        performFragmentTransaction(NotebooksFragment.getInstance(), true, NotebooksFragment.TAG);
        return false;
    }

    @Override
    public void onClick(View v) {
        performFragmentTransaction(SettingsFragment.newInstance(), true, SettingsFragment.TAG);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void debugToast(String message){
        if ( debug ){
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
        Log.d("logger", message);
    }
}