package com.icsfl.aschiff.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 *
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
