package dalsgaard.ronnie.migrainmonitor;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class OccurenceDetailActivityFragment extends Fragment implements View.OnClickListener {

    public OccurenceDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_occurence_detail, container, false);
        ImageButton delete_btn = (ImageButton) view.findViewById(R.id.fragment_occurrence_detail_delete_btn);
        delete_btn.setOnClickListener(this);
        Toast.makeText(getActivity(), "OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fragment_occurrence_detail_delete_btn:
                Toast.makeText(getActivity(), "DELETE pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                DialogFragment fragment = new OK_Cancel_DialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(OK_Cancel_DialogFragment.KEY_TEXT, "bla bla bla bla");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragment.show(fragmentManager, OK_Cancel_DialogFragment.TAG);
                break;
            case R.id.dialogfragment_ok_btn:
                Toast.makeText(getActivity(), "OK pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
