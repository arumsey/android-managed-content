package ch.connectcon.app.aem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 */
public class AgendaAdaptor extends ArrayAdapter<JSONObject> {

    private List<JSONObject> itemList;
    private Context context;

    public AgendaAdaptor(Context ctx, List<JSONObject> itemList) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public JSONObject getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.section_item, null);
        }

        try {
            JSONObject c = itemList.get(position);
            TextView text = (TextView) v.findViewById(R.id.label);
            text.setText(c.getString("title"));

            TextView text1 = (TextView) v.findViewById(R.id.time);
            text1.setText(c.getString("time"));
        } catch (JSONException e) {

        }

        return v;

    }

    public List<JSONObject> getItemList() {
        return itemList;
    }

    public void setItemList(List<JSONObject> itemList) {
        this.itemList = itemList;
    }
}
