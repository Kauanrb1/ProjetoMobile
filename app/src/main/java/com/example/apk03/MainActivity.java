package com.example.apk03;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
private ImageView google;
private Button button1;
private TextView text3;
private EditText texto1, texto2;
private SignInClient oneTapClient;
private BeginSignInRequest signInRequest;
private ProgressBar progress_bar;
String[] mensagens = {"Preencha todos os campos!", "Login realizado com sucesso!", "erro ao realizar o login!"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IniciarComponentes();

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
                Intent intent = new Intent(MainActivity.this, CadastroEtapa0.class);
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

        TextView Privacy = findViewById(R.id.Privacy);
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this);
                buider.setTitle("Política de Privacidade");
                buider.setMessage("Ao usar este aplicativo, você concorda com os nossos termos de uso e política de privacidade. Para mais informações, visite nosso site.");

                buider.setPositiveButton("Aceitar", (dialog, which) -> {

                });
                buider.setNegativeButton("Recusar", (dialog, which) -> {
                    dialog.dismiss();
                });
                buider.show();
            }
        });

google.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(MainActivity.this, new OnSuccessListener<BeginSignInResult>() {
        @Override
        public void onSuccess(BeginSignInResult beginSignInResult) {
            try {
                startIntentSenderForResult(beginSignInResult.getPendingIntent().getIntentSender(), 2, null, 0, 0, 0);
            }catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }
        }
    }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            e.printStackTrace();
        }
    });
    }
});

}
    private void IrParaPerfilActivity(){
        Intent intent = new Intent(MainActivity.this, telaPricipal.class);
        startActivity(intent);
    }
    private void IniciarComponentes(){
         google = findViewById(R.id.google);
         button1 = findViewById(R.id.button1);
         text3 = findViewById(R.id.text3);
         texto2 = findViewById(R.id.texto2);
         texto1 = findViewById(R.id.texto1);
    }
    private void AutenticarUsuario(View view) {

        String email = texto1.getText().toString();
        String senha = texto2.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener((new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progress_bar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IrParaPerfilActivity();
                        }
                    }, 2000);
                } else {
                    String erro;

                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = "Erro ao realizar login";
                    }
                    Toast toast = Toast.makeText(MainActivity.this, erro, Toast.LENGTH_SHORT);
                    Objects.requireNonNull(toast.getView()).setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    TextView text = toast.getView().findViewById(android.R.id.message);
                    text.setTextColor(Color.BLACK);
                    toast.show();
                }
            }
        }));
    }

    @Override
    protected void onStart(){
    super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null){
            IrParaPerfilActivity();
        }

        // Configure Google One Tap Sign-In
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = new BeginSignInRequest.Builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id)) // Add this in strings.xml
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .setAutoSelectEnabled(true)
                .build();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            try{
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                String googleAccountName = credential.getDisplayName();
                String googleAccountEmail = credential.getId();
                if (idToken != null){
                    FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        // Store login method
                                        getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit().putString("loginMethod", "google").apply();
                                        Intent intent = new Intent(MainActivity.this, telaPricipal.class);
                                        intent.putExtra("googleAccountName", googleAccountName);
                                        intent.putExtra("googleAccountEmail", googleAccountEmail);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            } catch (ApiException e){
                e.printStackTrace();
            }
        }
    }


}