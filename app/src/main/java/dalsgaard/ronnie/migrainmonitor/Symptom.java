package dalsgaard.ronnie.migrainmonitor;

import java.util.ArrayList;
import java.util.LinkedList;

import dalsgaard.ronnie.migrainmonitor.util.Time;


/**
 * Created by Ronnie D on 07-11-2015.
 */
public class Symptom {
    public static ArrayList<Symptom> symptoms = new ArrayList();
    public static ArrayList<Occurence> occurences = new ArrayList();
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
        symptoms.add(new Symptom("Aphasia", 0x30_3F_9F));

        // Attack
        symptoms.add(new Symptom("Mild headace", 0xE0_66_FF));
        symptoms.add(new Symptom("Severe headace", 0xDD_A0_DD));
        symptoms.add(new Symptom("Photophobia", 0x48_76_FF)); // Sensitivity to light
        symptoms.add(new Symptom("Hyperosmia", 0x22_8B_22)); // Sensitivity to smells
        symptoms.add(new Symptom("Nausea", 0x94_00_D3));
        symptoms.add(new Symptom("Vomiting", 0xCD_CD_00));
        symptoms.add(new Symptom("Blurred vision", 0xDC_14_3C));
    }

    public Symptom(String name, int color){
        this.name = name;
        this.color = color;
    }

    public final Occurence newSymptomOccurence(){
        return new Occurence(name, color);
    }

    public String getName() {
        return name;
    }
    public int getColor() {
        return color;
    }


    public class Occurence extends Symptom {
        private long time;

        public Occurence(String name, int color) {
            super(name, color);
            time = System.currentTimeMillis();
        }

        public long getTime(){
            return time;
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
            return "Occurence{\n" +
                        "\tName=" + name + "\n" +
                        "\tColor=" + color + "\n" +
                        "\ttime=" + time + "\n" +
                    '}';
        }
    }
}
