package com.example.PrintXpress;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.List;

public class SavedDesignAdapter extends RecyclerView.Adapter<SavedDesignAdapter.ViewHolder> {

    private List<SavedDesign> designs;
    private AppDatabase db;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(SavedDesign design);
    }

    public SavedDesignAdapter(List<SavedDesign> designs, AppDatabase db, OnDeleteClickListener deleteClickListener) {
        this.designs = designs;
        this.db = db;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedDesign design = designs.get(position);
        holder.nameText.setText(design.getName());
        holder.dateText.setText("Saved on: " + design.getDateSaved());

        if (design.getFilePath() != null) {
            holder.imageView.setImageURI(Uri.parse(design.getFilePath()));
        }

        holder.deleteBtn.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(design);
            }
        });
    }

    @Override
    public int getItemCount() {
        return designs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText, dateText;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.design_image);
            nameText = itemView.findViewById(R.id.design_name);
            dateText = itemView.findViewById(R.id.design_date);
            deleteBtn = itemView.findViewById(R.id.btn_delete_design);
        }
    }
}
