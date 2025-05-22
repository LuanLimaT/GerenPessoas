package com.example.gerenpessoas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesPessoaActivity extends AppCompatActivity {
    private EditText etNome, etIdade;
    private Button btnSalvar, btnExcluir;
    private int position;
    private PessoaSingleton pessoaSingleton;
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pessoa);

        pessoaSingleton = PessoaSingleton.getInstance();
        position = getIntent().getIntExtra("POSITION", -1);
        if (position == -1) {
            finish();
            return;
        }

        pessoa = pessoaSingleton.getPessoas().get(position);
        initViews();
        carregarDados();
        carregarCorFundo();

        btnSalvar.setOnClickListener(v -> salvarAlteracoes());
        btnExcluir.setOnClickListener(v -> excluirPessoa());
    }

    private void initViews() {
        etNome = findViewById(R.id.etNome);
        etIdade = findViewById(R.id.etIdade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
    }

    private void carregarDados() {
        etNome.setText(pessoa.getNome());
        etIdade.setText(String.valueOf(pessoa.getIdade()));
    }

    private void salvarAlteracoes() {
        String novoNome = etNome.getText().toString().trim();
        String novaIdadeStr = etIdade.getText().toString().trim();

        if (novoNome.isEmpty() || novaIdadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o nome foi alterado para um que já existe
        if (!novoNome.equals(pessoa.getNome()) && pessoaSingleton.nomeExiste(novoNome)) {
            Toast.makeText(this, "Nome já cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int novaIdade = Integer.parseInt(novaIdadeStr);
            Pessoa pessoaAtualizada = new Pessoa(novoNome, novaIdade);
            pessoaSingleton.atualizarPessoa(position, pessoaAtualizada);
            Toast.makeText(this, "Alterações salvas", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirPessoa() {
        pessoaSingleton.removerPessoa(pessoa);
        Toast.makeText(this, "Pessoa excluída", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void carregarCorFundo() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        int cor = sharedPreferences.getInt("BACKGROUND_COLOR", Color.WHITE);
        findViewById(R.id.detalhesLayout).setBackgroundColor(cor);
    }
}