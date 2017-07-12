package com.example.nefrin.newprojectstartup;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ViewPagerCustomDuration viewPager;
    private TabLayout tabLayout;

    public static void svp(int i) {
        viewPager.setCurrentItem(i, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        SugarDb db = new SugarDb(this);
        db.onCreate(db.getDB());
        SugarContext.init(getApplicationContext());
        viewPager = (ViewPagerCustomDuration) findViewById(R.id.viewpager);
        viewPager.setScrollDurationFactor(3); // make the animation twice as slow
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Questions"));
        tabLayout.addTab(tabLayout.newTab().setText("Ask"));
        tabLayout.addTab(tabLayout.newTab().setText("My questions"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {

        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);

                Log.i("TAG", "onTabSelected: " + tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabUnselected: " + tab.getPosition());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabReselected: " + tab.getPosition());
            }
        });
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast msg = Toast.makeText(this, "About us activity", Toast.LENGTH_LONG);
                msg.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new TabletTransformer());
        viewPager.setOffscreenPageLimit(2);
    }
}