package com.example.PrintXpress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<Integer> images;
    private OnSaveClickListener saveClickListener;

    public interface OnSaveClickListener {
        void onSaveClick(int imageResId);
    }

    public GalleryAdapter(List<Integer> images, OnSaveClickListener saveClickListener) {
        this.images = images;
        this.saveClickListener = saveClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageResId = images.get(position);
        holder.imageView.setImageResource(imageResId);
        
        holder.saveBtn.setOnClickListener(v -> {
            if (saveClickListener != null) {
                saveClickListener.onSaveClick(imageResId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton saveBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gallery_image);
            saveBtn = itemView.findViewById(R.id.btn_save_gallery);
        }
    }
}
