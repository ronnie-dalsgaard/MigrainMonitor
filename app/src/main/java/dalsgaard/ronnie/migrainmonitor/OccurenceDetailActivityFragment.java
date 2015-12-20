package dalsgaard.ronnie.migrainmonitor;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class OccurenceDetailActivityFragment extends Fragment implements View.OnClickListener {
    private Symptom.Occurrence mOccurrence;

    public OccurenceDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fetch occurrence
        Bundle bundle = getArguments();
        String occurrence_id = bundle.getString(OccurenceDetailActivity.KEY_OCCURRENCEID);
        mOccurrence = Symptom.getOccurrence(occurrence_id);

        // Collect the views
        View view = inflater.inflate(R.layout.fragment_occurence_detail, container, false);
        TextView name_tv = (TextView) view.findViewById(R.id.fragment_occurrence_detail_name_tv);
        TextView time_tv = (TextView) view.findViewById(R.id.fragment_occurrence_detail_time_tv);
        TextView count_tv = (TextView) view.findViewById(R.id.fragment_occurrence_detail_count_tv);
        LinearLayout header = (LinearLayout) view.findViewById(R.id.fragment_occurrence_detail_header);

        // Set Texts
        name_tv.setText(mOccurrence.getName());
        time_tv.setText(dalsgaard.ronnie.migrainmonitor.util.Time.toDateString(mOccurrence.getTime()));
        header.setBackgroundColor(0xFF000000 + mOccurrence.getColor()); // Add alpha

        // Collect buttons
        ImageButton minus_btn = (ImageButton) view.findViewById(R.id.fragment_occurrence_detail_minus_btn);
        ImageButton plus_btn = (ImageButton) view.findViewById(R.id.fragment_occurrence_detail_plus_btn);
        ImageButton delete_btn = (ImageButton) view.findViewById(R.id.fragment_occurrence_detail_delete_btn);
        Button save_btn = (Button) view.findViewById(R.id.fragment_occurrence_detail_save_btn);

        // Set listeners
        minus_btn.setOnClickListener(this);
        plus_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);

        //
        minus_btn.setTag(count_tv);
        plus_btn.setTag(count_tv);
        return view;
    }

    @Override
    public void onClick(View v) {
        int count;
        TextView count_tv;
        switch(v.getId()){
            case R.id.fragment_occurrence_detail_minus_btn:
                count = mOccurrence.getCount();
                count--;
                if(count <= 0) count = 1;
                mOccurrence.setCount(count);
                count_tv = (TextView) v.getTag();
                count_tv.setText(""+count);
                break;
            case R.id.fragment_occurrence_detail_plus_btn:
                count = mOccurrence.getCount();
                count++;
                mOccurrence.setCount(count);
                count_tv = (TextView) v.getTag();
                count_tv.setText(""+count);
                break;
            case R.id.fragment_occurrence_detail_delete_btn:
                Toast.makeText(getActivity(), "DELETE pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogfragment_ok_cancel);

                TextView text_tv = (TextView) dialog.findViewById(R.id.dialogfragment_ok_cancel_text);
                String text = "Delete symptom\n"+mOccurrence.getName();
                text_tv.setText(text);

                Button ok_btn = (Button) dialog.findViewById(R.id.dialogfragment_ok_btn);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Ok - DELETE pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                Button cancel_btn = (Button) dialog.findViewById(R.id.dialogfragment_cancel_btn);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Cancel - DELETE pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();

//                OK_Cancel_DialogFragment fragment = new OK_Cancel_DialogFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(OK_Cancel_DialogFragment.KEY_TEXT, "Delete symptom\n"+mOccurrence.getName());
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = getActivity().getFragmentManager();
//                fragment.show(fragmentManager, OK_Cancel_DialogFragment.TAG);
                break;
            case R.id.fragment_occurrence_detail_save_btn:
                Toast.makeText(getActivity(), "Save pressed in OccurenceDetailActivityFragment", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
