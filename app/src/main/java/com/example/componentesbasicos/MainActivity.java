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
        double peso = retornaPeso();
        int posologia = 30; //fake posologia que vai ser puxada no bd. pode ter varias posologia com o mesmo tipo de medicamento, ex doxiciclina pra cachorro tem posologia 10 e pra pato 30.
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

        //Alma do calculo
        double tbmAlvo;
        double tbmModelo;
        double doseTotal;
        double doseTotalMl;
        double posologiaAlvo;
        double frequenciaModelo;
        double frequenciaAlvo;
        double tmeAlvo;
        double tmeModelo;
        frequenciaModelo = 12; // puxado do bd que diz de quanto em quanto tempo, no caso a cada 12horas
        tbmModelo = 70*(Math.pow(10,0.75)); // se não for selecionado posologia usa o  modelo de um cao de 10kg
        tbmAlvo = k*(Math.pow(peso,0.75)); // peso do animal alvo que quer medicar.
        doseTotal = ((posologia*10)/tbmModelo)*tbmAlvo; // dose total para o animal alvo
        posologiaAlvo = doseTotal/peso; // para saber a posologia do animal alvo caso queira colocar no BD.
        tmeModelo = tbmModelo/10;
        tmeAlvo = tbmAlvo/peso;
        frequenciaAlvo= (tmeModelo*frequenciaModelo)/tmeAlvo;

        System.out.println("Test o animal alvo vai receber: "+ doseTotal + "mg");
        System.out.println("test posologia para a animal alvo é: "+ posologiaAlvo );
        System.out.println("test medicar a cada: "+ frequenciaAlvo );

        System.out.println("Test usando a concentração de 20mg/ml");
        doseTotalMl = doseTotal/20;
        System.out.println("Test vai receber: " + doseTotalMl + " ml");
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

        public double retornaPeso (){
        double peso = Integer.parseInt(etPeso.getText().toString());
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
