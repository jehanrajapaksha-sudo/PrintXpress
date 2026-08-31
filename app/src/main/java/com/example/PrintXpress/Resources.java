package com.example.PrintXpress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.printxpress.R;

public class Resources extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resources, container, false);

        Button contactBtn = view.findViewById(R.id.btn_contact_support);
        contactBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Connecting to support...", Toast.LENGTH_SHORT).show();
            // Example: Open dialer or email
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0112345678"));
            startActivity(intent);
        });

        return view;
    }
}