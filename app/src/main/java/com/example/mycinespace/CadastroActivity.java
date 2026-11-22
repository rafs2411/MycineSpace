package com.example.mycinespace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CadastroActivity extends Activity {

    private static final int SENHA_MINIMA = 6;

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfirmarSenha;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        TextView txtEntrar = findViewById(R.id.txtEntrar);

        btnCadastrar.setOnClickListener(v -> tentarCadastrar());

        etConfirmarSenha.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tentarCadastrar();
                return true;
            }
            return false;
        });

        txtEntrar.setOnClickListener(v -> irParaLogin());
    }
    private void tentarCadastrar() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString();
        String confirmar = etConfirmarSenha.getText().toString();

        if (!validar(nome, email, senha, confirmar)) {
            return;
        }

        esconderTeclado();

        // TODO: aqui entra a criação da conta de verdade
        // (Firebase Auth, SQLite, sua API, etc.) usando nome, email e senha.
        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show();
    }
    private boolean validar(String nome, String email, String senha, String confirmar) {
        etNome.setError(null);
        etEmail.setError(null);
        etSenha.setError(null);
        etConfirmarSenha.setError(null);

        View primeiroInvalido = null;

        if (nome.length() < 2) {
            etNome.setError("Informe seu nome");
            primeiroInvalido = etNome;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Informe um e-mail válido");
            if (primeiroInvalido == null) primeiroInvalido = etEmail;
        }

        if (senha.length() < SENHA_MINIMA) {
            etSenha.setError("A senha deve ter pelo menos 6 caracteres");
            if (primeiroInvalido == null) primeiroInvalido = etSenha;
        }
        if (!senha.equals(confirmar)) {
            etConfirmarSenha.setError("As senhas não coincidem");
            if (primeiroInvalido == null) primeiroInvalido = etConfirmarSenha;
        }

        if (primeiroInvalido != null) {
            primeiroInvalido.requestFocus();
            return false;
        }
        return true;
    }

    private void irParaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
    private void esconderTeclado() {
        View foco = getCurrentFocus();
        if (foco != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(foco.getWindowToken(), 0);
            }
        }
    }
}