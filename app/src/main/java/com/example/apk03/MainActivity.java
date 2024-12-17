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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView text3 = findViewById(R.id.text3);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, CadastroEtapa0.class);
                startActivity(intent);
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, telaPricipal.class);
                startActivity(intent);
            }
        });

        TextView Privacy =findViewById(R.id.Privacy);
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this);
                buider.setTitle("Política de Privacidade");
                buider.setMessage("Ao usar este aplicativo, você concorda com os nossos termos de uso e política de privacidade. Para mais informações, visite nosso site.");

                buider.setPositiveButton("Aceitar", (dialog, which)->{

                });
                buider.setNegativeButton("Recusar", (dialog, which) -> {
                    dialog.dismiss();
                });
                buider.show();
            }
        });
    }
}