package dalsgaard.ronnie.migrainmonitor;

import android.app.Activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import dalsgaard.ronnie.migrainmonitor.util.Time;

import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_DATE;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_SYMPTOM;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class MyListAdapter extends ArrayAdapter<MyListAdapter.ListItem> implements View.OnClickListener {
    private final Activity mActivity;

    public MyListAdapter(Activity activity) {
        super(activity, R.layout.list_item_history_occurrence, Symptom.getOccurrences());
        mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (this.getItemViewType(position)) {
            case TYPE_SYMPTOM:
                SymptomViewHolder sHolder;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item_history_occurrence, null);
                    sHolder = new SymptomViewHolder();
                    sHolder.base_rl = (RelativeLayout) convertView.findViewById(R.id.list_item_history_base);
                    sHolder.name_tv = (TextView) convertView.findViewById(R.id.list_item_history_name_tv);
                    sHolder.time_tv = (TextView) convertView.findViewById(R.id.list_item_history_time_tv);
                    sHolder.more_btn = (ImageView) convertView.findViewById(R.id.list_item_history_more_btn);
                    convertView.setTag(sHolder);
                } else {
                    sHolder = (SymptomViewHolder) convertView.getTag();
                }
                Symptom.Occurrence occurrence = (Symptom.Occurrence) Symptom.getOccurrences().get(position);
                sHolder.base_rl.setBackgroundColor(0xff000000 + occurrence.getColor());
                sHolder.name_tv.setText(occurrence.getName().toUpperCase());
                sHolder.time_tv.setText(Time.toDateTimeString(occurrence.getTime()));
                sHolder.more_btn.setOnClickListener(this);
                sHolder.more_btn.setTag(occurrence);
                return convertView;

            case TYPE_DATE:
                DateTimeHolder dHolder;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item_history_date, null);
                    dHolder = new DateTimeHolder();
                    dHolder.datetime_tv = (TextView) convertView.findViewById(R.id.list_item_history_date_tv);
                    convertView.setTag(dHolder);
                } else {
                    dHolder = (DateTimeHolder) convertView.getTag();
                }
                Symptom.Header dt = (Symptom.Header) Symptom.getOccurrences().get(position);
                dHolder.datetime_tv.setText(Time.toDateTimeString(dt.getTime()).split(" ")[0]);
                return convertView;

            default:
                return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_item_history_more_btn:
                Symptom.Occurrence occurrence = (Symptom.Occurrence) v.getTag();
                Toast.makeText(mActivity, "MORE " + occurrence.getName(), Toast.LENGTH_SHORT).show();
                DialogFragment fragment = new OccurrenceMoreFragment();
                Bundle bundle = new Bundle();
                // Todo bundle.putParcelable(occurrence);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                fragment.show(fragmentManager, "TAG");
                break;
        }
    }

    static class SymptomViewHolder {
        TextView name_tv, time_tv;
        RelativeLayout base_rl;
        ImageView more_btn;
    }

    static class DateTimeHolder {
        TextView datetime_tv;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return Symptom.getOccurrences().get(position).getType();
    }

    interface ListItem {
        public static final int TYPE_DATE = 0;
        public static final int TYPE_SYMPTOM = 1;

        public int getType();

        public long getTime();
    }
}
