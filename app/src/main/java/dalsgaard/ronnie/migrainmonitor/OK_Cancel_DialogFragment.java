package dalsgaard.ronnie.migrainmonitor;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ronnie D on 20-11-2015.
 */
public class OK_Cancel_DialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "OKCANCELDIALOG_TAG";
    public static final String KEY_TEXT = "keytext";
    private Dialog mDialog;

    public OK_Cancel_DialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity());
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialogfragment_ok_cancel);
        String text = getArguments().getString(KEY_TEXT);

        TextView text_tv = (TextView) mDialog.findViewById(R.id.dialogfragment_ok_cancel_text);
        Button ok_btn = (Button) mDialog.findViewById(R.id.dialogfragment_ok_btn);
        Button cancel_btn = (Button) mDialog.findViewById(R.id.dialogfragment_cancel_btn);

        text_tv.setText(text);
//        ok_btn.setOnClickListener(this);
//        cancel_btn.setOnClickListener(this);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.dialogfragment_ok_btn:
//                Toast.makeText(getActivity(), "ok clicked!", Toast.LENGTH_SHORT).show();
//                mDialog.cancel();
//                break;
//            case R.id.dialogfragment_cancel_btn:
//                // Toast.makeText(getActivity(), "cancel clicked!", Toast.LENGTH_SHORT).show();
//                // if(mClickListener != null) mClickListener.onClick(v);
//                mDialog.cancel();
//                break;
//        }
    }
}
