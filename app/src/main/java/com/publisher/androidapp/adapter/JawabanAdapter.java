package com.publisher.androidapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.model.Jawaban;
import com.publisher.androidapp.model.Loker;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class JawabanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> itemList;
    private List<Object> filteredList;
    private NameFilter filter;

    private static int ITEM_1 = 1;
    private static int ITEM_2 = 2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_1){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_jawaban_1, parent, false);


            return new ItemJawaban1(itemView);
        }else{
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_jawaban_2, parent, false);


            return new ItemJawaban2(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemJawaban1) {
            final ItemJawaban1 itemViewHolder = (ItemJawaban1) holder;
            Jawaban item = (Jawaban) itemList.get(position);
            itemViewHolder.textViewJawaban.setText(item.getJawaban());
            GlideApp.with(context)
                    .load(item.getFoto())
                    .dontTransform()
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.imageViewFoto);
        }else{
            final ItemJawaban2 itemViewHolder = (ItemJawaban2) holder;
            Jawaban item = (Jawaban) itemList.get(position);
            itemViewHolder.textViewJawaban.setText(item.getJawaban());
            GlideApp.with(context)
                    .load(item.getFoto())
                    .dontTransform()
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.imageViewFoto);

            itemViewHolder.textViewKeterangan.setText(item.getNama() + " - " + item.getTanggal());
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Jawaban item = (Jawaban) itemList.get(position);
        int id = Prefs.getInt(Constant.STR_ID, 0);
        String hakAkses = Prefs.getString(Constant.STR_HAK_AKSES, "");
        if (hakAkses.equals("Relawan")){
            if (item.getIdRelawan().equals(String.valueOf(id))){
                return ITEM_1;
            }
        }else {
            if (item.getIdTunaKarya().equals(String.valueOf(id))){
                return ITEM_1;
            }
        }

        return ITEM_2;
    }

    public JawabanAdapter(Context context, List<Object> arrayList) {
        this.itemList = arrayList;
        this.filteredList = arrayList;
        this.context = context;
    }

    public boolean isHeader(int position) {
        return position == itemList.size();
    }


    //region VIEW HOLDER
    public class ItemJawaban1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewJawaban;
        ImageView imageViewFoto;

        public ItemJawaban1(final View itemView) {
            super(itemView);
            textViewJawaban = itemView.findViewById(R.id.textview_jawaban);
            imageViewFoto = itemView.findViewById(R.id.imageview_foto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*
            Loker item = (Loker) itemList.get(getAdapterPosition());

            Intent intent = new Intent(v.getContext(), DetailLokerActivity.class);
            intent.putExtra(Constant.STR_ID, item.getId());
            v.getContext().startActivity(intent);
            */
        }
    }

    public class ItemJawaban2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewJawaban, textViewKeterangan;
        ImageView imageViewFoto;

        public ItemJawaban2(final View itemView) {
            super(itemView);
            textViewJawaban = itemView.findViewById(R.id.textview_jawaban);
            imageViewFoto = itemView.findViewById(R.id.imageview_foto);
            textViewKeterangan = itemView.findViewById(R.id.textview_keterangan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*
            Loker item = (Loker) itemList.get(getAdapterPosition());

            Intent intent = new Intent(v.getContext(), DetailLokerActivity.class);
            intent.putExtra(Constant.STR_ID, item.getId());
            v.getContext().startActivity(intent);
            */
        }
    }


    //endregion

    private void showCategoryActivity(int pos) {
        /*
        Locale.Category category = (Category) itemList.get(pos);
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(Constant.STR_CATEGORY_ID, category.getCid());
        intent.putExtra(Constant.STR_CATEGORY_NAME, category.getCategoryName());
        context.startActivity(intent);
        */
    }



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
                    if (filteredList.get(i) instanceof Loker) {
                        String nameList = ((Berita) filteredList.get(i)).getJudul();
                        if (nameList.toLowerCase().contains(constraint))
                            filteredItems.add((Berita) filteredList.get(i));
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
