package com.dwirandyh.gowork.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.dwirandyh.gowork.R;
import com.dwirandyh.gowork.model.Loker;
import com.dwirandyh.gowork.service.LokerService;
import com.dwirandyh.gowork.utils.Constant;
import com.dwirandyh.gowork.utils.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLokerActivity extends AppCompatActivity {

    TextView textViewPerusahaan, textViewRentangGaji;
    WebView webViewDeskripsi;
    private Toolbar toolbar;
    Button btnLokasi;
    private ProgressDialog progressDialog;

    int id = 0;
    private String latitude = "", longitude = "";
    private String perusahaan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loker);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Lowongan Kerja");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textViewPerusahaan = findViewById(R.id.textview_perusahaan);
        textViewRentangGaji = findViewById(R.id.textview_rentanggaji);
        btnLokasi = findViewById(R.id.btn_lokasi);
        webViewDeskripsi = findViewById(R.id.webview_deskripsi);
        webViewDeskripsi.setBackgroundColor(Color.TRANSPARENT);

        id = getIntent().getIntExtra(Constant.STR_ID, 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Mengambil data ke server"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        getDetail();

        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMap();
            }
        });
    }

    private void getDetail() {
        LokerService service = Injector.lokerService();
        service.getDetail(id).enqueue(new Callback<Loker>() {
            @Override
            public void onResponse(Call<Loker> call, Response<Loker> response) {
                Loker item = response.body();
                if (item != null){
                    toolbar.setTitle(item.getJudul());
                    textViewPerusahaan.setText(item.getPerusahaan());
                    textViewRentangGaji.setText(item.getRentangGaji());

                    latitude = item.getLatitude();
                    longitude = item.getLongitude();
                    perusahaan = item.getPerusahaan();

                    webViewDeskripsi.getSettings().setJavaScriptEnabled(true);
                    webViewDeskripsi.loadDataWithBaseURL("", item.getDeskripsi(), "text/html", "UTF-8", "");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Loker> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void openGoogleMap(){
        String label = perusahaan;
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
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
