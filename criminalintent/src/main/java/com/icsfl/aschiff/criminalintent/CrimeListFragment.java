package com.icsfl.aschiff.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeListFragment extends ListFragment {
    private boolean mSubtitleVisible;

    /**
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        ArrayList<Crime> crimes = CrimeLab.getCrimeLab(getActivity()).getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(crimes);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mSubtitleVisible
                && getActivity().getActionBar() != null)
            getActivity().getActionBar().setSubtitle(R.string.subtitle);

        Button reportButton = (Button) view.findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crime crime = new Crime();
                CrimeLab.getCrimeLab(getActivity()).addCrime(crime);
                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null)
            showSubtitle.setTitle(R.string.hide_subtitle);
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getCrimeLab(getActivity()).addCrime(crime);
                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar() != null) {
                    if (getActivity().getActionBar().getSubtitle() == null) {
                        getActivity().getActionBar().setSubtitle(R.string.subtitle);
                        mSubtitleVisible = true;
                        item.setTitle(R.string.hide_subtitle);
                    } else {
                        getActivity().getActionBar().setSubtitle(null);
                        mSubtitleVisible = false;
                        item.setTitle(R.string.show_subtitle);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     */
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup Parent) {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            Crime crime = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_title_text_view);
            titleTextView.setText(crime.getTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_date_text_view);
            dateTextView.setText(crime.getFullDateString());
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solved_check_box);
            solvedCheckBox.setChecked(crime.isSolved());
            return convertView;
        }
    }
}
