package com.icsfl.aschiff.criminalintent;

import android.text.format.DateFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Crime is the class that handles all of the details for a crime.
 *
 * @author Alex Schiff
 * @version 1.0
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    /**
     * Class constructor currently sets date to be the time when the app is created.
     */
    public Crime() {
        setDate(new Date());
        setId(UUID.randomUUID());
    }

    public Crime(JSONObject jsonObject) throws JSONException {
        setId(UUID.fromString(jsonObject.getString(JSON_ID)));
        if (jsonObject.has(JSON_TITLE))
            setTitle(jsonObject.getString(JSON_TITLE));
        setSolved(jsonObject.getBoolean(JSON_SOLVED));
        setDate(jsonObject.getLong(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_TITLE, mTitle);
        jsonObject.put(JSON_SOLVED, mSolved);
        jsonObject.put(JSON_DATE, mDate.getTime());
        return jsonObject;
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

    private void setId(UUID id) {
        mId = id;
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
        return DateFormat.format("MMM dd, yyyy hh:mm a", mDate).toString();
    }

    public String getDateString() {
        return DateFormat.format("MMM dd, yyyy", mDate).toString();
    }

    public String getTimeString() {
        return DateFormat.format("hh:mm a", mDate).toString();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(long longDate) {
        mDate = new Date(longDate);
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