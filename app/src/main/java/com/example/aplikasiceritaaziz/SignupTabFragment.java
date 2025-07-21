package com.example.aplikasiceritaaziz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText emailInput, passwordInput, confirmInput;
    Button signupButton;
    DatabaseHelper dbHelper;

    public SignupTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        // Binding View
        emailInput = view.findViewById(R.id.signup_email);
        passwordInput = view.findViewById(R.id.signup_password);
        confirmInput = view.findViewById(R.id.signup_confirm);
        signupButton = view.findViewById(R.id.signup_button);

        // Init database
        dbHelper = new DatabaseHelper(getContext());

        // Event Signup
        signupButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Password tidak sama!", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.registerUser(email, password);
                if (inserted) {
                    Toast.makeText(getContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                    emailInput.setText("");
                    passwordInput.setText("");
                    confirmInput.setText("");
                } else {
                    Toast.makeText(getContext(), "Registrasi gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
