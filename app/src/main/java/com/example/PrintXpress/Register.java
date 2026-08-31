package com.example.PrintXpress;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.android.material.textfield.TextInputEditText;

public class Register extends Fragment {

    private TextInputEditText nameEdit, emailEdit, phoneEdit, passwordEdit;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);

        db = AppDatabase.getInstance(requireContext());

        nameEdit = view.findViewById(R.id.edit_reg_name);
        emailEdit = view.findViewById(R.id.edit_reg_email);
        phoneEdit = view.findViewById(R.id.edit_reg_phone);
        passwordEdit = view.findViewById(R.id.edit_reg_password);
        Button registerBtn = view.findViewById(R.id.btn_register_submit);
        TextView loginLink = view.findViewById(R.id.text_goto_login);

        registerBtn.setOnClickListener(v -> handleRegistration());
        loginLink.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    private void handleRegistration() {
        if (nameEdit == null || emailEdit == null || phoneEdit == null || passwordEdit == null) return;

        String name = nameEdit.getText() != null ? nameEdit.getText().toString().trim() : "";
        String email = emailEdit.getText() != null ? emailEdit.getText().toString().trim() : "";
        String phone = phoneEdit.getText() != null ? phoneEdit.getText().toString().trim() : "";
        String password = passwordEdit.getText() != null ? passwordEdit.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name)) {
            nameEdit.setError("Enter name");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("Invalid email");
            return;
        }
        if (phone.length() < 10) {
            phoneEdit.setError("Invalid phone");
            return;
        }
        if (password.length() < 6) {
            passwordEdit.setError("Password too short");
            return;
        }

        // Run database operation in a background thread to prevent UI freezing
        new Thread(() -> {
            // Using the explicit SQL query defined in UserDao:
            // INSERT INTO users (name, email, phone, password) VALUES (:name, :email, :phone, :password)
            long result = db.userDao().insertUserManual(name, email, phone, password);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    if (result != -1) {
                        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(requireContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
