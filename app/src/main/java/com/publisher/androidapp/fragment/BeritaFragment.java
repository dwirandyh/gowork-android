package com.publisher.androidapp.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.publisher.androidapp.R;
import com.publisher.androidapp.adapter.BeritaAdapter;
import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.service.BeritaService;
import com.publisher.androidapp.utils.Injector;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imgNotFound;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Object> itemList = new ArrayList<>();
    private SearchView searchView;
    private BeritaAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        imgNotFound = rootView.findViewById(R.id.not_found);
        swipeRefreshLayout = rootView.findViewById(R.id.swip_refresh);
        setHasOptionsMenu(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        swipeRefreshLayout.setRefreshing(true);
        getData();

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
        BeritaService service = Injector.beritaService();
        service.getAll().enqueue(new Callback<List<Berita>>() {
            @Override
            public void onResponse(Call<List<Berita>> call, Response<List<Berita>> response) {
                List<Berita> beritaList = response.body();
                itemList.clear();

                if (beritaList != null){
                    itemList.addAll(beritaList);

                    imgNotFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    initRecyclerView();
                }else{
                    imgNotFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Berita>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initRecyclerView() {
        //itemList = new ArrayList<>();
        //itemList.addAll(MyApp.wallpaperList.getWallpaperItems());

        //Collections.shuffle(itemList);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new BeritaAdapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

    }
}
