package com.publisher.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.adapter.JawabanAdapter;
import com.publisher.androidapp.model.Jawaban;
import com.publisher.androidapp.model.TambahJawaban;
import com.publisher.androidapp.service.ForumService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailForumActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private EditText editTextKomentar;
    private FloatingActionButton btnKirim;

    RecyclerView recyclerView;

    List<Object> itemList = new ArrayList<>();
    private SearchView searchView;
    private JawabanAdapter adapter;

    int id = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;

        setContentView(R.layout.activity_detail_forum);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tanya Jawab");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        editTextKomentar = findViewById(R.id.edittext_komentar);
        btnKirim = findViewById(R.id.btn_kirim);


        id = getIntent().getIntExtra(Constant.STR_ID, 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Mengambil data ke server"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        getData();

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
    }

    private void kirim() {
        String komentar = editTextKomentar.getText().toString();
        int idUser = Prefs.getInt(Constant.STR_ID, 0);
        ForumService service = Injector.forumService();

        HashMap<String, String> fields = new HashMap<String, String>();
        if (Prefs.getString(Constant.STR_HAK_AKSES, "").equals(Constant.HAK_AKSES_RELAWAN)){
            fields.put("idRelawan", String.valueOf(idUser));
        }else{
            fields.put("idTunaKarya", String.valueOf(idUser));
        }
        fields.put("jawaban", komentar);

        service.add(id, fields).enqueue(new Callback<TambahJawaban>() {
            @Override
            public void onResponse(Call<TambahJawaban> call, Response<TambahJawaban> response) {
                getData();

                editTextKomentar.setText("");
            }

            @Override
            public void onFailure(Call<TambahJawaban> call, Throwable t) {
                getData();
            }
        });
    }

    private void getData(){
        ForumService service = Injector.forumService();
        service.getJawaban(id).enqueue(new Callback<List<Jawaban>>() {
            @Override
            public void onResponse(Call<List<Jawaban>> call, Response<List<Jawaban>> response) {
                itemList.clear();

                if (response.body() != null){
                    itemList.addAll(response.body());
                }


                initRecyclerView();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Jawaban>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(layoutManager);



        adapter = new JawabanAdapter(this, itemList);
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
