package com.example.PrintXpress;

import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends Fragment {

    private TextInputEditText loginIdEdit, passwordEdit;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());

        loginIdEdit = view.findViewById(R.id.edit_login_id);
        passwordEdit = view.findViewById(R.id.edit_login_password);
        Button loginBtn = view.findViewById(R.id.btn_do_login);
        TextView registerLink = view.findViewById(R.id.text_goto_register);

        loginBtn.setOnClickListener(v -> handleLogin());
        registerLink.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Register())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void handleLogin() {
        if (loginIdEdit.getText() == null || passwordEdit.getText() == null) return;

        String loginId = loginIdEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (TextUtils.isEmpty(loginId)) {
            loginIdEdit.setError("Enter name, email or phone");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Enter password");
            return;
        }

        User user = db.userDao().login(loginId, password);
        if (user != null) {
            sessionManager.createLoginSession(user.getId(), user.getName());
            Toast.makeText(requireContext(), "Welcome, " + user.getName() + "!", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        } else {
            Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}