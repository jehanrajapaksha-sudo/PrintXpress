package com.example.PrintXpress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.printxpress.R;

public class Profile extends Fragment {

    private SessionManager sessionManager;
    private TextView userNameText;
    private Button loginBtn;
    private Button logoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        sessionManager = new SessionManager(requireContext());
        userNameText = view.findViewById(R.id.text_user_name);
        loginBtn = view.findViewById(R.id.btn_login);
        logoutBtn = view.findViewById(R.id.btn_logout);

        // Settings items
        TextView orderHistorySettings = view.findViewById(R.id.setting_orders);
        TextView designSettings = view.findViewById(R.id.setting_designs);
        TextView addressSettings = view.findViewById(R.id.setting_address);


        updateUI();

        loginBtn.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Login())
                    .addToBackStack(null)
                    .commit();
        });

        logoutBtn.setOnClickListener(v -> {
            sessionManager.logoutUser();
            updateUI();
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
        });

        // Making the Order History functional
        orderHistorySettings.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Orders())
                        .addToBackStack(null)
                        .commit();
            } else {
                showLoginPrompt();
            }
        });

        // Making the Saved Designs functional
        designSettings.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SavedDesigns())
                        .addToBackStack(null)
                        .commit();
            } else {
                showLoginPrompt();
            }
        });

        // Making the Delivery Addresses functional
        addressSettings.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Addresses())
                        .addToBackStack(null)
                        .commit();
            } else {
                showLoginPrompt();
            }
        });



        return view;
    }

    private void showLoginPrompt() {
        Toast.makeText(getContext(), "Please login to access this feature", Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            userNameText.setText(sessionManager.getUserName());
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            userNameText.setText("Guest User");
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }
    }
}
