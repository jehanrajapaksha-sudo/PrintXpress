package com.example.PrintXpress;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class Addresses extends Fragment implements AddressAdapter.OnDeleteClickListener {

    private AppDatabase db;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private List<Address> addressList;
    private AddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addresses, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());
        recyclerView = view.findViewById(R.id.recycler_addresses);
        emptyText = view.findViewById(R.id.text_no_addresses);
        MaterialButton addBtn = view.findViewById(R.id.btn_add_address);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (sessionManager.isLoggedIn()) {
            loadAddresses();
        } else {
            showEmpty("Please login to manage your addresses.");
            addBtn.setEnabled(false);
        }

        addBtn.setOnClickListener(v -> showAddAddressDialog());

        return view;
    }

    private void loadAddresses() {
        addressList = db.addressDao().getAddressesByUserId(sessionManager.getUserId());

        if (!addressList.isEmpty()) {
            adapter = new AddressAdapter(addressList, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        } else {
            showEmpty("No addresses saved.");
        }
    }

    private void showEmpty(String message) {
        recyclerView.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
        emptyText.setText(message);
    }

    private void showAddAddressDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_address, null);
        EditText editLabel = dialogView.findViewById(R.id.edit_address_label);
        EditText editDetails = dialogView.findViewById(R.id.edit_address_details);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add New Address")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String label = editLabel.getText().toString().trim();
                    String details = editDetails.getText().toString().trim();

                    if (!label.isEmpty() && !details.isEmpty()) {
                        Address newAddress = new Address(0, label, details, sessionManager.getUserId());
                        db.addressDao().insert(newAddress);
                        Toast.makeText(requireContext(), "Address added", Toast.LENGTH_SHORT).show();
                        loadAddresses();
                    } else {
                        Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDeleteClick(Address address) {
        db.addressDao().delete(address);
        Toast.makeText(requireContext(), "Address deleted", Toast.LENGTH_SHORT).show();
        loadAddresses();
    }
}
