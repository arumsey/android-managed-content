package ch.connectcon.app.aem;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    JSONObject data = null;

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
        data = getJsonData();
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

        List<JSONObject> values = new ArrayList<JSONObject>();
        try {
            JSONArray allSessions = data.getJSONArray("sessions");
            for (int i = 0; i < allSessions.length(); i++)
            {
                JSONObject session = allSessions.getJSONObject(i);
                if (session.getInt("position") == mCurrentPosition) {
                    values.add(session);
                }
            }
        } catch (JSONException e) {

        }

        AgendaAdaptor adapter = new AgendaAdaptor(inflater.getContext(), values);
        setListAdapter(adapter);

        updateSectionView(mCurrentPosition, rootView);

        return rootView;
    }

    public void updateSectionView(int position, View view) {
        TextView section_title = (TextView) view.findViewById(R.id.title);
        if (position == 1) {
            section_title.setText(R.string.date_day1);
        } else if (position == 2) {
            section_title.setText(R.string.date_day2);
        }
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_SECTION_NUMBER, mCurrentPosition);
    }

    private JSONObject getJsonData() {

        try {

            InputStream is = getActivity().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            return new JSONObject(json);


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
