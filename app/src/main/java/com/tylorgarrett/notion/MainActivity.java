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
import com.tylorgarrett.notion.models.LoginBody;
import com.tylorgarrett.notion.models.School;
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

    LoginFragment loginFragment;
    NotionData notionData;
    Bitmap profileImage;
    User currentUser;

    String facebookUserName;

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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setSupportActionBar(toolbar);

        if ( !isLoggedIn() ){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            performFragmentTransaction(loginFragment, false);
        } else {
            userLoggedIn();
        }
        mockSomeData();
    }

    public void performFragmentTransaction(Fragment fragment, boolean addToBackStack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, fragment);
        if ( addToBackStack ){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void userLoggedIn(){
        getUserData();
        if ( loginFragment != null ){
            getSupportFragmentManager().beginTransaction().remove(loginFragment).commit();
        }
        performFragmentTransaction(NotebooksFragment.newInstance("Notebooks"), false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.closeDrawer(Gravity.LEFT);
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

    public void mockSomeData(){
        notionData.clearOut();

        String[] notebookNames = {"CS182", "CS490", "OLS386", "CS426", "EAPS112", "PSY120", "PE304", "ECON251", "OLS182", "CNIT170", "RT314", "GS190"};
        String[] noteNames = {"Note000", "Note001", "Note002", "Note003", "Note004", "Note005", "Note006", "Note007", "Note008", "Note009", "Note010", "Note011", "Note012", "Note013", "Note014", "Note015"};
        int randomOne = new Random().nextInt(notebookNames.length - 1);
        String content = "ghghgh";
        for (int i=0; i<randomOne; i++){
            Notebook notebook = new Notebook(notebookNames[i]);
            int randomTwo = new Random().nextInt(noteNames.length);
            for (int j=0; j<randomTwo; j++){
                Note newNote = new Note(noteNames[j]);
                newNote.setContent(new StringBuffer(content));
                notebook.addNote(newNote);
            }
            notionData.addNotebook(notebook);
        }
    }

    public void getUserData(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Call<User> loginResponseCall = NotionService.getApi().login(new LoginBody("facebook", accessToken.getToken()));
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                currentUser = response.body();
                finishUserSetup();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void finishUserSetup(){
        try{
            setUpNavHeader();
        } catch (IOException e){
            e.printStackTrace();
        }
        if ( currentUser.getSchool_id().equals("") ){
            getListOfSchools();
        }
    }

    public void getListOfSchools(){
        Call<List<School>> schoolCall = NotionService.getApi().getSchools();
        schoolCall.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Response<List<School>> response, Retrofit retrofit) {
                List<School> schools = response.body();
                setSchoolDialog(schools);
            }

            @Override
            public void onFailure(Throwable t) {

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
                } else {
                    //ask to create a new request for adding a school.
                    Toast.makeText(getApplicationContext(), "Requested new School to be added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog schoolDialog = builder.create();
        schoolDialog.show();
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
        performFragmentTransaction(NotebooksFragment.newInstance(title), true);
        return false;
    }

    @Override
    public void onClick(View v) {
        performFragmentTransaction(SettingsFragment.newInstance(), true);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}