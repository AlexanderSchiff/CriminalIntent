package com.icsfl.aschiff.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by aschiff on 8/20/2014.
 */
//crime class
public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDateString;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mDate = new Date();
        mId = UUID.randomUUID();
        mDateString = (new DateFormat()).format("MM/dd/yyyy hh:mm", mDate).toString();
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDateString() {
        return mDateString;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}