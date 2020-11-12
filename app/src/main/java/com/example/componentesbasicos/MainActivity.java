package com.example.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText tiPrincipioAtivo;
    private EditText etPeso, etconcenta, etPosologia, etNomeCom, etEsp;
    private RadioButton rbPassaro, rbNPassaro, rbReptil, rbMaMetaAlto, rbMaMetabaixo, rbKg, rbg;
    private RadioGroup rge_g1, rge_g2;
    private TextView tv_alvo;
    private boolean dadosValidados;
    private Button bt_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tiPrincipioAtivo = findViewById(R.id.et_PrincipioAtivo);
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

        radioGrupConfig();

        bt_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
            }
        });

    }
    // gambis pra melhorar o layout dos radiogroup, deve ter um jeito mais facil
    public void radioGrupConfig (){

        rge_g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_Passaro ||checkedId == R.id.rb_NPassaro ||checkedId == R.id.rb_Reptil ){
                    rge_g2.clearCheck();
                    rge_g1.check(checkedId);
               }
            }
        });
        rge_g2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_MaMetaAlto ||checkedId == R.id.rb_MaMetaBaixo){
                    rge_g1.clearCheck();
                    rge_g2.check(checkedId);
                }
            }
    });
    }

    //validar formulario
    public boolean validarDados (){
        boolean resultado = false;
        int kalvo = retornaKalvo();

        if ((((!TextUtils.isEmpty(tiPrincipioAtivo.getText().toString())) && (!TextUtils.isEmpty(etPosologia.getText().toString()))) && (((!TextUtils.isEmpty(etPeso.getText().toString())) && (kalvo !=0))) && ((!TextUtils.isEmpty(etEsp.getText().toString()))))) {
           //descobrir como limpar mensagem de erro.
            resultado = true;
        }else if (TextUtils.isEmpty(tiPrincipioAtivo.getText().toString())){
            tiPrincipioAtivo.setError("campo obrigatorio");
            tiPrincipioAtivo.requestFocus();

        }else if (TextUtils.isEmpty(etPosologia.getText().toString())){
            etPosologia.setError("campo obrigatorio");
            etPosologia.requestFocus();
            resultado = false;
        } else if (TextUtils.isEmpty(etPeso.getText().toString())){
            etPeso.setError("campo obrigatorio");
            etPeso.requestFocus();
            resultado = false;
        }else if (TextUtils.isEmpty(etEsp.getText().toString())){
            etEsp.setError("campo obrigatorio");
            etEsp.requestFocus();
            resultado = false;
        }else if (kalvo==0){
            tv_alvo.setError("campo obrigatorio");
            tv_alvo.requestFocus();
            resultado = false;
        }
        if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (TextUtils.isEmpty(etNomeCom.getText().toString()))){
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
            int kmodelo = Integer.parseInt(etEsp.getText().toString()); // fake kmodelo, puxado do bd com a tag especie ou selecionada manualmente.
            int frequencia = 12; // fake frequencia puxada do bd com a tag da posologia. não é obrigatoria.
            int valorKalvo = retornaKalvo();
            double peso = retornaPeso();
            double posologia = Double.parseDouble(etPosologia.getText().toString());
            double concen;

            // temporario, ate o bd retornar a concentraçao quando selecionar nome comercial
            if (TextUtils.isEmpty(etconcenta.getText().toString())){
                concen = 20;
            }else {
                concen = 30;
            }

            calculos calcular = new calculos();

            if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia!=0)){
                //conta base, frequencia e concentração
                String resultado = calcular.calculo(valorKalvo,peso,posologia,kmodelo,frequencia,concen); // retornar algo
                System.out.println("test " + resultado);

            } else if ((!TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia ==0)){
                //conta base e concentração
                String resultado = calcular.calculo(valorKalvo,peso,posologia,kmodelo,concen);
                System.out.println("test " + resultado);

            } else if ((TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia!=0)){
                //conta base e frequencia
                String resultado = calcular.calculo(valorKalvo,peso,posologia,kmodelo,frequencia);
                System.out.println("test " + resultado);

            } else if ((TextUtils.isEmpty(etconcenta.getText().toString())) && (frequencia ==0)){
                // conta base
                String resultado = calcular.calculo(valorKalvo,peso,posologia,kmodelo);
                System.out.println("test " + resultado);
            }
        }else{
            System.out.println("test falta dados");
        }
    }

    // n sei pq mas fiz um buscar no bd
    public void buscarBdnomeCom(String[] bdNomeCom){
       // String[] bdNomeCom = {"doxvet", "enrodog", "carprocat", "dipirona", "doflex"};fake bd
        if (!TextUtils.isEmpty(etNomeCom.getText().toString())) {
            String chave = etNomeCom.getText().toString();
            for (int num1 = 0; num1 < bdNomeCom.length; num1++) {
                if (chave.equals(bdNomeCom[num1])) {
                    System.out.println("test importante igual " + bdNomeCom[num1] + " na casa: " + num1);
                    num1 = bdNomeCom.length + 1;
                } else {
                    System.out.println("diff");
                }
            }
        }
    }
    public void buscarBdPrinciAtiv (String[] bdPrinAtiv){
       // String[] bdPrinAtiv = {"doxiciclina", "enrofloxacino", "carprofeno", "Ceftriaxona"};fake bd

        if (!TextUtils.isEmpty(tiPrincipioAtivo.getText().toString())) {
            String chave = tiPrincipioAtivo.getText().toString();
            for (int num1 = 0; num1 < bdPrinAtiv.length; num1++) {
                if (chave.equals(bdPrinAtiv[num1])) {
                    System.out.println("test importante igual " + bdPrinAtiv[num1] + " na casa: " + num1);
                    num1 = bdPrinAtiv.length + 1;
                } else {
                    System.out.println("diff");
                }
            }
        }
    }

    //retorna o k alvo
    public int retornaKalvo (){
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

    // retorna o peso
    public double retornaPeso (){
        double peso = Double.parseDouble(etPeso.getText().toString());
        if (rbg.isChecked()){
            peso = peso/1000;
            return peso;
        }else if (rbKg.isChecked()){
            return peso;
        }else {
            System.out.println("test Selecione um tipo de peso"); // precisa selecionar algo, não sei tratar.
            return peso;
        }
    }

    //navegar para registro

}
