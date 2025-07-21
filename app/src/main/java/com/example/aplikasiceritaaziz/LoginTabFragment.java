package com.example.aplikasiceritaaziz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    EditText emailInput, passwordInput;
    Button loginButton;
    DatabaseHelper dbHelper;

    public LoginTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        // Binding komponen UI
        emailInput = view.findViewById(R.id.login_email);
        passwordInput = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.login_button);

        // Inisialisasi database helper
        dbHelper = new DatabaseHelper(getContext());

        // Event Login
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                boolean valid = dbHelper.checkUser(email, password);
                if (valid) {
                    Toast.makeText(getContext(), "Login berhasil!", Toast.LENGTH_SHORT).show();

                    // Pindah ke HomeActivity
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    getActivity().finish(); // Menutup Login activity agar tidak bisa balik dengan tombol back
                } else {
                    Toast.makeText(getContext(), "Email atau password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
