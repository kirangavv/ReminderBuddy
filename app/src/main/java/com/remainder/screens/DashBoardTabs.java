package com.remainder.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.remainder.R;
import com.remainder.common.Constants;
import com.remainder.dao.RemainderDAO;


public class DashBoardTabs extends AppCompatActivity {

    private RemainderDAO dao;
    ReminderList listRemainder;
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_tabs);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("7 Days"));
        tabLayout.addTab(tabLayout.newTab().setText("30 Days"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("DeActivated"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                //calling add reminder page
                Intent addIntent = new Intent(this, AddReminder.class);
                addIntent.putExtra(Constants.MODE, Constants.NEW);
                startActivity(addIntent);
                break;
            case R.id.menu_settings:
                // calling setting page
                Intent settingIntent = new Intent(this, Settings.class);
                startActivity(settingIntent);
                break;
        }

        // Close the database
        dao.close();

        return super.onOptionsItemSelected(item);
    }
}