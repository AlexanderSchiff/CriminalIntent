package com.icsfl.aschiff.criminalintent;

import android.support.v4.app.Fragment;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
