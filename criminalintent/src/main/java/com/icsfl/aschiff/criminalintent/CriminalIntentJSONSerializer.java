package com.icsfl.aschiff.criminalintent;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by aschiff on 9/25/2014.
 */
public class CriminalIntentJSONSerializer {
    private static final String TAG = "CriminalIntentJSONSerializer";
    private Context mContext;
    private String mFileName;

    /**
     * @param context
     * @param fileName
     */
    public CriminalIntentJSONSerializer(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mContext.openFileInput(mFileName)));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) jsonString.append(line);
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++)
                crimes.add(new Crime(array.getJSONObject(i)));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "exception: ", e);
        } finally {
            if (bufferedReader != null)
                bufferedReader.close();
        }
        return crimes;
    }

    /**
     *
     * @param crimes
     * @throws JSONException
     * @throws IOException
     */
    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (Crime crime : crimes)
            array.put(crime.toJSON());
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(mContext.openFileOutput(mFileName, Context.MODE_PRIVATE));
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
