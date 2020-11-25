package com.example.componentesbasicos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.componentesbasicos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistroNovoUsuarioActivity extends AppCompatActivity {
    private EditText et_RegistroEmail;
    private EditText et_RegistroSenha;
    private EditText et_usuario;
    private Button bt_Registrarse;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_registro_novo_usuario);
        et_RegistroEmail = findViewById(R.id.et_RegistroEmail);
        et_RegistroSenha = findViewById(R.id.et_RegistroSenha);
        bt_Registrarse = findViewById(R.id.bt_Registrarse);
        et_usuario = findViewById(R.id.et_usuario);


        bt_Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    public void createAccount() {
        if (TextUtils.isEmpty(et_RegistroEmail.getText().toString())) {
            et_RegistroEmail.setError("Campo obrigatório");
            et_RegistroEmail.requestFocus();
        } else if (TextUtils.isEmpty(et_RegistroSenha.getText().toString())) {
            et_RegistroSenha.setError("Campo obrigatório");
            et_RegistroSenha.requestFocus();
        } else if (TextUtils.isEmpty(et_usuario.getText().toString())) {
            et_usuario.setError("Campo obrigatório");
            et_usuario.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(et_RegistroEmail.getText().toString(), et_RegistroSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.w("TAG", "createUserWithEmail:success", task.getException());
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegistroNovoUsuarioActivity.this, "Verifique seu e-mail para confirmação",
                                                            Toast.LENGTH_LONG).show();
                                                    updateProfile();
                                                    enviaremail();
                                                    Intent intent = new Intent(getApplicationContext(), LoguinActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(RegistroNovoUsuarioActivity.this, "Falha no envio. Certifique-se utilizar um e-mail válido",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistroNovoUsuarioActivity.this, "falha ao registrar-se. já existe um usuario com esse e-mail",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    public void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(et_usuario.getText().toString())
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d("TAG", "User profile updated.");
                        }
                    }
                });
    }

    public void enviaremail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String url = "https://calcula-vet-silvestre.firebaseapp.com/__/auth/action?mode=action&oobCode=code" + user.getUid();
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setIOSBundleId("com.example.ios")
                // The default for this is populated with the current android package name.
                .setAndroidPackageName("com.example.android", false, null)
                .build();

        user.sendEmailVerification(actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });
    }
}