package com.example.apk03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tela2 extends AppCompatActivity {

    private TextView exibir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button FinishCadastro = findViewById(R.id.FinishCadastro);
        FinishCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buider = new AlertDialog.Builder(Tela2.this);
                buider.setTitle("Cadastro");
                buider.setMessage("Cadastro Finalizado com Sucesso!");
                buider.setPositiveButton("ok", (dialog, which)->{

                });
                buider.setNegativeButton("Cancelar", (dialog, which)-> {
                    dialog.dismiss();
                });
                buider.show();
            }
        });
    }
}