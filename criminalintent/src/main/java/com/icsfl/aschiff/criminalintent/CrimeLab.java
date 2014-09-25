package com.icsfl.aschiff.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private ArrayList<Crime> mCrimes;
    private Context mAppContext;

    /**
     * Initializes the CrimeLab. We set the private variable mAppContext to be the input
     * and generate an array list of Crimes.
     *
     * @param appContext sets CrimeLab's context to the input.
     */
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
    }

    /**
     * If there is no crimelab, this method creates one.
     * @param context input context
     * @return a Crimelab
     */
    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }
}
