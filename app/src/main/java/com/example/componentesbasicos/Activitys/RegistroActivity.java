package com.example.componentesbasicos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.componentesbasicos.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {
    private CheckBox cb_obs,cb_EditarObs,cb_limpaAlvo;
    private EditText et_Obs;
    private AutoCompleteTextView et_posologia,et_especie,et_frequencia,et_nomeComercial, et_Concentracao,et_PrincipioAtivo;
    private Button bt_salvarOK, bt_voltar;
    private RadioButton rbPassaro, rbNPassaro, rbReptil, rbMaMetaAlto, rbMaMetabaixo;
    private RadioGroup rge_g1,rge_g2;
    private TextView tv_alvo,tv_logout2,tv_usuario2;
    private DatabaseReference databaseref;
    private FirebaseDatabase database;
    private FirebaseUser usuario2;
    private ImageView img_freq,img_posologia,img_concetracao,img_nomecomercial,img_princioAtivo,img_especie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        et_PrincipioAtivo = findViewById(R.id.et_Principioativo);
        et_posologia = findViewById(R.id.et_Posologia);
        et_especie = findViewById(R.id.et_Esp);
        et_frequencia = findViewById(R.id.et_Freq);
        et_nomeComercial = findViewById(R.id.et_NomeCom);
        et_Concentracao = findViewById(R.id.et_concentra);
        cb_obs = findViewById(R.id.cb_AdicionarObs);
        cb_EditarObs = findViewById(R.id.cb_EditarObs);
        bt_salvarOK = findViewById(R.id.bt_SalvaObs);
        bt_voltar = findViewById(R.id.bt_Voltar);
        et_Obs = findViewById(R.id.et_Obs);
        rbPassaro = findViewById(R.id.rb_Passaro);
        rbNPassaro = findViewById(R.id.rb_NPassaro);
        rbReptil = findViewById(R.id.rb_Reptil);
        rbMaMetaAlto = findViewById(R.id.rb_MaMetaAlto);
        rbMaMetabaixo = findViewById(R.id.rb_MaMetaBaixo);
        rge_g1 = findViewById(R.id.rg_g1);
        rge_g2 = findViewById(R.id.rg_g2);
        tv_alvo = findViewById(R.id.tv_alvo);
        cb_limpaAlvo = findViewById(R.id.cb_LimpaAlvo);
        img_concetracao = findViewById(R.id.img_concentracao);
        img_nomecomercial = findViewById(R.id.img_nomecomercial);
        img_posologia = findViewById(R.id.img_posologia);
        img_freq = findViewById(R.id.img_freq);
        img_princioAtivo = findViewById(R.id.img_principioAtivo);
        img_especie = findViewById(R.id.img_especie);
        tv_logout2 = findViewById(R.id.tv_logout);
        tv_usuario2 = findViewById(R.id.tv_usuario);
        database = FirebaseDatabase.getInstance();
        databaseref = FirebaseDatabase.getInstance().getReference();

        et_PrincipioAtivo.setThreshold(1);
        et_posologia.setThreshold(1);
        et_especie.setThreshold(1);
        et_nomeComercial.setThreshold(1);
        et_Concentracao.setThreshold(1);

        usuario2 = FirebaseAuth.getInstance().getCurrentUser();
        tv_usuario2.setText(usuario2.getDisplayName());

        checkBox();
        radioGrupConfig();
        preenchePrincioAtivo();

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        bt_salvarOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_salvarOK.setVisibility(View.INVISIBLE);
                et_Obs.setVisibility(View.INVISIBLE);
                if (TextUtils.isEmpty(et_Obs.getText().toString())){
                    cb_EditarObs.setVisibility(View.INVISIBLE);
                    cb_EditarObs.setChecked(false);
                    cb_obs.setChecked(false);
                } else if (!TextUtils.isEmpty(et_Obs.getText().toString())){
                    cb_EditarObs.setVisibility(View.VISIBLE);
                    cb_EditarObs.setChecked(false);
                }
                deixarViewVisivel();
            }
        });
        tv_logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        img_princioAtivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_PrincipioAtivo.showDropDown();
            }
        });
        img_posologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_posologia.showDropDown();
            }
        });
        img_especie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_especie.showDropDown();
            }
        });
        img_nomecomercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nomeComercial.showDropDown();
            }
        });
        img_concetracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_Concentracao.showDropDown();
            }
        });
        img_freq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_frequencia.showDropDown();
            }
        });
        et_especie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheEspecie(et_PrincipioAtivo.getText().toString());

                }
            }
        });
        et_posologia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preenchePosologia(et_PrincipioAtivo.getText().toString(), et_especie.getText().toString());

                }
            }
        });
        et_nomeComercial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheNomeComercial(et_PrincipioAtivo.getText().toString());

                }
            }
        });
        et_Concentracao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    preencheConcentracao(et_PrincipioAtivo.getText().toString(), et_nomeComercial.getText().toString());
                }
            }
        });
    }
    //deslogar
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoguinActivity.class);
        startActivity(intent);
        finish();
    }

    // salvar dados cadastrados
    public void salvarRegistro(View view) {
        if (validarDados()) {
            int constante = retornaK();
            if (!cb_obs.isChecked()){
             cb_obs.setText("");
            }

            if ((!TextUtils.isEmpty(et_posologia.getText().toString())) && (!TextUtils.isEmpty(et_nomeComercial.getText().toString()))){
                enviarDadosMedicamento(et_PrincipioAtivo.getText().toString(), et_especie.getText().toString(), Integer.parseInt(et_frequencia.getText().toString()), Double.parseDouble(et_posologia.getText().toString()), constante,et_Obs.getText().toString());
                enviarDadosConcentracao(et_PrincipioAtivo.getText().toString(), et_nomeComercial.getText().toString(), Integer.parseInt(et_Concentracao.getText().toString()));

            }else if ((!TextUtils.isEmpty(et_posologia.getText().toString())) && (TextUtils.isEmpty(et_nomeComercial.getText().toString()))){
                enviarDadosMedicamento(et_PrincipioAtivo.getText().toString(), et_especie.getText().toString(), Integer.parseInt(et_frequencia.getText().toString()), Double.parseDouble(et_posologia.getText().toString()), constante,et_Obs.getText().toString());

            }else if ((TextUtils.isEmpty(et_posologia.getText().toString())) && (!TextUtils.isEmpty(et_nomeComercial.getText().toString()))){
                enviarDadosConcentracao(et_PrincipioAtivo.getText().toString(), et_nomeComercial.getText().toString(), Integer.parseInt(et_Concentracao.getText().toString()));

            }
            preenchePrincioAtivo();
            Toast.makeText(getApplicationContext(), "Salvo", Toast.LENGTH_LONG).show();
        }
    }

    //adicionar ou nao Caixa de observação vinculada com o posologia daquela especie.
    public void checkBox (){
        bt_salvarOK.setVisibility(View.INVISIBLE);
        et_Obs.setVisibility(View.INVISIBLE);
        cb_EditarObs.setVisibility(View.INVISIBLE);
        cb_limpaAlvo.setVisibility(View.INVISIBLE);

        cb_obs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cb_obs.isChecked()){
                    bt_salvarOK.setVisibility(View.INVISIBLE);
                    et_Obs.setVisibility(View.INVISIBLE);
                    deixarViewVisivel();
                    cb_EditarObs.setChecked(false);
                } else if (cb_obs.isChecked()){
                    if (TextUtils.isEmpty(et_Obs.getText().toString())){
                        cb_EditarObs.setChecked(false);
                    }
                    bt_salvarOK.setVisibility(View.VISIBLE);
                    et_Obs.setVisibility(View.VISIBLE);
                    deixarViewInvisivel();
                }
            }
        });
        cb_EditarObs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_EditarObs.isChecked()){
                    bt_salvarOK.setVisibility(View.VISIBLE);
                    et_Obs.setVisibility(View.VISIBLE);
                    deixarViewInvisivel();
                }
            }
        });
        cb_limpaAlvo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rge_g1.clearCheck();
                rge_g2.clearCheck();
                cb_limpaAlvo.setChecked(false);
                cb_limpaAlvo.setVisibility(View.INVISIBLE);
            }
        });
    }

    //validar formulario
    public boolean validarDados (){
        int k = retornaK();
        boolean resultado = true;

        //principio ativo retorna falso ate que tenha alguma cosia
        if (TextUtils.isEmpty(et_PrincipioAtivo.getText().toString())){
            et_PrincipioAtivo.setError("Preencha com um pricipio ativo");
            et_PrincipioAtivo.requestFocus();
            return false;
        }
        // se grupo 1 e grupo 2 estiver com algo retorna true ou se grupo 1 ou grupo 2 estiver completo.
        if ((((!TextUtils.isEmpty(et_posologia.getText().toString()))) && ((!TextUtils.isEmpty(et_especie.getText().toString())) && (!TextUtils.isEmpty(et_frequencia.getText().toString())))) && (k != 0) && (
                (TextUtils.isEmpty(et_Concentracao.getText().toString())) && (!TextUtils.isEmpty(et_nomeComercial.getText().toString())))) {
            return true;
        }
        // obriga a preencher todos campos do tipo1 se algum campo tipo 1 estiver com algo
        if ((((!TextUtils.isEmpty(et_posologia.getText().toString()))) || ((!TextUtils.isEmpty(et_especie.getText().toString())) || (!TextUtils.isEmpty(et_frequencia.getText().toString())))) || (k != 0)){
            if (TextUtils.isEmpty(et_posologia.getText().toString())){
                et_posologia.setError("");
                return false;
            }else if (TextUtils.isEmpty(et_especie.getText().toString())){
                et_especie.setError("");
                return false;
            }else if (TextUtils.isEmpty(et_frequencia.getText().toString())){
                et_frequencia.setError("");
                return false;
            } else if (k==0){
                tv_alvo.setError("");
                return false;
            }
        }
        // obriga a preencher todos do tipo 2 se algum campo 2 estiver com algo
        if ((!TextUtils.isEmpty(et_Concentracao.getText().toString())) && (TextUtils.isEmpty(et_nomeComercial.getText().toString()))){
            et_nomeComercial.setError("Preencha com um nome comercial");
            et_nomeComercial.requestFocus();
            return false;
        } else if ((TextUtils.isEmpty(et_Concentracao.getText().toString())) && (!TextUtils.isEmpty(et_nomeComercial.getText().toString()))) {
            et_Concentracao.setError("Preencha com uma concentração");
            et_Concentracao.requestFocus();
            return false;
        }
        // se os campos poso e nc estiver vasio
        if (TextUtils.isEmpty(et_posologia.getText().toString()) && TextUtils.isEmpty(et_nomeComercial.getText().toString())){
            Toast.makeText(getApplicationContext(),"Preencha o campo Posologia ou Nome comercial",Toast.LENGTH_LONG).show();
            return false;
        }
        // se o campo observaçao estiver cheio e o campo especie estiver vasio ele volta pro campo
        if (!TextUtils.isEmpty(et_Obs.getText().toString()) && TextUtils.isEmpty(et_especie.getText()) ){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle("Configurar envio");
            dialog.setMessage("Você esta tentando enviar uma observação sem declarar a especie.Deseja exluir a observação?");
            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_Obs.setText("");
                }
            });
            dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_especie.setError("");
                    et_especie.requestFocus();
                }
            });
            dialog.create();
            dialog.show();
        }
        return resultado;
    }

    //so pra facilitar
    public void deixarViewVisivel(){
        tv_alvo.setVisibility(View.VISIBLE);
        rbPassaro.setVisibility(View.VISIBLE);
        rbNPassaro.setVisibility(View.VISIBLE);
        rbReptil.setVisibility(View.VISIBLE);
        rbMaMetaAlto.setVisibility(View.VISIBLE);
        rbMaMetabaixo.setVisibility(View.VISIBLE);
        rge_g1.setVisibility(View.VISIBLE);
        rge_g2.setVisibility(View.VISIBLE);
    }
    public void deixarViewInvisivel(){
        tv_alvo.setVisibility(View.INVISIBLE);
        rbPassaro.setVisibility(View.INVISIBLE);
        rbNPassaro.setVisibility(View.INVISIBLE);
        rbReptil.setVisibility(View.INVISIBLE);
        rbMaMetaAlto.setVisibility(View.INVISIBLE);
        rbMaMetabaixo.setVisibility(View.INVISIBLE);
        rge_g1.setVisibility(View.INVISIBLE);
        rge_g2.setVisibility(View.INVISIBLE);
    }

    // gambis pra melhorar o layout dos radiogroup, deve ter um jeito mais facil
    public void radioGrupConfig (){

        rge_g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_Passaro ||checkedId == R.id.rb_NPassaro ||checkedId == R.id.rb_Reptil ){
                    cb_limpaAlvo.setVisibility(View.VISIBLE);
                    rge_g2.clearCheck();
                    rge_g1.check(checkedId);
                }
            }
        });
        rge_g2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_MaMetaAlto ||checkedId == R.id.rb_MaMetaBaixo){
                    cb_limpaAlvo.setVisibility(View.VISIBLE);
                    rge_g1.clearCheck();
                    rge_g2.check(checkedId);
                }
            }
        });
    }

    //retorna o k alvo
    public int retornaK(){
        int k;
        if (rbPassaro.isChecked()){
            k = 129;
            return k;
        }else if(rbNPassaro.isChecked()) {
            k = 78;
            return k;
        }else if(rbReptil.isChecked()) {
            k = 10;
            return k;
        }else if(rbMaMetaAlto.isChecked()) {
            k = 70;
            return k;
        }else if (rbMaMetabaixo.isChecked()) {
            k = 49;
            return k;
        }else {
            k = 0;
            return k;
        }
    }

    public void enviarDadosMedicamento(String principioativo,String especie,int frequencia, Double posologia,int constante,String observacao){
        databaseref.child("principio ativo").child(principioativo).child("medicamento").setValue(principioativo);
        databaseref.child("principio ativo").child(principioativo).child(especie).child("especie").setValue(especie);
        databaseref.child("principio ativo").child(principioativo).child(especie).child("frequencia").setValue(frequencia);
        databaseref.child("principio ativo").child(principioativo).child(especie).child("constante").setValue(constante);
        databaseref.child("principio ativo").child(principioativo).child(especie).child("posologia").setValue(posologia);
        databaseref.child("principio ativo").child(principioativo).child(especie).child("observação").setValue(observacao);


    }
    public void enviarDadosConcentracao (String principioativo,String nomeComercial, int concentracao) {
        databaseref.child("principio ativo").child(principioativo).child("nome comercial").child(nomeComercial).child("nome comercial").setValue(nomeComercial);
        databaseref.child("principio ativo").child(principioativo).child("nome comercial").child(nomeComercial).child("concentração" + concentracao).child("concentração").setValue(concentracao);
    }

    //preenche o autocompleta
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
                et_PrincipioAtivo.setAdapter(adapter);

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
                et_especie.setAdapter(adapter);
                System.out.println("teste");
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
                    et_posologia.setAdapter(adapter);
                }

                if (snapshot.child("frequencia").exists()) {
                    Double[] listfreq = new Double[1];
                    double freq = snapshot.child("frequencia").getValue(double.class);
                    listfreq[0] = freq;
                    ArrayAdapter<Double> adapter = new ArrayAdapter<Double>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listfreq);
                    et_frequencia.setAdapter(adapter);
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
                    et_nomeComercial.setAdapter(adapter);
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
                    et_Concentracao.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}