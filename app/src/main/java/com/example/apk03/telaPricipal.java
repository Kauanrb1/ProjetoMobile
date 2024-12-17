package com.example.apk03;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class telaPricipal extends AppCompatActivity {
private boolean corDiferente = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_pricipal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button Geral = findViewById(R.id.Geral);

        Drawable transparenteborda = getResources().getDrawable(R.drawable.button_transparente_borda);
        Drawable coralterada = getResources().getDrawable(R.drawable.cor_alterada);
        Geral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(corDiferente){
                    Geral.setBackground(transparenteborda);
                } else {
                    Geral.setBackground(coralterada);
                }
                corDiferente = !corDiferente;
            }
        });
    }
}