package dalsgaard.ronnie.migrainmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dalsgaard.ronnie.migrainmonitor.util.Time;

import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_DATE;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_SYMPTOM;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class MyListAdapter extends ArrayAdapter<MyListAdapter.ListItem> {
    private final Context context;

    public MyListAdapter(Context context) {
        super(context, R.layout.list_item_history_occurrence, Symptom.occurrences);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (this.getItemViewType(position)) {
            case TYPE_SYMPTOM:
                SymptomViewHolder sHolder;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item_history_occurrence, null);
                    sHolder = new SymptomViewHolder();
                    sHolder.base_rl = (RelativeLayout) convertView.findViewById(R.id.list_item_history_base);
                    sHolder.name_tv = (TextView) convertView.findViewById(R.id.list_item_history_name_tv);
                    sHolder.time_tv = (TextView) convertView.findViewById(R.id.list_item_history_time_tv);
                    convertView.setTag(sHolder);
                } else {
                    sHolder = (SymptomViewHolder) convertView.getTag();
                }
                Symptom.Occurrence occurrence = (Symptom.Occurrence) Symptom.occurrences.get(position);
                sHolder.base_rl.setBackgroundColor(0xff000000 + occurrence.getColor());
                sHolder.name_tv.setText(occurrence.getName().toUpperCase());
                sHolder.time_tv.setText(Time.toDateTimeString(occurrence.getTime()));
                return convertView;

            case TYPE_DATE:
                DateTimeHolder dHolder;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item_history_date, null);
                    dHolder = new DateTimeHolder();
                    dHolder.datetime_tv = (TextView) convertView.findViewById(R.id.list_item_history_date_tv);
                    dHolder.margin = convertView.findViewById(R.id.list_item_history_date_margin);
                    convertView.setTag(dHolder);
                } else {
                    dHolder = (DateTimeHolder) convertView.getTag();
                }
                Symptom.DateItem dt = (Symptom.DateItem) Symptom.occurrences.get(position);
                dHolder.datetime_tv.setText(Time.toDateTimeString(dt.getTime()).split(" ")[0]);
                if(position == 0) dHolder.margin.setVisibility(View.GONE);
                return convertView;

            default: return null;
        }
    }

    static class SymptomViewHolder {
        TextView name_tv, time_tv;
        RelativeLayout base_rl;
    }

    static class DateTimeHolder {
        TextView datetime_tv;
        View margin;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return Symptom.occurrences.get(position).getType();
    }

    interface ListItem {
        public static final int TYPE_DATE = 0;
        public static final int TYPE_SYMPTOM = 1;
        public int getType();
        public long getTime();
    }
}
