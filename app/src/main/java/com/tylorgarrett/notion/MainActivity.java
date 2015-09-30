package com.tylorgarrett.notion;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.fragments.LoginFragment;
import com.tylorgarrett.notion.fragments.NotionFragment;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    NotionData notionData;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.navigation_view)
    public NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar();
        loginFragment = LoginFragment.newInstance();
        notionData = NotionData.getInstance();
        mockSomeData();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int option = menuItem.getItemId();
                menuItem.setChecked(true);
                String title = "";
                switch ( option ){
                    case R.id.navigation_item_1:
                        title = "Notebooks";
                        break;
                    case R.id.navigation_item_2:
                        title = "Notes";
                        break;
                    case R.id.navigation_item_3:
                        title = "Archive";
                        break;
                    case R.id.navigation_item_4:
                        title = "Trash";
                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                performFragmentTransaction(NotionFragment.newInstance(title), true);
                return false;
            }
        });

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
        if ( loginFragment != null ){
            getSupportFragmentManager().beginTransaction().remove(loginFragment).commit();
        }
        performFragmentTransaction(NotionFragment.newInstance("Notebooks"), false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }


    public boolean isLoggedIn() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token != null;
    }

    public void setUpToolbar(){
        toolbar.setTitleTextColor(getResources().getColor(R.color.NotionYellow));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.getItem(0);
        SpannableString searchString = new SpannableString("Log Out");
        searchString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.NotionYellow)), 0, searchString.length(), 0);
        searchItem.setTitle(searchString);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();
        switch (option) {
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                performFragmentTransaction(LoginFragment.newInstance(), false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mockSomeData(){
        int notebookCount = 8;
        int noteCount = 5;
        String[] noteTitles = {"Lecture One", "Homework 2", "Exam 3", "Study Guide", "Final Exam"};
        String[] notebookTitles = {"CS182", "CS354", "Senior Project", "Discrete Mathematics", "Applied Leadership", "Intro To Law", "Health 101", "Wine Tasting"};
        for (int i=0; i<notebookCount; i++){
            Notebook notebook = new Notebook(notebookTitles[i]);
            for (int j=0; j<noteCount; j++ ){
                notebook.addNote(new Note(noteTitles[j]));
            }
            notionData.addNotebook(notebook);
        }
    }
}