package com.icsfl.aschiff.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Crime is the class that handles all of the details for a crime.
 *
 * @author Alex Schiff
 * @version 1.0
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    /**
     * Class constructor currently sets date to be the time when the app is created.
     */
    public Crime() {
        mDate = new Date();
        mId = UUID.randomUUID();
    }

    /**
     * @return the title of Crime.
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * @return the unique id associated with the Crime.
     * @see com.icsfl.aschiff.criminalintent.Crime#getTitle()
     */
    public UUID getId() {
        return mId;
    }

    /**
     * @return the title of the Crime. This method is called by {@link Crime#toString()}.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * @param title the title of the Crime.
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * The date format here is "MMM dd, yyyy hh:mm a".
     * For example, "Dec 21, 2010 10:00 PM".
     *
     * @return the date on which the Crime was committed.
     */
    public String getFullDateString() {
        return (new DateFormat()).format("MMM dd, yyyy hh:mm a", mDate).toString();
    }

    public String getDateString() {
        return (new DateFormat()).format("MMM dd, yyyy", mDate).toString();
    }

    public String getTimeString() {
        return (new DateFormat()).format("hh:mm a", mDate).toString();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    /**
     * @return true if the Crime has been solved and false otherwise.
     */
    public boolean isSolved() {
        return mSolved;
    }

    /**
     * @param solved the boolean representing whether or not the Crime has been solved.
     */
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}