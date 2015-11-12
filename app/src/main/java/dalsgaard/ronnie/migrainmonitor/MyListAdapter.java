package dalsgaard.ronnie.migrainmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dalsgaard.ronnie.migrainmonitor.util.Time;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class MyListAdapter extends ArrayAdapter<Symptom.Occurence> {
    private final Context context;

    public MyListAdapter(Context context) {
        super(context, R.layout.list_item_history, Symptom.occurences);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_history, null);
            holder = new ViewHolder();
            holder.base_rl = (RelativeLayout) convertView.findViewById(R.id.list_item_history_base);
            holder.name_tv = (TextView) convertView.findViewById(R.id.list_item_history_name_tv);
            holder.time_tv = (TextView) convertView.findViewById(R.id.list_item_history_time_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Symptom.Occurence occurence = Symptom.occurences.get(position);
        holder.base_rl.setBackgroundColor(0xff000000 + occurence.getColor());
        holder.name_tv.setText(occurence.getName().toUpperCase());
        holder.time_tv.setText(Time.toDateTimeString(occurence.getTime()));
        return convertView;
    }

    static class ViewHolder{
        TextView name_tv, time_tv;
        RelativeLayout base_rl;
    }
}