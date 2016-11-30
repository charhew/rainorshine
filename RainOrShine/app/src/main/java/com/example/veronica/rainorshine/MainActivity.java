package com.example.veronica.rainorshine;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            HomeFragment homeFrag = new HomeFragment();
            NavBarFragment navFrag = new NavBarFragment();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();

            //arg 1: where we want to add the fragment
            //arg 2: the fragment
            //arg 3: String to identify the fragmenth
            transaction.add(R.id.MyLayout, homeFrag, "Home Fragment");
            transaction.add(R.id.MyLayout, navFrag, "Nav Bar Fragment");
            transaction.commit();
        } else {
            //do nothing
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
