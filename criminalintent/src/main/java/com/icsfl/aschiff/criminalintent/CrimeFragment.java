package com.icsfl.aschiff.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;

import java.util.Date;
import java.util.UUID;

/**
 * CrimeFragment is the Fragment that handles the UI for entering/managing a particular Crime.
 *
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeFragment extends SherlockFragment {
    /**
     * EXTRA_CRIME_ID is a static String. I am not exactly sure why it is necessary.
     */
    public static final String EXTRA_CRIME_ID = "com.icsfl.aschiff.criminalintent.crime_id";
    private static final String KEY_INDEX = "index";
    private static final String DIALOG_DATE = "DATE";
    private static final String DIALOG_TIME = "TIME";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private Crime mCrime;
    private Button mDateButton;
    private Button mTimeButton;
    private EditText mTitleField;
    private CheckBox mSolvedCheckBox;

    /**
     * This method creates a new CrimeFragment given a UUID.
     *
     * @param crimeId is the input UUID that is tied to a particular Crime.
     * @return the created CrimeFragment.
     */
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCrime(Crime crime) {
        mCrime = crime;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCrime(CrimeLab.getCrimeLab(getSherlockActivity())
                .getCrime((UUID) getArguments().getSerializable(EXTRA_CRIME_ID)));
        setHasOptionsMenu(true);
    }

    /**
     *
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB &&
                getSherlockActivity().getSupportActionBar() != null &&
                NavUtils.getParentActivityName(getSherlockActivity()) != null)
            getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fragmentManager, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        if (savedInstanceState == null) {
            mDateButton.setText(mCrime.getDateString());
            mTimeButton.setText(mCrime.getTimeString());
        } else {
            mDateButton.setText(savedInstanceState.getStringArray(KEY_INDEX)[0]);
            mTimeButton.setText(savedInstanceState.getStringArray(KEY_INDEX)[1]);
        }

        return view;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (NavUtils.getParentActivityName(getSherlockActivity()) != null)
                NavUtils.navigateUpFromSameTask(getSherlockActivity());
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getCrimeLab(getSherlockActivity()).saveCrimes();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_DATE) {
                mCrime.setDate((Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE));
                mDateButton.setText(mCrime.getDateString());
            } else if (requestCode == REQUEST_TIME) {
                mCrime.setDate((Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME));
                mTimeButton.setText(mCrime.getTimeString());
            }
        }
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArray(KEY_INDEX, new String[]{mDateButton.getText().toString(),
                mTimeButton.getText().toString()});
    }
}
