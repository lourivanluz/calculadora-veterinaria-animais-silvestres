package com.example.componentesbasicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText tiPrincipioAtivo;
    private EditText etPosologia;
    private TextView tvResultado;
    private EditText etPeso;
    private RadioButton rbPassaro;
    private RadioButton rbNPassaro;
    private RadioButton rbReptil;
    private RadioButton rbMaMetaAlto;
    private RadioButton rbMaMetaixo;
    private RadioButton rbKg;
    private RadioButton rbg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tiPrincipioAtivo = findViewById(R.id.et_PrincipioAtivo);
        etPosologia = findViewById(R.id.et_Posologia);
        tvResultado = findViewById(R.id.tv_Teste);
        etPeso = findViewById(R.id.et_peso);
        rbPassaro = findViewById(R.id.rb_Passaro);
        rbNPassaro = findViewById(R.id.rb_NPassaro);
        rbReptil = findViewById(R.id.rb_Reptil);
        rbMaMetaAlto = findViewById(R.id.rb_MaMetaAlto);
        rbMaMetaixo = findViewById(R.id.rb_MaMetaBaixo);
        rbKg = findViewById(R.id.rb_kg);
        rbg = findViewById(R.id.rb_g);
    }

    public void calcular(View view) {
        String principioAtivo = tiPrincipioAtivo.getText().toString();
        int k = retornaK();
        int peso = retornaPeso();
        int posologia = 30; //fake posologia que vai ser puxada no bd.
        String[] bdRemedios = {"doxiciclina", "enrofloxacino", "carprofeno"}; // fake bd. a ideia é buscar no banco de dados por esses principios ativos

        for (int num1 = 0; num1 < bdRemedios.length; num1++){

            if (principioAtivo.equals(bdRemedios[num1])){
                System.out.println("importante igual" );
               // int posologia = 30; // puxou do fake bd
               // for (num1=num1;principioAtivo.equals(bdRemedios[num1])==false; num1++) {//fake bd percorre o principio ativo ate que ele seja falso capturando todos volores de concentraçao e jogando em um array
                //    String[] ApresentarConcentracao = {"primeirovalor"};
               // }
                    num1 = bdRemedios.length + 1;
            }else {
                System.out.println("importante diferente");
               // int intPosologia = Integer.parseInt(edPosologia.getText().toString());

            }
        }
        double tbmAlvo;
        double tbmModelo;
        double dosetotal;
        tbmModelo = k*(Math.pow(10,0.75)); // usando modelo de um cao de 10kg
        tbmAlvo = k*(Math.pow(peso,0.75)); // peso do animal alvo que quer medicar.
        dosetotal = posologia;



        }
        public int retornaK (){
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
        }else{
            k = 49;
            return k;
        }
        }

        public int retornaPeso (){
        int peso = Integer.parseInt(etPeso.getText().toString());
        if (rbg.isChecked()){
            peso = peso/1000;
            return peso;
        }else if (rbKg.isChecked()){
            return peso;
        }else {
            System.out.println("Selecione um tipo de peso"); // precisa selecionar algo, não sei tratar.
            return peso;
        }
        }

}


