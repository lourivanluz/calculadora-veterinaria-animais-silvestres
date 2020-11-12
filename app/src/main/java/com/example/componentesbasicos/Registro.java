package com.example.componentesbasicos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Registro extends AppCompatActivity {
    private TextInputEditText ti_principioativo;
    private CheckBox cb_obs,cb_EditarObs,cb_limpaAlvo;
    private EditText et_Obs,et_posologia,et_especie,et_frequencia,et_nomeComercial, et_Concentracao;
    private Button bt_salvarOK, bt_voltar;
    private RadioButton rbPassaro, rbNPassaro, rbReptil, rbMaMetaAlto, rbMaMetabaixo;
    private RadioGroup rge_g1,rge_g2;
    private TextView tv_alvo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ti_principioativo = findViewById(R.id.et_PrincipioAtivo);
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

        checkBox();
        radioGrupConfig();

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
    }

    // salvar dados cadastrados
    public void salvarRegistro(View view){
        if (validarDados()){
            Toast.makeText(getApplicationContext(),"aqui vai salvar os dados que foi colocado",Toast.LENGTH_LONG).show();
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

        // a ideia é usar o return pra quebrar o if caso o dado nao seja inserido

        // tem dois grupos, o grupo 1  posologia,frequencia,especie e k
        // e o grupo 2 com concentra e nc


        //principio ativo retorna falso ate que tenha alguma cosia
        if (TextUtils.isEmpty(ti_principioativo.getText().toString())){
            ti_principioativo.setError("Preencha com um pricipio ativo");
            ti_principioativo.requestFocus();
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

}