package com.example.PrintXpress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.List;

public class SavedDesigns extends Fragment implements SavedDesignAdapter.OnDeleteClickListener {

    private AppDatabase db;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private TextView emptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_designs, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());
        recyclerView = view.findViewById(R.id.recycler_designs);
        emptyText = view.findViewById(R.id.text_no_designs);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (sessionManager.isLoggedIn()) {
            loadDesigns();
        } else {
            showEmpty("Please login to view your saved designs.");
        }

        return view;
    }

    private void loadDesigns() {
        List<SavedDesign> designList = db.savedDesignDao().getDesignsByUserId(sessionManager.getUserId());

        if (!designList.isEmpty()) {
            SavedDesignAdapter adapter = new SavedDesignAdapter(designList, db, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        } else {
            showEmpty("You have no saved designs.");
        }
    }

    private void showEmpty(String message) {
        recyclerView.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
        emptyText.setText(message);
    }

    @Override
    public void onDeleteClick(SavedDesign design) {
        db.savedDesignDao().delete(design);
        Toast.makeText(requireContext(), "Design deleted", Toast.LENGTH_SHORT).show();
        loadDesigns(); // Refresh list
    }
}
