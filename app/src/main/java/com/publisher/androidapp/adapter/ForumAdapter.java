package com.publisher.androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.publisher.androidapp.R;
import com.publisher.androidapp.activity.DetailForumActivity;
import com.publisher.androidapp.model.Forum;
import com.publisher.androidapp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> itemList;
    private List<Object> filteredList;
    private NameFilter filter;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pertanyaan, parent, false);


        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Forum item = (Forum) itemList.get(position);
            itemViewHolder.textViewPertanyaan.setText(item.getPertanyaan());
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ForumAdapter(Context context, List<Object> arrayList) {
        this.itemList = arrayList;
        this.filteredList = arrayList;
        this.context = context;
    }

    public boolean isHeader(int position) {
        return position == itemList.size();
    }


    //region VIEW HOLDER
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewPertanyaan;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            textViewPertanyaan = itemView.findViewById(R.id.textview_pertanyaan);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Forum item = (Forum) itemList.get(getAdapterPosition());

            Intent intent = new Intent(v.getContext(), DetailForumActivity.class);
            intent.putExtra(Constant.STR_ID, item.getId());
            v.getContext().startActivity(intent);
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
                    if (filteredList.get(i) instanceof Forum) {
                        String nameList = ((Forum) filteredList.get(i)).getPertanyaan();
                        if (nameList.toLowerCase().contains(constraint))
                            filteredItems.add((Forum) filteredList.get(i));
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
