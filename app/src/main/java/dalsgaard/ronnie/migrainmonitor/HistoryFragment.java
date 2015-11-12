package dalsgaard.ronnie.migrainmonitor;

import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class HistoryFragment extends ListFragment implements MainActivity.MyFragmentInterface {
    private MyListAdapter adapter;

    public HistoryFragment() {
        System.out.println("--> HistoryFragment.Constructor()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("--> HistoryFragment.onCreate()");
        super.onCreate(savedInstanceState);
        adapter = new MyListAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("--> HistoryFragment.onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String str = Symptom.occurences.get(position).getName();
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFragmentSelected() {
        if(adapter != null) adapter.notifyDataSetChanged();
    }
}
