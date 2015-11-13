package dalsgaard.ronnie.migrainmonitor;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private HistoryFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    // Menu
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

    public void setHistoryFragment(HistoryFragment historyFragment){
        mHistoryFragment = historyFragment;
        System.out.println("--> MainActivity.setHistoryFragment()");
    }

    public void onOccurrenceAdded(Symptom.Occurrence occurrence) {
        System.out.println("--> MainActivity.onSymptomFragmentCallback() : "+(mHistoryFragment == null? "null" : "Hist.Frag. exists"));
        if(mHistoryFragment != null) mHistoryFragment.notifyDataSetChanged();
    }

    // OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Ignored
        //System.out.println("--> MainActivity.OnPageChangeListener.onPageScrolled()");
    }
    @Override
    public void onPageSelected(int position) {
        System.out.println("--> MainActivity.OnPageChangeListener.onPageSelected()");
        mHistoryFragment.notifyDataSetChanged();
    }
    @Override
    public void onPageScrollStateChanged(int state) {
        // Ignored
        System.out.println("--> MainActivity.OnPageChangeListener.onPageScrollStateChanged()");
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new SymptomFragment();
                case 1: return new HistoryFragment();
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
}
