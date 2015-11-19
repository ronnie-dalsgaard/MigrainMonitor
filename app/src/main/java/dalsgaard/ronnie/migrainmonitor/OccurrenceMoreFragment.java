package dalsgaard.ronnie.migrainmonitor;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class OccurrenceMoreFragment extends DialogFragment implements View.OnClickListener {

    public OccurrenceMoreFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_occurrence_more);
        TextView tv = (TextView) dialog.findViewById(R.id.fragment_occurrence_more_tv);
        tv.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {

    }
}
