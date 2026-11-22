package com.example.mycinespace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView tvEsqueciSenha;
    private TextView tvCadastro;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        tvEsqueciSenha = findViewById(R.id.tvEsqueciSenha);
        tvCadastro = findViewById(R.id.tvCadastro);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarLogin();
            }
        });

        tvEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, R.string.recuperar_senha, Toast.LENGTH_SHORT).show();
            }
        });

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void tentarLogin() {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.erro_email_vazio));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.erro_email_invalido));
            editTextEmail.requestFocus();
            return;
        }

        if (senha.isEmpty()) {
            editTextPassword.setError(getString(R.string.erro_senha_vazia));
            editTextPassword.requestFocus();
            return;
        }

        if (senha.length() < 6) {
            editTextPassword.setError(getString(R.string.erro_senha_curta));
            editTextPassword.requestFocus();
            return;
        }

        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}