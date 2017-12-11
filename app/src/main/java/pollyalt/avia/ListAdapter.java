package pollyalt.avia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<JSONObject> {

    int ListRow;
    ArrayList<JSONObject> List;
    Context Context;

    public ListAdapter(Context context, int listRow, int id, ArrayList<JSONObject> list){
        super(context, listRow, id, list);
        this.Context = context;
        this.ListRow = listRow;
        this.List=list;
    }
    static class ViewHolder {
        public TextView textId;
        public TextView textDate;
        public TextView textTime;
        public TextView textPrice;

    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(ListRow, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textId= (TextView) itemView.findViewById(R.id.flight);
        viewHolder.textDate= (TextView) itemView.findViewById(R.id.date);
        viewHolder.textTime= (TextView) itemView.findViewById(R.id.time);
        viewHolder.textPrice= (TextView) itemView.findViewById(R.id.price);
        itemView.setTag(viewHolder);

        ViewHolder holder = (ViewHolder) itemView.getTag();
        try {
            holder.textId.setText(List.get(position).getString("id"));
            holder.textDate.setText(List.get(position).getString("date_flight"));
            holder.textTime.setText(List.get(position).getString("time_flight"));
            holder.textPrice.setText(List.get(position).getString("cost"));
        } catch(Exception e) {

        }
        return itemView;
    }

}

