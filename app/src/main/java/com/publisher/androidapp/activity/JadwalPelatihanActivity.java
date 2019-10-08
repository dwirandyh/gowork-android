package com.publisher.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.adapter.PelatihanCalendarAdapter;
import com.publisher.androidapp.model.Pelatihan;
import com.publisher.androidapp.model.PelatihanEvent;
import com.publisher.androidapp.service.PelatihanService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.DrawableUtils;
import com.publisher.androidapp.utils.Injector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalPelatihanActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private CalendarView calendarView;
    private RecyclerView recyclerView;

    private List<Pelatihan> itemList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private PelatihanCalendarAdapter adapter;

    TextView textViewBulan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_jadwal_pelatihan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Jadwal Pelatihan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textViewBulan = (TextView) findViewById(R.id.textview_bulan);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Mengambil data ke server"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        getJadwal();
    }

    private void getJadwal() {

        int id = Prefs.getInt(Constant.STR_ID, 0);
        if (Prefs.getString(Constant.STR_HAK_AKSES, "").equals("Tuna Karya")){
            PelatihanService service = Injector.pelatihanService();
            service.jadwalPelatihanTunaKarya(id).enqueue(new Callback<List<Pelatihan>>() {
                @Override
                public void onResponse(Call<List<Pelatihan>> call, Response<List<Pelatihan>> response) {
                    itemList = response.body();

                    setupEvents();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<Pelatihan>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }else{
            PelatihanService service = Injector.pelatihanService();
            service.jadwalPelatihanRelawan(id).enqueue(new Callback<List<Pelatihan>>() {
                @Override
                public void onResponse(Call<List<Pelatihan>> call, Response<List<Pelatihan>> response) {
                    itemList = response.body();

                    setupEvents();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<Pelatihan>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void setupEvents() {
        List<EventDay> events = new ArrayList<>();

        for (Pelatihan item : itemList) {
            String[] tanggal = item.getTanggalPelatihanRaw().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(tanggal[0]), Integer.parseInt(tanggal[1]) -1, Integer.parseInt(tanggal[2]));
            PelatihanEvent event = new PelatihanEvent(calendar, DrawableUtils.getCircleDrawableWithText(this, "P"));
            events.add(event);
        }

        /*
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 9, 11);
        PelatihanEvent event = new PelatihanEvent(calendar, DrawableUtils.getCircleDrawableWithText(this, "P"));
        calendar.set(2018, 9, 11);
        PelatihanEvent event2 = new PelatihanEvent(calendar, DrawableUtils.getCircleDrawableWithText(this, "P"));
        events.add(event);
        */


        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if (eventDay instanceof PelatihanEvent) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String tanggal = dateFormat.format(eventDay.getCalendar().getTime());
                    showEventList(tanggal);

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM");
                    String bulan = dateFormat1.format(eventDay.getCalendar().getTime());
                    textViewBulan.setText(bulan);
                }
            }
        });
    }

    private void showEventList(String tanggal){
        List<Object> pelatihanList = new ArrayList<>();
        for (Pelatihan item : itemList){
            if (item.getTanggalPelatihanRaw().equals(tanggal)){
                pelatihanList.add(item);
            }
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        adapter = new PelatihanCalendarAdapter(context, pelatihanList);
        recyclerView.setAdapter(adapter);
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
