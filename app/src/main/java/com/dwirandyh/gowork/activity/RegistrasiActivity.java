package com.dwirandyh.gowork.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.dwirandyh.gowork.R;
import com.dwirandyh.gowork.adapter.ViewPagerAdapter;
import com.dwirandyh.gowork.fragment.RegistrasiRelawanFragment;
import com.dwirandyh.gowork.fragment.RegistrasiTunaKaryaFragment;

public class RegistrasiActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registrasi");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = findViewById(R.id.tabs);
        setupTabLayout();
    }

    //region FUNCTION
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegistrasiTunaKaryaFragment(), "Tuna Karya");
        adapter.addFragment(new RegistrasiRelawanFragment(), "Relawan");
        viewPager.setAdapter(adapter);
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
