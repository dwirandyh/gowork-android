package com.publisher.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.publisher.androidapp.R;
import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.service.BeritaService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.GlideApp;
import com.publisher.androidapp.utils.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBeritaActivity extends AppCompatActivity {

    ImageView imageViewThumbnail;
    TextView textViewTanggal;
    WebView webViewDeskripsi;

    Context context;

    int id;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_detail_berita);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        id = getIntent().getIntExtra(Constant.STR_ID, 0);

        imageViewThumbnail = findViewById(R.id.imageview_thumbnail);
        textViewTanggal = findViewById(R.id.textview_tanggal_pelatihan);
        webViewDeskripsi = findViewById(R.id.webview_deskripsi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Mengambil data ke server"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        getDetail();
    }

    private void getDetail() {
        BeritaService service = Injector.beritaService();
        service.getDetail(id).enqueue(new Callback<Berita>() {
            @Override
            public void onResponse(Call<Berita> call, Response<Berita> response) {
                Berita item = response.body();
                if (item != null){
                    toolbar.setTitle(item.getJudul());
                    textViewTanggal.setText(item.getTanggalPosting());
                    GlideApp.with(context)
                            .load(item.getFoto())
                            .dontTransform()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageViewThumbnail);

                    webViewDeskripsi.getSettings().setJavaScriptEnabled(true);
                    webViewDeskripsi.loadDataWithBaseURL("", item.getDeskripsi(), "text/html", "UTF-8", "");

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Berita> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
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
