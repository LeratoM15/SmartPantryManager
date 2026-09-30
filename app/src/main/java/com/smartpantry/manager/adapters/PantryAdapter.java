package com.smartpantry.manager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.R;
import com.smartpantry.manager.models.PantryItem;

import java.util.List;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;
    private final OnPantryItemActionListener listener;

    public interface OnPantryItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    public PantryAdapter(
            List<PantryItem> pantryItems,
            OnPantryItemActionListener listener) {

        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item = pantryItems.get(position);

        holder.tvIngredientName.setText(item.getName());

        String quantityText =
                item.getQuantity() + " " + item.getUnit();

        holder.tvQuantity.setText(quantityText);

        if (item.getExpiryDate() == null ||
                item.getExpiryDate().trim().isEmpty()) {

            holder.tvExpiry.setText("No expiry date");

        } else {

            holder.tvExpiry.setText(
                    "Expiry: " + item.getExpiryDate()
            );
        }

        holder.btnEdit.setOnClickListener(
                v -> listener.onEdit(item)
        );

        holder.btnDelete.setOnClickListener(
                v -> listener.onDelete(item)
        );
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public void updateItems(List<PantryItem> newItems) {
        pantryItems = newItems;
        notifyDataSetChanged();
    }

    static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvQuantity;
        TextView tvExpiry;

        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvIngredientName =
                    itemView.findViewById(
                            R.id.tvIngredientName);

            tvQuantity =
                    itemView.findViewById(
                            R.id.tvQuantity);

            tvExpiry =
                    itemView.findViewById(
                            R.id.tvExpiry);

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEdit);

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete);
        }
    }
}