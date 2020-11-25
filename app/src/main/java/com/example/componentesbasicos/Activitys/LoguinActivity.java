package com.example.componentesbasicos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.componentesbasicos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoguinActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText et_senha;
    private Button bt_loguin;
    private TextView tv_novoRegistro;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loguin);
        et_email = findViewById(R.id.et_email);
        et_senha = findViewById(R.id.et_senha);
        bt_loguin = findViewById(R.id.bt_loguin);
        tv_novoRegistro = findViewById(R.id.tv_novoRegistro);
        mAuth = FirebaseAuth.getInstance();

        if (userConected()){
            if (mAuth.getCurrentUser().isEmailVerified()){
                menuActivity();
            }
        }

        tv_novoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroNovoUsuarioActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        bt_loguin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguin();
            }
        });


    }

    public void loguin() {
        if (TextUtils.isEmpty(et_email.getText().toString())) {
            et_email.setError("Campo obrigatório");
            et_email.requestFocus();
        } else if (TextUtils.isEmpty(et_senha.getText().toString())) {
            et_senha.setError("Campo obrigatório");
            et_senha.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_senha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");

                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    menuActivity();
                                } else if (!mAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(LoguinActivity.this, "Por favor, verifique seu e-mail para confirmar o registro",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoguinActivity.this, "Usuario não registrado. Registre-se para continuar",
                                        Toast.LENGTH_SHORT).show();
                                tv_novoRegistro.setTextColor(getResources().getColor(R.color.red));
                            }
                        }
                    });

        }
    }

    public Boolean userConected() {
        if (mAuth.getInstance().getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }


    public void menuActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
