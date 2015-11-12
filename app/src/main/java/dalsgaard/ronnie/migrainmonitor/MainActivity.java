package dalsgaard.ronnie.migrainmonitor;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SymptomFragment.iSymptomFragmentCallback {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MyFragmentInterface historyFragment;
    private Fragment symptomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Ignored
                //System.out.println("--> MainActivity.OnPageChangeListener.onPageScrolled()");
            }
            @Override
            public void onPageSelected(int position) {
                System.out.println("--> MainActivity.OnPageChangeListener.onPageSelected()");
                switch (position){
                    //case 0: if(symptomFragment != null) symptomFragment.OnFragmentSelected(); break;
                    case 1: if(historyFragment != null) historyFragment.OnFragmentSelected(); break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // Ignored
                System.out.println("--> MainActivity.OnPageChangeListener.onPageScrollStateChanged()");
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSymptomFragmentCallback() {
        System.out.println("--> MainActivity.onSymptomFragmentCallback() : "+(historyFragment == null? "null" : "Hist.Frag. exists"));
        if(historyFragment != null) historyFragment.OnFragmentSelected();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println("--> MainActivity.getItem()");
            symptomFragment = new SymptomFragment();
            historyFragment = new HistoryFragment();
            switch (position){
                case 0: return symptomFragment;
                case 1: return (Fragment)historyFragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Symptoms";
                case 1: return "History";
            }
            return null;
        }
    }


    interface MyFragmentInterface {
        public void OnFragmentSelected();
    }
}
