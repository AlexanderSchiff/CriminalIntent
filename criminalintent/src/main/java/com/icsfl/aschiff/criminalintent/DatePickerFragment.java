package com.icsfl.aschiff.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.DialogFragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.icsfl.aschiff.criminalintent.date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE, mDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_date_picker_date);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePickerView, int year, int monthOfYear, int dayOfMonth) {
                        Calendar theCalendar = Calendar.getInstance();
                        theCalendar.setTime(mDate);
                        theCalendar.set(theCalendar.YEAR, year);
                        theCalendar.set(theCalendar.MONTH, monthOfYear);
                        theCalendar.set(theCalendar.DAY_OF_MONTH, dayOfMonth);
                        mDate = theCalendar.getTime();
                        getArguments().putSerializable(EXTRA_DATE, mDate);
                    }
                });
        return new AlertDialog.Builder(getActivity()).setView(view).setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }).create();
    }
}
