package com.icsfl.aschiff.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.internal.widget.IcsAdapterView;

import java.util.ArrayList;

/**
 * @author Alex Schiff
 * @version 1.0
 */
public class CrimeListFragment extends SherlockListFragment {
    private boolean mSubtitleVisible;

    /**
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getSherlockActivity().setTitle(R.string.crimes_title);
        setListAdapter(new CrimeAdapter(CrimeLab.getCrimeLab(getSherlockActivity()).getCrimes()));
        setRetainInstance(true);
        setSubtitleVisible(false);
    }

    /**
     * @param isVisible
     */
    public void setSubtitleVisible(boolean isVisible) {
        mSubtitleVisible = isVisible;
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mSubtitleVisible
                && getSherlockActivity().getSupportActionBar() != null) {
            getSherlockActivity().getSupportActionBar().setSubtitle(R.string.subtitle);
        }

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        Button reportButton = (Button) view.findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCrime();
            }
        });
        return view;
    }

    /**
     *
     * @param menu
     * @param menuInflater
     */
    @Override
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
                                    com.actionbarsherlock.view.MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_crime_list, menu);
        com.actionbarsherlock.view.MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null)
            showSubtitle.setTitle(R.string.hide_subtitle);
    }

    /**
     *
     * @param item
     * @return
     */
    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                createCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                if (getSherlockActivity().getSupportActionBar() != null) {
                    if (getSherlockActivity().getSupportActionBar().getSubtitle() == null) {
                        getSherlockActivity().getSupportActionBar().setSubtitle(R.string.subtitle);
                        setSubtitleVisible(true);
                        item.setTitle(R.string.hide_subtitle);
                    } else {
                        getSherlockActivity().getSupportActionBar().setSubtitle(null);
                        setSubtitleVisible(false);
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
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getSherlockActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        IcsAdapterView.AdapterContextMenuInfo info = (IcsAdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
        Crime crime = adapter.getItem(position);
        if (item.getItemId() == R.id.menu_item_delete_crime) {
            CrimeLab.getCrimeLab(getSherlockActivity()).deleteCrime(crime);
            adapter.notifyDataSetChanged();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void createCrime() {
        Crime crime = new Crime();
        CrimeLab.getCrimeLab(getSherlockActivity()).addCrime(crime);
        Intent intent = new Intent(getSherlockActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(intent, 0);
    }

    /**
     *
     * @param listView
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Intent intent = new Intent(getSherlockActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(intent, 0);
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        crimeNotifyDataSetChanged();
    }

    private void crimeNotifyDataSetChanged() {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        crimeNotifyDataSetChanged();
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getSherlockActivity(), android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup Parent) {
            if (convertView == null)
                convertView = getSherlockActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
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
