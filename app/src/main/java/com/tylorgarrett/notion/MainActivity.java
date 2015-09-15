package com.tylorgarrett.notion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tylorgarrett.notion.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        performFragmentTransaction(new LoginFragment(), false);
    }

    public void performFragmentTransaction(Fragment fragment, boolean addToBackStack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.fragment_container, fragment);
        if ( addToBackStack ){
            ft.addToBackStack(null);
        }
        ft.commit();
    }



}
