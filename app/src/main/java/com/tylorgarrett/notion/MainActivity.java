package com.tylorgarrett.notion;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.fragments.LoginFragment;
import com.tylorgarrett.notion.fragments.NotebooksFragment;
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
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                performFragmentTransaction(NotebooksFragment.newInstance(title), true);
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
        performFragmentTransaction(NotebooksFragment.newInstance("Notebooks"), false);
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
        String[] notebookNames = {"CS182", "CS490", "OLS386", "CS426", "EAPS112", "PSY120", "PE304", "ECON251", "OLS182", "CNIT170", "RT314", "GS190"};
        String[] noteNames = {"Note000", "Note001", "Note002", "Note003", "Note004", "Note005", "Note006", "Note007", "Note008", "Note009", "Note010", "Note011", "Note012", "Note013", "Note014", "Note015"};
        String content = "Text messaging, or texting, is the act of composing and sending brief, electronic messages between two or more mobile phones, or fixed or portable devices over a phone network. The term originally referred to messages sent using the Short Message Service (SMS). It has grown to include messages containing image, video, and sound content (known as MMS messages). The sender of a text message is known as a texter, while the service itself has different colloquialisms depending on the region. It may simply be referred to as a text in North America, the United Kingdom, Australia, New Zealand and the Philippines, an SMS in most of mainland Europe, and an MMS or SMS in the Middle East, Africa, and Asia.";

        int randomOne = new Random().nextInt(notebookNames.length-1);

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
}