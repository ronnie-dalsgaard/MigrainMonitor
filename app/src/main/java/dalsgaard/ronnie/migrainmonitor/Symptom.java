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
    public static ArrayList<Symptom> mSymptomList = new ArrayList<>();
    private static ArrayList<ListItem> mOccurrenceList = new ArrayList<>();
    private String mName;
    private int mColor;
    static {
        // Prodrome
        mSymptomList.add(new Symptom("Constipation", 0xDB_70_93)); // 0
        mSymptomList.add(new Symptom("Diarrhea", 0xFF_14_93)); // 1
        mSymptomList.add(new Symptom("Depression", 0x64_95_ED)); // 2
        mSymptomList.add(new Symptom("Food cravings", 0xFF_00_FF)); // 3
        mSymptomList.add(new Symptom("Hyperactivity", 0x00_80_80)); // 4
        mSymptomList.add(new Symptom("Irritability", 0xDF_B7_00)); // 5
        mSymptomList.add(new Symptom("Neck stiffness", 0xFF_8C_00)); // 6
        mSymptomList.add(new Symptom("Uncontrollable yawning", 0x46_82_B4)); // 7

        // Aura
        mSymptomList.add(new Symptom("Visual phenomena", 0x40_E0_D0)); // such as seeing various shapes, bright spots or flashes of light
        mSymptomList.add(new Symptom("Vision loss", 0xB0_C4_DE)); // 9
        mSymptomList.add(new Symptom("Skin tinkling", 0xFF_63_47)); // Pins and needles
        mSymptomList.add(new Symptom("Aphasia", 0x9A_CD_32)); // 11

        // Attack
        mSymptomList.add(new Symptom("Mild headace", 0xE0_66_FF)); // 12
        mSymptomList.add(new Symptom("Severe headace", 0xDD_A0_DD)); // 13
        mSymptomList.add(new Symptom("Photophobia", 0x48_76_FF)); // Sensitivity to light
        mSymptomList.add(new Symptom("Hyperosmia", 0x22_8B_22)); // Sensitivity to smells
        mSymptomList.add(new Symptom("Nausea", 0x94_00_D3)); // 16
        mSymptomList.add(new Symptom("Vomiting", 0xCD_CD_00)); // 17
        mSymptomList.add(new Symptom("Blurred vision", 0xDC_14_3C)); // 18
    }
    static { // TODO Debug
        addOccurrence(mSymptomList.get(12).createSymptomOccurence(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000)); // Mild headache
        addOccurrence(mSymptomList.get(06).createSymptomOccurence(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000)); // Neck stiffness
        addOccurrence(mSymptomList.get(02).createSymptomOccurence(System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000)); // Depression
        addOccurrence(mSymptomList.get(03).createSymptomOccurence(System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000)); // Food cravings
        addOccurrence(mSymptomList.get(14).createSymptomOccurence(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000));  // Photophobia

        cleanUpOccurences();
    }

    public Symptom(String name, int color){
        mName = name;
        mColor = color;
    }

    public final Occurrence createSymptomOccurence(){
        return createSymptomOccurence(System.currentTimeMillis());
    }
    public final Occurrence createSymptomOccurence(long datetime){
        Occurrence occurrence = new Occurrence(mName, mColor, datetime);
        return occurrence;
    }

    public String getName() {
        return mName;
    }
    public int getColor() {
        return mColor;
    }

    public static final ArrayList<ListItem> getOccurrences(){
        return mOccurrenceList;
    }
    public static final void addOccurrence(long time, Occurrence... occurrences){
        // Handle invalid input
        if(occurrences == null || occurrences.length == 0) throw new IllegalArgumentException();

        // Align occurrences
        for(Occurrence occurrence : occurrences) occurrence.setTime(time);

        // Add header
        mOccurrenceList.add(0, new Header(time));

        // Add all occurrences
        for(Occurrence occurrence : occurrences){
            mOccurrenceList.add(1, occurrence);
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
        Collections.sort(mOccurrenceList, new Comparator<ListItem>() {
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
        for(int i = mOccurrenceList.size()-1; i > 0; i--){
            ListItem current = mOccurrenceList.get(i);
            ListItem previous = mOccurrenceList.get(i-1);

            // Add Date
            if(current.getType() == TYPE_SYMPTOM && previous.getType() == TYPE_SYMPTOM
                    && !Time.sameDay(current.getTime(), previous.getTime())){
                Header dt = new Header(current.getTime());
                mOccurrenceList.add(i, dt); // Pushback ramaining items
            }

            // Remove identical Headers
            if(current.getType() == TYPE_DATE && previous.getType() == TYPE_DATE){
                mOccurrenceList.remove(current);
            }

            // Remove identical Symptoms
            if(current.getType() == TYPE_SYMPTOM && previous.getType() == TYPE_SYMPTOM){
                Occurrence _current = (Occurrence) current;
                Occurrence _previous = (Occurrence) previous;
                // Two symptoms in the same block must have the same date.
                if(_current.getName().equals(_previous.getName())){
                    mOccurrenceList.remove(current);
                }
            }
        }
    }

    public static class Occurrence extends Symptom implements ListItem, Comparable<Occurrence> {
        private long mTime;

        public Occurrence(String name, int color) {
            this(name, color, System.currentTimeMillis());
        }
        public Occurrence(String name, int color, long time){
            super(name, color);
            mTime = time;
        }
        public Occurrence(Occurrence original){
            this(original.getName(), original.getColor(), original.getTime());
        }
        public void setTime(long time){
            this.mTime = time;
        }
        @Override
        public long getTime(){
            return mTime;
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
            return Time.sameDay(this.mTime, that.mTime);
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
            String time = Time.toDateTimeString(this.mTime);
            return "Occurrence{\n" +
                        "\tName=" + name + "\n" +
                        "\tColor=" + color + "\n" +
                        "\tTime=" + time + "\n" +
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
        private long mTime;

        public Header(long time){
            mTime = time;
        }
        @Override
        public long getTime(){
            return mTime;
        }
        @Override
        public int getType() {
            return TYPE_DATE;
        }
    }
}
