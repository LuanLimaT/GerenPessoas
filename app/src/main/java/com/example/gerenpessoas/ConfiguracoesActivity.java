package com.example.gerenpessoas;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracoesActivity extends AppCompatActivity {
    private RadioGroup rgCores;
    private Button btnSalvarCor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        rgCores = findViewById(R.id.rgCores);
        btnSalvarCor = findViewById(R.id.btnSalvarCor);
        carregarCorSelecionada();
        carregarCorFundo();

        btnSalvarCor.setOnClickListener(v -> salvarCor());
    }

    private void carregarCorSelecionada() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        int cor = sharedPreferences.getInt("BACKGROUND_COLOR", Color.WHITE);

        int radioId = R.id.rbBranco;
        if (cor == Color.parseColor("#ADD8E6")) {
            radioId = R.id.rbAzul;
        } else if (cor == Color.parseColor("#90EE90")) {
            radioId = R.id.rbVerde;
        } else if (cor == Color.parseColor("#FFFFE0")) {
            radioId = R.id.rbAmarelo;
        }

        rgCores.check(radioId);
    }

    private void salvarCor() {
        int corSelecionada = Color.WHITE;
        int checkedId = rgCores.getCheckedRadioButtonId();

        if (checkedId == R.id.rbAzul) {
            corSelecionada = Color.parseColor("#ADD8E6");
        } else if (checkedId == R.id.rbVerde) {
            corSelecionada = Color.parseColor("#90EE90");
        } else if (checkedId == R.id.rbAmarelo) {
            corSelecionada = Color.parseColor("#FFFFE0");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("BACKGROUND_COLOR", corSelecionada);
        editor.apply();

        findViewById(R.id.configLayout).setBackgroundColor(corSelecionada);
    }

    private void carregarCorFundo() {
        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);
        int cor = sharedPreferences.getInt("BACKGROUND_COLOR", Color.WHITE);
        findViewById(R.id.configLayout).setBackgroundColor(cor);
    }
}