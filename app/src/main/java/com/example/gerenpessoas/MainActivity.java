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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PessoaAdapter.OnPessoaClickListener {
    private EditText etNome, etIdade;
    private Button btnCadastrar, btnConfigurarCor;
    private RecyclerView rvPessoas;
    private PessoaAdapter adapter;
    private PessoaSingleton pessoaSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obter a lista
        pessoaSingleton = PessoaSingleton.getInstance();
        initViews();
        setupRecyclerView();
        carregarCorFundo();

        btnCadastrar.setOnClickListener(v -> cadastrarPessoa());
        btnConfigurarCor.setOnClickListener(v -> abrirConfiguracoesCor());
    }

    private void initViews() {
        etNome = findViewById(R.id.etNome);
        etIdade = findViewById(R.id.etIdade);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnConfigurarCor = findViewById(R.id.btnConfigurarCor);
        rvPessoas = findViewById(R.id.rvPessoas);
    }

    //exibir a lista com RecyclerView
    private void setupRecyclerView() {
        List<Pessoa> pessoas = pessoaSingleton.getPessoas();
        adapter = new PessoaAdapter(pessoas, this);
        rvPessoas.setLayoutManager(new LinearLayoutManager(this));
        rvPessoas.setAdapter(adapter);
    }

    private void cadastrarPessoa() {
        String nome = etNome.getText().toString().trim();
        String idadeStr = etIdade.getText().toString().trim();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pessoaSingleton.nomeExiste(nome)) {
            Toast.makeText(this, "Nome já cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int idade = Integer.parseInt(idadeStr);
            Pessoa pessoa = new Pessoa(nome, idade);
            pessoaSingleton.adicionarPessoa(pessoa);
            adapter.notifyDataSetChanged();

            etNome.setText("");
            etIdade.setText("");
            Toast.makeText(this, "Pessoa cadastrada com sucesso", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirConfiguracoesCor() {
        Intent intent = new Intent(this, ConfiguracoesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPessoaClick(int position) {
        Pessoa pessoa = pessoaSingleton.getPessoas().get(position);
        Intent intent = new Intent(this, DetalhesPessoaActivity.class);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    private void carregarCorFundo() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        int cor = sharedPreferences.getInt("BACKGROUND_COLOR", Color.WHITE);
        findViewById(R.id.mainLayout).setBackgroundColor(cor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCorFundo();
        adapter.notifyDataSetChanged();
    }
}