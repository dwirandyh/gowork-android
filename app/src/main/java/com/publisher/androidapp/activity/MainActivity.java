package com.publisher.androidapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.adapter.ViewPagerAdapter;
import com.publisher.androidapp.fragment.BeritaFragment;
import com.publisher.androidapp.fragment.ForumFragment;
import com.publisher.androidapp.fragment.LokerFragment;
import com.publisher.androidapp.fragment.PelatihanFragment;
import com.publisher.androidapp.model.Relawan;
import com.publisher.androidapp.model.TunaKarya;
import com.publisher.androidapp.service.RelawanService;
import com.publisher.androidapp.service.TunaKaryaService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.GlideApp;
import com.publisher.androidapp.utils.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    View headerView;

    TextView textViewNama, textViewEmail;
    ImageView imageViewFoto;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        textViewNama = headerView.findViewById(R.id.textview_nama);
        textViewEmail = headerView.findViewById(R.id.textview_email);
        imageViewFoto = headerView.findViewById(R.id.imageview_foto);
        navigationView.setNavigationItemSelectedListener(this);


        setupProfile();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = findViewById(R.id.tabs);
        setupTabLayout();
    }

    //region FUNCTION
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BeritaFragment(), "");
        adapter.addFragment(new PelatihanFragment(), "");
        adapter.addFragment(new LokerFragment(), "");
        adapter.addFragment(new ForumFragment(), "");
        viewPager.setAdapter(adapter);
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        if (tabLayout.getTabCount() > 0) {
            tabLayout.getTabAt(0).setIcon(R.drawable.berita);
            tabLayout.getTabAt(1).setIcon(R.drawable.pelatihan);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_job);
            tabLayout.getTabAt(3).setIcon(R.drawable.question_answer);
        }
    }

    private void setupProfile() {
        int id = Prefs.getInt(Constant.STR_ID, 0);

        if (Prefs.getString(Constant.STR_HAK_AKSES, "").equals(Constant.HAK_AKSES_TUNA_KARYA)) {
            TunaKaryaService service = Injector.tunaKaryaService();
            service.getDetail(id).enqueue(new Callback<TunaKarya>() {
                @Override
                public void onResponse(Call<TunaKarya> call, Response<TunaKarya> response) {
                    TunaKarya item = response.body();
                    if (item != null) {
                        textViewNama.setText(item.getNama());
                        textViewEmail.setText(item.getEmail());

                        GlideApp.with(context)
                                .load(item.getFoto())
                                .dontTransform()
                                .placeholder(R.drawable.placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageViewFoto);
                    }
                }

                @Override
                public void onFailure(Call<TunaKarya> call, Throwable t) {

                }
            });
        } else {
            RelawanService service = Injector.relawanService();
            service.getDetail(id).enqueue(new Callback<Relawan>() {
                @Override
                public void onResponse(Call<Relawan> call, Response<Relawan> response) {
                    Relawan item = response.body();
                    if (item != null) {
                        textViewNama.setText(item.getNama());
                        textViewEmail.setText(item.getEmail());

                        GlideApp.with(context)
                                .load(item.getFoto())
                                .dontTransform()
                                .placeholder(R.drawable.placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageViewFoto);
                    }
                }

                @Override
                public void onFailure(Call<Relawan> call, Throwable t) {

                }
            });
        }
    }

    private void logout() {
        Prefs.remove(Constant.STR_ID);
        Prefs.remove(Constant.STR_HAK_AKSES);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //endregion

    //region OVERRIDE FUNCTION
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_berita:
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_pelatihan:
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_loker:
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_qa:
                viewPager.setCurrentItem(3);
                break;
            case R.id.nav_jadwal:
                Intent intent = new Intent(context, JadwalPelatihanActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    boolean doubleBackToExitPressedOnce = false;

    //endregion
}
