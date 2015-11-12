package dalsgaard.ronnie.migrainmonitor;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class SymptomFragment extends Fragment {


    public SymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symptom, container, false);
        LinearLayout col1 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col1);
        LinearLayout col2 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col2);
        LinearLayout col3 = (LinearLayout)view.findViewById(R.id.fragment_symptom_col3);

        for(int i = 0; i < Symptom.symptoms.size(); i++){
            final Symptom symptom = Symptom.symptoms.get(i);
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
                    Symptom.Occurence occurence = symptom.newSymptomOccurence();
                    Symptom.occurences.add(occurence);
                    String str = occurence.toString();
                    // Toast.makeText(SymptomFragment.this.getActivity(), str, Toast.LENGTH_SHORT).show();
                    ((iSymptomFragmentCallback) getActivity()).onSymptomFragmentCallback();
                }
            });
            btn.setBackgroundColor(0xff000000 + symptom.getColor()); // add alpha to color
            btn.setHeight(125);
            // Width are controlled by weight of layout(column)

            col.addView(btn);
        }

        // Save Button
        Button btn = (Button)view.findViewById(R.id.fragment_symptom_btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                for (Symptom.Occurence o : Symptom.occurences) {
                    str += o.getName() + "\n";
                }
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    interface iSymptomFragmentCallback {
        public void onSymptomFragmentCallback();
    }
}
