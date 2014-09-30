package com.icsfl.aschiff.criminalintent;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeListActivity extends SingleFragmentActivity {
    /**
     * @return
     */
    @Override
    protected SherlockFragment createFragment() {
        return new CrimeListFragment();
    }
}