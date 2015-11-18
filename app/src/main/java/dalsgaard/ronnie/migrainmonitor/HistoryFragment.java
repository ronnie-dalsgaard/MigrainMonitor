package dalsgaard.ronnie.migrainmonitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem;
import dalsgaard.ronnie.migrainmonitor.util.Time;

import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_DATE;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_SYMPTOM;


public class HistoryFragment extends ListFragment {
    private MyListAdapter mAdapter;
    private ListView mListView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyListAdapter(getActivity());
        setListAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int bg_color = ContextCompat.getColor(getActivity(), R.color.Blue);
        view.setBackgroundColor(bg_color);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListItem item = Symptom.getOccurrences().get(position);
        String str = "";
        switch (item.getType()) {
            case TYPE_DATE:
                str = Time.toDateTimeString(((Symptom.Header) item).getTime());
                break;
            case TYPE_SYMPTOM:
                str = ((Symptom.Occurrence) item).getName();
                break;
        }
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).setHistoryFragment(this);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
}
