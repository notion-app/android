package com.tylorgarrett.notion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.fragments.LoginFragment;
import com.tylorgarrett.notion.fragments.NotebooksFragment;
import com.tylorgarrett.notion.fragments.ProfileContentFragment;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    NotionData notionData;

    Bitmap profileImage;

    String facebookUserID;
    String facebookUserName;

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
                switch (option) {
                    case R.id.navigation_item_1:
                        title = "Notebooks";
                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                performFragmentTransaction(NotebooksFragment.newInstance(title), true);
                return false;
            }
        });

        RelativeLayout navHeaderLayout = (RelativeLayout) findViewById(R.id.nav_header_layout);
        navHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFragmentTransaction(ProfileContentFragment.newInstance(), true);
                drawerLayout.closeDrawer(Gravity.LEFT);
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
        facebookSetUp();
    }

    public void setUpNavHeader() throws IOException{
        final CircleImageView circleImageView = (CircleImageView) findViewById(R.id.nav_header_image);
        final TextView textView = (TextView) findViewById(R.id.nav_header_tv);
        textView.setText(getFacebookUserName());
        final String userID = getFacebookUserID();
        final URL url = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
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

    public void facebookSetUp(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                try {
                    String id = jsonObject.getString("id");
                    setFacebookUserName(jsonObject.getString("name"));
                    setFacebookUserID(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    setUpNavHeader();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        request.executeAsync();
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
        menu.clear();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ( isLoggedIn() ){
            super.onBackPressed();
        }
    }

    public void mockSomeData(){
        notionData.clearOut();

        String[] notebookNames = {"CS182", "CS490", "OLS386", "CS426", "EAPS112", "PSY120", "PE304", "ECON251", "OLS182", "CNIT170", "RT314", "GS190"};
        String[] noteNames = {"Note000", "Note001", "Note002", "Note003", "Note004", "Note005", "Note006", "Note007", "Note008", "Note009", "Note010", "Note011", "Note012", "Note013", "Note014", "Note015"};
        int randomOne = new Random().nextInt(notebookNames.length-1);
        String content = "#Header sizes\n" +
                "##Smaller header\n" +
                "###Even smaller header\n" +
                "\n" +
                "Paragraphs are obviously supported along with all the fancy text styling you could want.\n" +
                "There is *italic*, **bold** and ***bold italic***. Even links are supported, visit the\n" +
                "github page for Bypass [here](https://github.com/Uncodin/bypass).\n" +
                "\n" +
                "* Nested List\n" +
                "\t* One\n" +
                "\t* Two\n" +
                "\t* Three\n" +
                "* One\n" +
                "\t* One\n" +
                "\t* Two\n" +
                "\t* Three\n" +
                "\n" +
                "## Code Block Support\n" +
                "\n" +
                "    const char* str;\n" +
                "    str = env->GetStringUTFChars(markdown, NULL);\n" +
                "\t\t\t";

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

    public String getFacebookUserID() {
        return facebookUserID;
    }

    public void setFacebookUserID(String facebookUserID) {
        this.facebookUserID = facebookUserID;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getFacebookUserName() {
        return facebookUserName;
    }

    public void setFacebookUserName(String facebookUserName) {
        this.facebookUserName = facebookUserName;
    }
}