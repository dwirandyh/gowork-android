package com.publisher.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.model.Pelatihan;
import com.publisher.androidapp.model.PelatihanRelawan;
import com.publisher.androidapp.model.PelatihanTunaKarya;
import com.publisher.androidapp.service.PelatihanService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.GlideApp;
import com.publisher.androidapp.utils.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPelatihanActivity extends AppCompatActivity {

    ImageView imageViewThumbnail;
    TextView textViewTanggal, textViewKuota, textViewLokasi;
    WebView webViewDeskripsi;

    Context context;

    int id;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private Pelatihan item = new Pelatihan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_detail_pelatihan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Tambahkan kedalam jadwal pelatihan anda?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        id = getIntent().getIntExtra(Constant.STR_ID, 0);

        imageViewThumbnail = findViewById(R.id.imageview_thumbnail);
        textViewTanggal = findViewById(R.id.textview_tanggal_pelatihan);
        textViewKuota = findViewById(R.id.textview_kuota);
        textViewLokasi = findViewById(R.id.textview_lokasi);
        webViewDeskripsi = findViewById(R.id.webview_deskripsi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Mengambil data ke server"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        getDetail();
    }

    private void getDetail() {
        PelatihanService service = Injector.pelatihanService();
        service.getDetail(id).enqueue(new Callback<Pelatihan>() {
            @Override
            public void onResponse(Call<Pelatihan> call, Response<Pelatihan> response) {
                item = response.body();
                if (item != null){
                    toolbar.setTitle(item.getJudul());
                    textViewTanggal.setText(item.getTanggalPelatihan());
                    textViewLokasi.setText(item.getLokasi());
                    textViewKuota.setText(item.getJumlahPesertaTerdaftar() + " dari " + item.getKuota());

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
            public void onFailure(Call<Pelatihan> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void tambahJadwalTunaKarya(){
        progressDialog.setMessage("Menambahkan ke jadwal pelatihan anda");
        progressDialog.show();

        int idPelatihan = item.getId();

        PelatihanService service = Injector.pelatihanService();
        if (Prefs.getString(Constant.STR_HAK_AKSES, "").equals("Tuna Karya")){
            int idTunaKarya = Prefs.getInt(Constant.STR_ID, 0);
            service.tambahJadwalTunaKarya(idPelatihan, idTunaKarya).enqueue(pelatihanTunaKaryaCallback);
        }else{
            int idRelwan = Prefs.getInt(Constant.STR_ID, 0);
            service.tambahJadwalRelawan(idPelatihan, idRelwan).enqueue(pelatihanRelawanCallback);
        }

    }

    Callback<PelatihanRelawan> pelatihanRelawanCallback = new Callback<PelatihanRelawan>() {
        @Override
        public void onResponse(Call<PelatihanRelawan> call, Response<PelatihanRelawan> response) {
            progressDialog.dismiss();
            Toast.makeText(context, "Berhasil menjadi relawan", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<PelatihanRelawan> call, Throwable t) {
            t.printStackTrace();
            progressDialog.dismiss();
        }
    };

    Callback<PelatihanTunaKarya> pelatihanTunaKaryaCallback = new Callback<PelatihanTunaKarya>() {
        @Override
        public void onResponse(Call<PelatihanTunaKarya> call, Response<PelatihanTunaKarya> response) {
            progressDialog.dismiss();
            Toast.makeText(context, "Berhasil menambahkan pelatihan kedalam jadwal anda", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<PelatihanTunaKarya> call, Throwable t) {
            t.printStackTrace();
            progressDialog.dismiss();
        }
    };

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    tambahJadwalTunaKarya();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

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
