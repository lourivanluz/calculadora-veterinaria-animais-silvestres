package com.example.componentesbasicos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.componentesbasicos.R;
import com.example.componentesbasicos.classes.CALCULOS;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso;
    private RadioButton rbPassaro, rbNPassaro, rbReptil, rbMaMetaAlto, rbMaMetabaixo, rbKg, rbg;
    private RadioGroup rge_g1, rge_g2;
    private TextView tv_alvo, tv_usuario, tv_logout;
    private boolean dadosValidados;
    private Button bt_registro;
    private FirebaseUser usuario;
    private AutoCompleteTextView etconcenta, etPosologia, etNomeCom, etEsp, etPrincipioAtivo;
    private ImageView imgposologia, imgprincipioAtivo, imgespecie, imgcomecial, imgconcentracao;
    private FirebaseDatabase database;
    private Integer kmodelo, frequencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPrincipioAtivo = findViewById(R.id.et_Principioativo);
        etPosologia = findViewById(R.id.et_Posologia);
        etPeso = findViewById(R.id.et_peso);
        etNomeCom = findViewById(R.id.et_NomeCom);
        etconcenta = findViewById(R.id.et_concentra);
        rbPassaro = findViewById(R.id.rb_Passaro);
        rbNPassaro = findViewById(R.id.rb_NPassaro);
        rbReptil = findViewById(R.id.rb_Reptil);
        rbMaMetaAlto = findViewById(R.id.rb_MaMetaAlto);
        rbMaMetabaixo = findViewById(R.id.rb_MaMetaBaixo);
        rbKg = findViewById(R.id.rb_kg);
        rbg = findViewById(R.id.rb_g);
        rge_g1 = findViewById(R.id.rg_g1);
        rge_g2 = findViewById(R.id.rg_g2);
        etEsp = findViewById(R.id.et_Esp);
        tv_alvo = findViewById(R.id.tv_alvo);
        bt_registro = findViewById(R.id.bt_salvarRegistro);
        tv_usuario = findViewById(R.id.tv_usuario);
        tv_logout = findViewById(R.id.tv_logout);
        imgposologia = findViewById(R.id.img_posologia);
        imgprincipioAtivo = findViewById(R.id.img_principioAtivo);
        imgespecie = findViewById(R.id.img_especie);
        imgcomecial = findViewById(R.id.img_nomecomercial);
        imgconcentracao = findViewById(R.id.img_concentracao);
        database = FirebaseDatabase.getInstance();
        kmodelo=0;
        etPrincipioAtivo.setThreshold(1);
        etPosologia.setThreshold(1);
        etEsp.setThreshold(1);
        etNomeCom.setThreshold(1);
        etconcenta.setThreshold(1);

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        tv_usuario.setText(usuario.getDisplayName());

        radioGrupConfig();
        preenchePrincioAtivo();

        bt_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        imgprincipioAtivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPrincipioAtivo.showDropDown();
            }
        });
        imgposologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPosologia.showDropDown();
            }
        });
        imgespecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEsp.showDropDown();
            }
        });
        imgcomecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNomeCom.showDropDown();
            }
        });
        imgconcentracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcenta.showDropDown();
            }
        });
        etEsp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheEspecie(etPrincipioAtivo.getText().toString());

                }
            }
        });
        etPosologia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preenchePosologia(etPrincipioAtivo.getText().toString(), etEsp.getText().toString());

                }
            }
        });
        etNomeCom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheNomeComercial(etPrincipioAtivo.getText().toString());

                }
            }
        });
        etconcenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheConcentracao(etPrincipioAtivo.getText().toString(), etNomeCom.getText().toString());
                }
            }
        });

    }

    // gambis pra melhorar o layout dos radiogroup, deve ter um jeito mais facil
    public void radioGrupConfig() {

        rge_g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_Passaro || checkedId == R.id.rb_NPassaro || checkedId == R.id.rb_Reptil) {
                    rge_g2.clearCheck();
                    rge_g1.check(checkedId);
                }
            }
        });
        rge_g2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_MaMetaAlto || checkedId == R.id.rb_MaMetaBaixo) {
                    rge_g1.clearCheck();
                    rge_g2.check(checkedId);
                }
            }
        });
    }

    //validar formulario
    public boolean validarDados() {
        boolean resultado = false;
        int kalvo = retornaKalvo();

        if ((((!TextUtils.isEmpty(etPrincipioAtivo.getText().toString())) && (!TextUtils.isEmpty(etPosologia.getText().toString()))) && (((!TextUtils.isEmpty(etPeso.getText().toString())) && (kalvo != 0))) && ((!TextUtils.isEmpty(etEsp.getText().toString()))))) {
            //descobrir como limpar mensagem de erro.
            resultado = true;
        } else if (TextUtils.isEmpty(etPrincipioAtivo.getText().toString())) {
            etPrincipioAtivo.setError("campo obrigatorio");
            etPrincipioAtivo.requestFocus();

        } else if (TextUtils.isEmpty(etPosologia.getText().toString())) {
            etPosologia.setError("campo obrigatorio");
            etPosologia.requestFocus();
            resultado = false;
        } else if (TextUtils.isEmpty(etPeso.getText().toString())) {
            etPeso.setError("campo obrigatorio");
            etPeso.requestFocus();
            resultado = false;
        } else if (TextUtils.isEmpty(etEsp.getText().toString())) {
            etEsp.setError("campo obrigatorio");
            etEsp.requestFocus();
            resultado = false;
        } else if (kalvo == 0) {
            tv_alvo.setError("campo obrigatorio");
            tv_alvo.requestFocus();
            resultado = false;
        }
        if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (TextUtils.isEmpty(etNomeCom.getText().toString()))) {
            etNomeCom.setError("selecione um nome comercial");
            etNomeCom.requestFocus();
            resultado = false;
        }
        return resultado;
    }

    // filtra e chama o calculo
    public void calcular(View view) {
        dadosValidados = validarDados();
        if (dadosValidados) {

            int valorKalvo = retornaKalvo();
            double peso = retornaPeso();
            double posologia = Double.parseDouble(etPosologia.getText().toString());
            double concen = 0;
            if (!TextUtils.isEmpty(etconcenta.getText().toString())) {
                concen = Double.parseDouble(etconcenta.getText().toString());
            }


            // temporario, ate o bd retornar a concentraçao quando selecionar nome comercial
            CALCULOS calcular = new CALCULOS();

            if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia != 0)) {
                //conta base, frequencia e concentração
                String resultado = calcular.calculo(valorKalvo, peso, posologia, kmodelo, frequencia, concen); // retornar algo
                System.out.println("test base,freq e concentra" + resultado);

            } else if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia == 0)) {
                //conta base e concentração
                String resultado = calcular.calculo(valorKalvo, peso, posologia, kmodelo, concen);
                System.out.println("test base e concentra" + resultado);

            } else if ((TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia != 0)) {
                //conta base e frequencia
                String resultado = calcular.calculo(valorKalvo, peso, posologia, kmodelo, frequencia);
                System.out.println("test base e freq" + resultado);

            } else if ((TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia == 0)) {
                // conta base
                String resultado = calcular.calculo(valorKalvo, peso, posologia, kmodelo);
                System.out.println("test base" + resultado);
            }
        } else {
            System.out.println("test falta dados");
        }
    }

    //retorna o k alvo
    public int retornaKalvo() {
        int k;
        if (rbPassaro.isChecked()) {
            k = 129;
            return k;
        } else if (rbNPassaro.isChecked()) {
            k = 78;
            return k;
        } else if (rbReptil.isChecked()) {
            k = 10;
            return k;
        } else if (rbMaMetaAlto.isChecked()) {
            k = 70;
            return k;
        } else if (rbMaMetabaixo.isChecked()) {
            k = 49;
            return k;
        } else {
            k = 0;
            return k;
        }
    }

    // retorna o peso
    public double retornaPeso() {
        double peso = Double.parseDouble(etPeso.getText().toString());
        if (rbg.isChecked()) {
            peso = peso / 1000;
            return peso;
        } else if (rbKg.isChecked()) {
            return peso;
        } else {
            System.out.println("test Selecione um tipo de peso"); // precisa selecionar algo, não sei tratar.
            return peso;
        }
    }

    // deslogar
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoguinActivity.class);
        startActivity(intent);
        finish();
    }

    //busca os dados no bd e faz um array pro autocomplete

    public void preenchePrincioAtivo() {
        DatabaseReference reference = database.getReference().child("principio ativo");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            List<String> listPrincioAtivo = new ArrayList<String>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String principioAtivo = data.child("medicamento").getValue(String.class);
                        listPrincioAtivo.add(principioAtivo);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listPrincioAtivo);
                    etPrincipioAtivo.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void preencheEspecie(String princioAtivo) {
        DatabaseReference reference = database.getReference().child("principio ativo").child(princioAtivo);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            List<String> listespecie = new ArrayList<String>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String especie = data.child("especie").getValue(String.class);
                        if (especie != null) {
                            listespecie.add(especie);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listespecie);
                    etEsp.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void preenchePosologia(String princioAtivo, String especie) {
        DatabaseReference reference = database.getReference().child("principio ativo").child(princioAtivo).child(especie);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("posologia").exists()) {
                    Double[] listposologia = new Double[1];
                    double poso = snapshot.child("posologia").getValue(double.class);
                    listposologia[0] = poso;
                    ArrayAdapter<Double> adapter = new ArrayAdapter<Double>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listposologia);
                    etPosologia.setAdapter(adapter);
                }
                if (snapshot.child("constante").exists()) {
                    int contante = snapshot.child("constante").getValue(int.class);
                    kmodelo = contante;
                }
                if (snapshot.child("frequencia").exists()) {
                    int freq = snapshot.child("frequencia").getValue(int.class);
                    frequencia = freq;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void preencheNomeComercial(String princioAtivo) {

        DatabaseReference reference = database.getReference().child("principio ativo").child(princioAtivo).child("nome comercial");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            List<String> listnomecomercial = new ArrayList<String>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("nome comercial").exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String nome = data.child("nome comercial").getValue(String.class);
                        if (nome != null) {
                            listnomecomercial.add(nome);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listnomecomercial);
                    etNomeCom.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void preencheConcentracao(String princioAtivo, String comercial) {
        DatabaseReference reference = database.getReference().child("principio ativo").child(princioAtivo).child("nome comercial").child(comercial);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Double> listconcentra = new ArrayList<Double>();
                if (snapshot.child("concentração").exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Double conentracao = data.child("concentração").getValue(Double.class);
                        if (conentracao != null) {
                            listconcentra.add(conentracao);
                        }
                    }
                    ArrayAdapter<Double> adapter = new ArrayAdapter<Double>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listconcentra);
                    etconcenta.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
