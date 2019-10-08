package com.publisher.androidapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.activity.SemuaForumActivity;
import com.publisher.androidapp.adapter.ForumAdapter;
import com.publisher.androidapp.model.Forum;
import com.publisher.androidapp.model.Relawan;
import com.publisher.androidapp.service.ForumService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumFragment extends Fragment {

    RecyclerView recyclerView, recyclerViewTerbaru;
    FloatingActionButton btnKirim;
    Button btnSemua;
    EditText editTextPertanyaan;

    ConstraintLayout layoutPertanyaanAnda, layoutForm;

    List<Object> itemList = new ArrayList<>();
    List<Object> itemListAll = new ArrayList<>();
    private SearchView searchView;
    private ForumAdapter adapter;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forum, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerViewTerbaru = rootView.findViewById(R.id.recycler_view_terbaru);
        btnKirim = rootView.findViewById(R.id.btn_kirim);
        btnSemua = rootView.findViewById(R.id.btn_semua);
        editTextPertanyaan = rootView.findViewById(R.id.edittext_pertanyaan);
        layoutForm = rootView.findViewById(R.id.layout_form);
        layoutPertanyaanAnda = rootView.findViewById(R.id.layout_pertanyaan_anda);

        if (Prefs.getString(Constant.STR_HAK_AKSES, "").equals(Constant.HAK_AKSES_RELAWAN)){
            layoutPertanyaanAnda.setVisibility(View.GONE);
            layoutForm.setVisibility(View.INVISIBLE);
        }

        getData();
        getAllData();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Mengirim data ke server"); // Setting Title
                progressDialog.show();
                kirim();
            }
        });

        btnSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SemuaForumActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (searchView.isIconified()) {
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        int id = Prefs.getInt(Constant.STR_ID, 0);
        ForumService service = Injector.forumService();
        service.getForumTunaKarya(id).enqueue(new Callback<List<Forum>>() {
            @Override
            public void onResponse(Call<List<Forum>> call, Response<List<Forum>> response) {
                List<Forum> forumList = response.body();
                itemListAll.clear();

                if (forumList != null){
                    itemListAll.addAll(forumList);
                    recyclerView.setVisibility(View.VISIBLE);
                    initRecyclerView();
                }else{
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                //swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Forum>> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getAllData(){
        ForumService service = Injector.forumService();
        service.getAll().enqueue(new Callback<List<Forum>>() {
            @Override
            public void onResponse(Call<List<Forum>> call, Response<List<Forum>> response) {
                List<Forum> forumList = response.body();
                itemList.clear();

                if (forumList != null){
                    int i = 1;
                    for (Forum item : forumList){
                        if (i <= 5){
                            itemList.add(item);
                        }
                        i++;
                    }
                    recyclerViewTerbaru.setVisibility(View.VISIBLE);
                    initRecyclerViewTerbaru();
                }else{
                    recyclerViewTerbaru.setVisibility(View.INVISIBLE);
                }
                //swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Forum>> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initRecyclerViewTerbaru() {
        //itemList = new ArrayList<>();
        //itemList.addAll(MyApp.wallpaperList.getWallpaperItems());

        //Collections.shuffle(itemList);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerViewTerbaru.setLayoutManager(layoutManager);
        recyclerViewTerbaru.setNestedScrollingEnabled(false);
        recyclerViewTerbaru.setHasFixedSize(false);

        recyclerViewTerbaru.setLayoutManager(layoutManager);


        adapter = new ForumAdapter(getContext(), itemList);
        recyclerViewTerbaru.setAdapter(adapter);

    }

    public void initRecyclerView() {
        //itemList = new ArrayList<>();
        //itemList.addAll(MyApp.wallpaperList.getWallpaperItems());

        //Collections.shuffle(itemList);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(layoutManager);



        adapter = new ForumAdapter(getContext(), itemListAll);
        recyclerView.setAdapter(adapter);

    }

    private void kirim(){
        int id = Prefs.getInt(Constant.STR_ID, 0);
        String pertanyaan = editTextPertanyaan.getText().toString();
        if (!pertanyaan.equals("")){
            HashMap<String, String> fields = new HashMap<String, String>();
            fields.put("idTunaKarya", String.valueOf(id));
            fields.put("pertanyaan", pertanyaan);

            ForumService service = Injector.forumService();

            service.add(fields).enqueue(new Callback<Relawan>() {
                @Override
                public void onResponse(Call<Relawan> call, Response<Relawan> response) {
                    progressDialog.dismiss();
                    editTextPertanyaan.setText("");

                    Toast.makeText(getActivity(), "Pertanyaan anda berhasil terkirim", Toast.LENGTH_SHORT).show();
                    getAllData();
                    getData();
                }

                @Override
                public void onFailure(Call<Relawan> call, Throwable t) {
                    t.printStackTrace();

                    progressDialog.dismiss();
                    editTextPertanyaan.setText("");
                    Toast.makeText(getActivity(), "Gagal mengirim pertanyaan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
