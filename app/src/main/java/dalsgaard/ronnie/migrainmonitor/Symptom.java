package dalsgaard.ronnie.migrainmonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dalsgaard.ronnie.migrainmonitor.util.Time;
import dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_DATE;
import static dalsgaard.ronnie.migrainmonitor.MyListAdapter.ListItem.TYPE_SYMPTOM;

/**
 * Created by Ronnie D on 07-11-2015.
 */
public class Symptom {
    public static ArrayList<Symptom> symptomList = new ArrayList<>();
    private static ArrayList<ListItem> occurrenceList = new ArrayList<>();
    private String name;
    private int color;
    static {
        // Prodrome
        symptomList.add(new Symptom("Constipation", 0xDB_70_93)); // 0
        symptomList.add(new Symptom("Diarrhea", 0xFF_14_93)); // 1
        symptomList.add(new Symptom("Depression", 0x64_95_ED)); // 2
        symptomList.add(new Symptom("Food cravings", 0xFF_00_FF)); // 3
        symptomList.add(new Symptom("Hyperactivity", 0x00_80_80)); // 4
        symptomList.add(new Symptom("Irritability", 0xDF_B7_00)); // 5
        symptomList.add(new Symptom("Neck stiffness", 0xFF_8C_00)); // 6
        symptomList.add(new Symptom("Uncontrollable yawning", 0x46_82_B4)); // 7

        // Aura
        symptomList.add(new Symptom("Visual phenomena", 0x40_E0_D0)); // such as seeing various shapes, bright spots or flashes of light
        symptomList.add(new Symptom("Vision loss", 0xB0_C4_DE)); // 9
        symptomList.add(new Symptom("Skin tinkling", 0xFF_63_47)); // Pins and needles
        symptomList.add(new Symptom("Aphasia", 0x9A_CD_32)); // 11

        // Attack
        symptomList.add(new Symptom("Mild headace", 0xE0_66_FF)); // 12
        symptomList.add(new Symptom("Severe headace", 0xDD_A0_DD)); // 13
        symptomList.add(new Symptom("Photophobia", 0x48_76_FF)); // Sensitivity to light
        symptomList.add(new Symptom("Hyperosmia", 0x22_8B_22)); // Sensitivity to smells
        symptomList.add(new Symptom("Nausea", 0x94_00_D3)); // 16
        symptomList.add(new Symptom("Vomiting", 0xCD_CD_00)); // 17
        symptomList.add(new Symptom("Blurred vision", 0xDC_14_3C)); // 18
    }
    static { // TODO Debug
        addOccurrence(symptomList.get(12).createSymptomOccurence(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000)); // Mild headache
        addOccurrence(symptomList.get(06).createSymptomOccurence(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000)); // Neck stiffness
        addOccurrence(symptomList.get(02).createSymptomOccurence(System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000)); // Depression
        addOccurrence(symptomList.get(03).createSymptomOccurence(System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000)); // Food cravings
        addOccurrence(symptomList.get(14).createSymptomOccurence(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000));  // Photophobia

        cleanUpOccurences();
    }

    public Symptom(String name, int color){
        this.name = name;
        this.color = color;
    }

    public final Occurrence createSymptomOccurence(){
        return createSymptomOccurence(System.currentTimeMillis());
    }
    public final Occurrence createSymptomOccurence(long datetime){
        Occurrence occurrence = new Occurrence(name, color, datetime);
        return occurrence;
    }

    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }

    public static final ArrayList<ListItem> getOccurrences(){
        return occurrenceList;
    }
    public static final void addOccurrence(long time, Occurrence... occurrences){
        // Handle invalid input
        if(occurrences == null || occurrences.length == 0) throw new IllegalArgumentException();

        // Align occurrences
        for(Occurrence occurrence : occurrences) occurrence.setTime(time);

        // Add header
        occurrenceList.add(0, new Header(time));

        // Add all occurrenceList
        for(Occurrence occurrence : occurrences){
            occurrenceList.add(1, occurrence);
        }
    }
    public static final void addOccurrence(Occurrence... occurrences){
        // Handle invalid input
        if(occurrences == null || occurrences.length == 0) throw new IllegalArgumentException();

        // Align occurences
        long time = occurrences[0].getTime();

        addOccurrence(time, occurrences);
    }
    public static void cleanUpOccurences(){
        // Sort list
        Collections.sort(occurrenceList, new Comparator<ListItem>() {
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
                return lhs.getTime() - rhs.getTime() < 0 ? +1 : -1;
            }
        });

        // Clean-up
        for(int i = occurrenceList.size()-1; i > 0; i--){
            ListItem current = occurrenceList.get(i);
            ListItem previous = occurrenceList.get(i-1);

            // Add Date
            if(current.getType() == TYPE_SYMPTOM && previous.getType() == TYPE_SYMPTOM
                    && !Time.sameDay(current.getTime(), previous.getTime())){
                Header dt = new Header(current.getTime());
                occurrenceList.add(i, dt); // Pushback ramaining items
            }

            // Remove identical Headers
            if(current.getType() == TYPE_DATE && previous.getType() == TYPE_DATE){
                occurrenceList.remove(current);
            }

            // Remove identical Symptoms
            if(current.getType() == TYPE_SYMPTOM && previous.getType() == TYPE_SYMPTOM){
                Occurrence _current = (Occurrence) current;
                Occurrence _previous = (Occurrence) previous;
                // Two symptomList next to each other must have the same date.
                if(_current.getName().equals(_previous.getName())){
                    occurrenceList.remove(current);
                }
            }
        }
    }

    public static class Occurrence extends Symptom implements ListItem, Comparable<Occurrence> {
        private long time;

        public Occurrence(String name, int color) {
            this(name, color, System.currentTimeMillis());
        }

        public Occurrence(String name, int color, long time){
            super(name, color);
            this.time = time;
        }
        public Occurrence(Occurrence original){
            this(original.getName(), original.getColor(), original.getTime());
        }

        @Override
        public long getTime(){
            return time;
        }

        public void setTime(long time){
            this.time = time;
        }

        @Override
        public int getType() {
            return TYPE_SYMPTOM;
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
        public int compareTo(Occurrence that) {
            if(Time.sameDay(this.getTime(), that.getTime())){
                return this.getName().compareTo(that.getName());
            }
            return this.getTime() - that.getTime() < 0 ? -1 : +1;
        }
    }

    public static class Header implements ListItem {
        private long time;

        public Header(long time){
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
