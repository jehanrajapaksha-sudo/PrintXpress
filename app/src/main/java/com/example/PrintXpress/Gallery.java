package com.example.PrintXpress;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Gallery extends Fragment implements GalleryAdapter.OnSaveClickListener {

    private AppDatabase db;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_gallery);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        List<Integer> galleryImages = new ArrayList<>();
        galleryImages.add(R.drawable.business);
        galleryImages.add(R.drawable.posters);
        galleryImages.add(R.drawable.shirt);
        galleryImages.add(R.drawable.business);
        galleryImages.add(R.drawable.posters);
        galleryImages.add(R.drawable.shirt);

        GalleryAdapter adapter = new GalleryAdapter(galleryImages, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onSaveClick(int imageResId) {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(requireContext(), "Please login to save designs", Toast.LENGTH_SHORT).show();
            return;
        }

        String designName = "Gallery Design " + (int)(Math.random() * 100);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        
        // Construct a URI for the drawable resource
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + requireContext().getResources().getResourcePackageName(imageResId)
                + '/' + requireContext().getResources().getResourceTypeName(imageResId)
                + '/' + requireContext().getResources().getResourceEntryName(imageResId));

        SavedDesign design = new SavedDesign(0, designName, imageUri.toString(), date, sessionManager.getUserId());
        long result = db.savedDesignDao().insert(design);

        if (result != -1) {
            Toast.makeText(requireContext(), "Design saved to your collection!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to save design.", Toast.LENGTH_SHORT).show();
        }
    }
}
