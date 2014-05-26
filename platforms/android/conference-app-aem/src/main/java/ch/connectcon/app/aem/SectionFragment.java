package ch.connectcon.app.aem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arumsey on 2014-05-09.
 */
public class SectionFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    int mCurrentPosition = -1;
    final List<JSONObject> mCurrentListValues = new ArrayList<JSONObject>();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SectionFragment newInstance(int sectionNumber) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            mCurrentPosition = args.getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in a two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_SECTION_NUMBER);
        }

        View rootView = inflater.inflate(R.layout.section_view_frag, container, false);

        mCurrentListValues.clear();
        JSONObject data = ((MainActivity) getActivity()).getConferenceData();

        try {
            JSONArray allSessions = data.getJSONArray("sessions");
            for (int i = 0; i < allSessions.length(); i++)
            {
                JSONObject session = allSessions.getJSONObject(i);
                if (session.getInt("position") == mCurrentPosition) {
                    mCurrentListValues.add(session);
                }
            }
        } catch (JSONException e) {

        }

        AgendaAdaptor adapter = new AgendaAdaptor(inflater.getContext(), mCurrentListValues);
        setListAdapter(adapter);

        updateSectionView(rootView, data, mCurrentPosition);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            JSONObject item = mCurrentListValues.get(position);
            if (item != null){
                String url = item.getString("url");
                if (url != null && url.length() > 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(url));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().getApplicationContext().startActivity(browserIntent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateSectionView(View view, JSONObject data, int position) {
        TextView section_title = (TextView) view.findViewById(R.id.title);
        try {
            JSONArray allDays = data.getJSONArray("days");
            JSONObject day = allDays.getJSONObject(position - 1);
            section_title.setText(day.getString("date"));
        } catch (JSONException e) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_SECTION_NUMBER, mCurrentPosition);
    }


}
