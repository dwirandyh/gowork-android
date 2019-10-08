package com.dwirandyh.gowork.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dwirandyh.gowork.R;
import com.dwirandyh.gowork.activity.DetailPelatihanActivity;
import com.dwirandyh.gowork.model.Pelatihan;
import com.dwirandyh.gowork.utils.Constant;
import com.dwirandyh.gowork.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class PelatihanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> itemList;
    private List<Object> filteredList;
    private NameFilter filter;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pelatihan, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Pelatihan item = (Pelatihan) itemList.get(position);
            itemViewHolder.textviewJudul.setText(item.getJudul());
            itemViewHolder.textviewTanggalPelatihan.setText(item.getTanggalPelatihan());
            itemViewHolder.textviewKuota.setText(item.getKuota().toString());

            final String thumbnail = item.getFoto();

            GlideApp.with(context)
                    .load(thumbnail)
                    .dontTransform()
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.imageViewThumbnail1);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public PelatihanAdapter(Context context, List<Object> arrayList) {
        this.itemList = arrayList;
        this.filteredList = arrayList;
        this.context = context;
    }

    public boolean isHeader(int position) {
        return position == itemList.size();
    }


    //region VIEW HOLDER
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textviewJudul, textviewTanggalPelatihan, textviewKuota;
        ImageView imageViewThumbnail1;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            textviewJudul = itemView.findViewById(R.id.textview_judul);
            textviewTanggalPelatihan = itemView.findViewById(R.id.textview_tanggal_pelatihan);
            imageViewThumbnail1 = itemView.findViewById(R.id.imageview_thumbnail);
            textviewKuota = itemView.findViewById(R.id.textview_kuota);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Pelatihan item = (Pelatihan) itemList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailPelatihanActivity.class);
            intent.putExtra(Constant.STR_ID, item.getId());

            v.getContext().startActivity(intent);
        }
    }


    //endregion



    public Filter getFilter() {
        if (filter == null) {
            filter = new NameFilter();
        }
        return filter;
    }


    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<Object> filteredItems = new ArrayList<Object>();

                for (int i = 0, l = filteredList.size(); i < l; i++) {
                    if (filteredList.get(i) instanceof Pelatihan) {
                        String nameList = ((Pelatihan) filteredList.get(i)).getJudul();
                        if (nameList.toLowerCase().contains(constraint))
                            filteredItems.add((Pelatihan) filteredList.get(i));
                    }
                }

                result.count = filteredItems.size();
                result.values = filteredItems;

            } else {
                synchronized (this) {
                    result.values = filteredList;
                    result.count = filteredList.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList = (List<Object>) results.values;
            notifyDataSetChanged();
        }
    }
}
