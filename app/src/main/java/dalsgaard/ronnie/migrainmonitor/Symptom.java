package dalsgaard.ronnie.migrainmonitor;

import android.app.LauncherActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import dalsgaard.ronnie.migrainmonitor.util.Time;
import dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_DATE;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_SYMPTOM;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class Symptom {
    public static ArrayList<Symptom> symptoms = new ArrayList();
    public static ArrayList<ListItem> occurrences = new ArrayList();
    private String name;
    private int color;
    static {
        // Prodrome
        symptoms.add(new Symptom("Constipation", 0xDB_70_93));
        symptoms.add(new Symptom("Diarrhea", 0xFF_14_93));
        symptoms.add(new Symptom("Depression", 0x64_95_ED));
        symptoms.add(new Symptom("Food cravings", 0xFF_00_FF));
        symptoms.add(new Symptom("Hyperactivity", 0x00_80_80));
        symptoms.add(new Symptom("Irritability", 0xFF_D7_00));
        symptoms.add(new Symptom("Neck stiffness", 0xFF_8C_00));
        symptoms.add(new Symptom("Uncontrollable yawning", 0x46_82_B4));

        // Aura
        symptoms.add(new Symptom("Visual phenomena", 0x40_E0_D0)); // such as seeing various shapes, bright spots or flashes of light
        symptoms.add(new Symptom("Vision loss", 0xB0_C4_DE));
        symptoms.add(new Symptom("Skin tinkling", 0xFF_63_47)); // Pins and needles
        symptoms.add(new Symptom("Aphasia", 0x9A_CD_32));

        // Attack
        symptoms.add(new Symptom("Mild headace", 0xE0_66_FF));
        symptoms.add(new Symptom("Severe headace", 0xDD_A0_DD));
        symptoms.add(new Symptom("Photophobia", 0x48_76_FF)); // Sensitivity to light
        symptoms.add(new Symptom("Hyperosmia", 0x22_8B_22)); // Sensitivity to smells
        symptoms.add(new Symptom("Nausea", 0x94_00_D3));
        symptoms.add(new Symptom("Vomiting", 0xCD_CD_00));
        symptoms.add(new Symptom("Blurred vision", 0xDC_14_3C));

        symptoms.get(2).newSymptomOccurence(System.currentTimeMillis() - 2*24*60*60*1000);
        symptoms.get(2).newSymptomOccurence(System.currentTimeMillis() - 2*24*60*60*1000);
        symptoms.get(2).newSymptomOccurence(System.currentTimeMillis() - 2*24*60*60*1000);
        symptoms.get(2).newSymptomOccurence(System.currentTimeMillis() - 2*24*60*60*1000);
        symptoms.get(2).newSymptomOccurence(System.currentTimeMillis() - 2*24*60*60*1000);
    }

    public Symptom(String name, int color){
        this.name = name;
        this.color = color;
    }

    public final Occurrence newSymptomOccurence(){
        return newSymptomOccurence(System.currentTimeMillis());
    }
    public final Occurrence newSymptomOccurence(long datetime){
        Occurrence occurrence = new Occurrence(name, color, datetime);
        int index = occurrences.size();
        if(index == 0){
            occurrences.add(new DateItem(occurrence.getTime()));
        } else if(index > 0){
            Occurrence previous = (Occurrence) occurrences.get(index -1);
            if(! Time.sameDay(occurrence.getTime(), previous.getTime())){
                occurrences.add(new DateItem(occurrence.getTime()));
            }
        }
        occurrences.add(occurrence);
        return occurrence;
    }

    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }

    public static void cleanOccurences(){
        // Sort list
        Collections.sort(occurrences, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem lhs, ListItem rhs) {
                if(Time.sameDay(lhs.getTime(), rhs.getTime())){
                    if(lhs.getType() == TYPE_DATE && rhs.getType() == TYPE_DATE) return 0; // Shouldn't happen
                    else if(lhs.getType() == TYPE_DATE && rhs.getType() == TYPE_SYMPTOM) return -1;
                    else if(lhs.getType() == TYPE_SYMPTOM && rhs.getType() == TYPE_DATE) return +1;
                    else { // lhs.getType() == TYPE_SYMPTOM && rhs.getType() == TYPE_SYMPTOM
                        Occurrence _lhs = (Occurrence)lhs;
                        Occurrence _rhs = (Occurrence)rhs;
                        return _lhs.getName().compareTo(_rhs.getName());
                    }
                }
                return lhs.getTime() - rhs.getTime() < 0 ? -1 : +1;
            }
        });

        // Add missing headers
        for(int i = occurrences.size()-1; i > 0; i--){
            ListItem current = occurrences.get(i);
            ListItem previous = occurrences.get(i-1);

        }
    }

    public class Occurrence extends Symptom implements ListItem, Comparable<Occurrence> {
        private long time;

        public Occurrence(String name, int color) {
            this(name, color, System.currentTimeMillis());
        }

        public Occurrence(String name, int color, long time){
            super(name, color);
            this.time = time;
        }

        @Override
        public long getTime(){
            return time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Occurrence that = (Occurrence) o;

            if (!this.getName().equals(that.getName())) return false;
            return Time.sameDay(this.time, that.time);
        }

        @Override
        public String toString() {
            String name = super.getName();
            int r = (super.getColor() & 0xFF_00_00) >> 16;
            int g = (super.getColor() & 0x00_FF_00) >> 8;
            int b = (super.getColor() & 0x00_00_FF) >> 0;
            String _r = Integer.toHexString(r); if(_r.length()==1) _r = "0"+_r;
            String _g = Integer.toHexString(g); if(_g.length()==1) _g = "0"+_g;
            String _b = Integer.toHexString(b); if(_b.length()==1) _b = "0"+_b;
            String color = String.format("0x %s %s %s", _r, _g, _b);
            String time = Time.toDateTimeString(this.time);
            return "Occurrence{\n" +
                        "\tName=" + name + "\n" +
                        "\tColor=" + color + "\n" +
                        "\ttime=" + time + "\n" +
                    '}';
        }

        @Override
        public int getType() {
            return TYPE_SYMPTOM;
        }

        @Override
        public int compareTo(Occurrence that) {
            if(Time.sameDay(this.getTime(), that.getTime())){
                return this.getName().compareTo(that.getName());
            }
            return this.getTime() - that.getTime() < 0 ? -1 : +1;
        }
    }

    public class DateItem implements ListItem {
        private long time;

        public DateItem(long time){
            this.time = time;
        }

        @Override
        public long getTime(){
            return time;
        }

        @Override
        public int getType() {
            return TYPE_DATE;
        }
    }
}
