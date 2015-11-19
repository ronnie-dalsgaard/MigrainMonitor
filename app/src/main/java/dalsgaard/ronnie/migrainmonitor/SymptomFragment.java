package dalsgaard.ronnie.migrainmonitor;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class SymptomFragment extends Fragment {
    private ArrayList<Symptom.Occurrence> mSelected = new ArrayList<>();
    private ArrayList<Button> mButtons = new ArrayList<>();
    private View mScroller;

    public SymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symptom, container, false);
        mScroller = view.findViewById(R.id.framgment_symptom_scroller);
        LinearLayout col1 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col1);
        LinearLayout col2 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col2);
        LinearLayout col3 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col3);

        for(int i = 0; i < Symptom.mSymptomList.size(); i++){
            final Symptom symptom = Symptom.mSymptomList.get(i);
            LinearLayout col;
            switch(i%3){
                case 0: col = col1; break;
                case 1: col = col2; break;
                case 2: col = col3; break;
                default: col = col1;
            }

            Button btn = new Button(getContext());
            btn.setText(symptom.getName());
            btn.setTag(new Integer(i));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Symptom.Occurrence occurrence = symptom.createSymptomOccurence();
                    String str = occurrence.toString();
                    if (mSelected.contains(occurrence)) {
                        mSelected.remove(occurrence);
                        v.setAlpha(1.0f);
                    } else {
                        mSelected.add(occurrence);
                        v.setAlpha(0.25f);
                    }
                }
            });
            btn.setBackgroundColor(0xff000000 + symptom.getColor()); // add alpha to color
            btn.setTextColor(ContextCompat.getColor(getActivity(), R.color.WhiteSmoke));
            btn.setHeight(125);
            // Width are controlled by weight of layout(column)
            mButtons.add(btn);
            col.addView(btn);
        }

        // Save Button
        final Button btn = (Button)view.findViewById(R.id.fragment_symptom_btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fix format
                Collections.sort(mSelected);
                Symptom.Occurrence[] occurrences = mSelected.toArray(new Symptom.Occurrence[mSelected.size()]);

                // Add occurences
                long time = System.currentTimeMillis();
                Symptom.addOccurrence(time, occurrences);

                // Diplay message
                String msg = mSelected.size() + " symptoms added.";
                if(mScroller != null) Snackbar.make(mScroller, msg, Snackbar.LENGTH_LONG).show();
                else Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                // Clean up
                mSelected.clear();
                for(Button btn : mButtons) {
                    btn.setAlpha(1.0f);
                }

                ((MainActivity) getActivity()).onOccurrenceAdded(occurrences);
            }
        });
        return view;
    }
}
