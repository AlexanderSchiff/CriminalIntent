package com.icsfl.aschiff.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";
    private static CrimeLab sCrimeLab;
    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        setAppContext(appContext);
        setSerializer(new CriminalIntentJSONSerializer(mAppContext, FILENAME));
        try {
            setCrimes(mSerializer.loadCrimes());
        } catch (Exception e) {
            setCrimes(new ArrayList<Crime>());
            Log.e(TAG, "Error loading crimes: ", e);
        }
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

    private void setAppContext(Context appContext) {
        mAppContext = appContext;
    }

    private void setSerializer(CriminalIntentJSONSerializer serializer) {
        mSerializer = serializer;
    }

    /**
     * @param crime
     */
    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    /**
     *
     * @param crime
     */
    public void deleteCrime(Crime crime) {
        mCrimes.remove(crime);
    }

    /**
     * @return
     */
    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving crimes: ", e);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     *
     * @param crimes
     */
    public void setCrimes(ArrayList<Crime> crimes) {
        mCrimes = crimes;
    }

    /**
     *
     * @param id
     * @return
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id))
                return crime;
        }
        return null;
    }
}
